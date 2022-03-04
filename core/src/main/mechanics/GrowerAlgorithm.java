package main.mechanics;

import main.utility.Team;
import main.utility.TileType;

import java.util.List;

import static main.utility.TileType.*;
import static main.utility.Team.*;

public class GrowerAlgorithm extends BaseTile {
    private TileType newTileType;
    private TileType oppositeTileType;
    private Team team;
    public GrowerAlgorithm(TileType tileType, GridIndex gridIndex) {
        super(tileType, gridIndex);
        this.newTileType = findNewTileType();
        this.oppositeTileType = findOppositeTileType();
        this.team = findTeam();
    }

    private Team findTeam() {
        if (tileType == YELLOW_GROWER
                || tileType == YELLOW_ADVANCED
                || tileType == YELLOW_BASIC
                || tileType == YELLOW_GROWER_NEW) {
            return YELLOW;
        } else if (tileType == RED_GROWER
                || tileType == RED_ADVANCED
                || tileType == RED_BASIC
                || tileType == RED_GROWER_NEW) {
            return RED;
        }
        return YELLOW;
    }

    private TileType findNewTileType() {
        switch (this.tileType) {
            case YELLOW_GROWER:
                return YELLOW_GROWER_NEW;
            case RED_GROWER:
                return RED_GROWER_NEW;
        }
        return CLASH;
    }

    private TileType findOppositeTileType() {
        switch (this.tileType) {
            case YELLOW_GROWER:
                return RED_GROWER_NEW;
            case RED_GROWER:
                return YELLOW_GROWER_NEW;
        }
        return CLASH;
    }

    @Override
    public void tick() {
        List<Integer> indexList = gridIndex.FindAllOfType(tileType);
        for (int index : indexList) {
            int XY[] = gridIndex.GetXYOf(index);
            List<Integer> surrounding = super.SurroundIndex(XY[0], XY[1]);
            for (int surroundIndex : surrounding) {
                int surroundX = gridIndex.GetXYOf(surroundIndex)[0];
                int surroundY = gridIndex.GetXYOf(surroundIndex)[1];
                if (surroundX >= 0 && surroundY >= 0) {
                    TileType surroundTileType =  gridIndex.TileAtXY(surroundX, surroundY);
                    if (surroundTileType != this.newTileType
                            && surroundTileType != WALL
                            && surroundTileType != CLASH) {
                        if (surroundTileType == this.oppositeTileType) {
                            gridIndex.addObject(surroundX, surroundY, CLASH);
                        } else {
                            if (this.team == YELLOW) {
                                if (surroundTileType != YELLOW_ADVANCED
                                        && surroundTileType != YELLOW_BASIC
                                        && surroundTileType != YELLOW_GROWER
                                        && surroundTileType != YELLOW_GROWER_NEW ) {
                                    gridIndex.addObject(surroundX, surroundY, this.tileType);
                                }
                            } else if (this.team == RED) {
                                if (surroundTileType != RED_ADVANCED
                                        && surroundTileType != RED_BASIC
                                        && surroundTileType != RED_GROWER
                                        && surroundTileType != RED_GROWER_NEW ) {
                                    gridIndex.addObject(surroundX, surroundY, this.tileType);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
