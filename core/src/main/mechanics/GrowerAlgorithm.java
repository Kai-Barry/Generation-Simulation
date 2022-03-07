package main.mechanics;

import main.utility.Team;
import main.utility.TileType;

import java.util.List;

import static main.utility.TileType.*;

public class GrowerAlgorithm extends BaseTile {
    private final TileType newTileType;
    private final Team team;
    private final float takeEmpty;
    private final float takeEnemy;
    private final float takeFuse;
    private final TileType teamsFuseTile;
    public GrowerAlgorithm(TileType tileType, GridIndex gridIndex, float takeEmpty, float takeEnemy, float takeFuse) {
        super(tileType, gridIndex);
        this.newTileType = findNewTileType();
        this.team = findTeam();
        this.takeEmpty = takeEmpty;
        this.takeEnemy = takeEnemy;
        this.takeFuse = takeFuse;
        this.teamsFuseTile = findTeamsFuse();
    }
    private TileType findTeamsFuse() {
        if (this.team == Team.YELLOW) {
            return YELLOW_FUSE_SNAKE;
        } else if (this.team == Team.RED){
            return RED_FUSE_SNAKE;
        }
        return null;
    }

    @Override
    public void tick() {
        List<Integer> indexList = gridIndex.findAllOfType(tileType);
        for (int index : indexList) {
            int XY[] = gridIndex.getXYOf(index);
            List<Integer> surrounding = super.surroundIndex(XY[0], XY[1]);
            for (int surroundIndex : surrounding) {
                int surroundX = gridIndex.getXYOf(surroundIndex)[0];
                int surroundY = gridIndex.getXYOf(surroundIndex)[1];
                if (surroundX >= 0 && surroundY >= 0) {
                    TileType surroundTileType =  gridIndex.tileAtXY(surroundX, surroundY);
                    if (surroundTileType != this.newTileType
                            && surroundTileType != WALL
                            && surroundTileType != CLASH
                            && surroundTileType != EMPTY
                            && surroundTileType != this.teamsFuseTile) {
                        if (isOppositeNewTile(this.team, surroundTileType)) {
                            if (Math.random() < this.takeEmpty) {
                                gridIndex.addObject(surroundX, surroundY, CLASH);
                            }
                        } else {
                            if (!isTileTeam(this.team, surroundTileType)) {
                                if (Math.random() < this.takeEnemy) {
                                    gridIndex.addObject(surroundX, surroundY, this.newTileType);
                                }
                            }
                        }
                    } else if (surroundTileType == EMPTY) {
                        if (Math.random() < this.takeEmpty) {
                            gridIndex.addObject(surroundX, surroundY, this.newTileType);
                        }
                    } else if (surroundTileType == this.teamsFuseTile) {
                        if (Math.random() < this.takeFuse) {
                            gridIndex.addObject(surroundX, surroundY, this.newTileType);
                        }
                    }
                }
            }
        }
    }
}
