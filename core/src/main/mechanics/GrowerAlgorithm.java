package main.mechanics;

import main.utility.Team;
import main.utility.TileType;

import java.util.List;

import static main.utility.TileType.*;
import static main.utility.Team.*;

public class GrowerAlgorithm extends BaseTile {
    private final TileType newTileType;
    private final Team team;
    private final float takeEmpty;
    private final float takeEnemy;
    public GrowerAlgorithm(TileType tileType, GridIndex gridIndex, float takeEmpty, float takeEnemy) {
        super(tileType, gridIndex);
        this.newTileType = findNewTileType();
        this.team = findTeam();
        this.takeEmpty = takeEmpty;
        this.takeEnemy = takeEnemy;
    }

    @Override
    public void tick() {
        List<Integer> indexList = gridIndex.FindAllOfType(tileType);
        for (int index : indexList) {
            int XY[] = gridIndex.GetXYOf(index);
            List<Integer> surrounding = super.surroundIndex(XY[0], XY[1]);
            for (int surroundIndex : surrounding) {
                int surroundX = gridIndex.GetXYOf(surroundIndex)[0];
                int surroundY = gridIndex.GetXYOf(surroundIndex)[1];
                if (surroundX >= 0 && surroundY >= 0) {
                    TileType surroundTileType =  gridIndex.TileAtXY(surroundX, surroundY);
                    if (surroundTileType != this.newTileType
                            && surroundTileType != WALL
                            && surroundTileType != CLASH
                            && surroundTileType != EMPTY) {
                        if (isOppositeNewTile(this.team, surroundTileType)) {
                            if (Math.random() < this.takeEmpty) {
                                gridIndex.addObject(surroundX, surroundY, CLASH);
                            }
                        } else {
                            if (!isTileTeamExBasic(this.team, surroundTileType)) {
                                if (Math.random() < this.takeEnemy) {
                                    gridIndex.addObject(surroundX, surroundY, this.newTileType);
                                }
                            }
                        }
                    } else if (surroundTileType == EMPTY) {
                        if (Math.random() < this.takeEmpty) {
                            gridIndex.addObject(surroundX, surroundY, this.newTileType);
                        }
                    }
                }
            }
        }
    }
}
