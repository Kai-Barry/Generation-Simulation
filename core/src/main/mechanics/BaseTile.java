package main.mechanics;

import main.utility.Team;
import main.utility.TileType;

import java.util.ArrayList;
import java.util.List;

import static main.utility.Team.RED;
import static main.utility.Team.YELLOW;
import static main.utility.TileType.*;

public abstract class BaseTile {
    protected TileType tileType;
    protected GridIndex gridIndex;
    protected BaseTile(TileType tileType, GridIndex gridIndex) {
        this.tileType = tileType;
        this.gridIndex = gridIndex;
    }

    /**
     * returns the index of the surrounding tiles
     *  _ _ _
     * |6|7|8|
     * |3|x|5|
     * |0|1|2|
     *
     *  the array will include a -1 if there is no index in any of the direction
     * @param x
     * @param y
     * @return
     */
    protected List<Integer> surroundIndex(int x, int y) {
        List<Integer> surroundingIndex = new ArrayList<>();
        int index = gridIndex.GetIndexOf(x, y);
        int[][] surroundingXY = getSurroundingXY(x, y);
        if (index != -1) {
            for (int XY = 0; XY < surroundingXY.length; XY++) {
                surroundingIndex.add(gridIndex.GetIndexOf(surroundingXY[XY][0], surroundingXY[XY][1]));
            }
        } else {
            return null;
        }
        return surroundingIndex;
    }

    protected int[][] getSurroundingXY(int x, int y) {
        int[][] surroundingXY = {{x - 1, y - 1}, {x, y - 1}, {x + 1, y - 1},
                {x - 1, y}, {x, y}, {x + 1, y}, {x - 1, y + 1} ,{x, y + 1}, {x + 1, y + 1}};
        return surroundingXY;
    }


    protected List<Boolean> surroundIsTeam(int x, int y, Team team) {
        List<Boolean> surroundingTeam = new ArrayList<>();
        int index = gridIndex.GetIndexOf(x, y);
        int[][] surroundingXY = getSurroundingXY(x, y);
        if (index != -1) {
            for (int XY = 0; XY < surroundingXY.length; XY++) {
                if (gridIndex.GetIndexOf(surroundingXY[XY][0], surroundingXY[XY][1]) != -1) {
                    TileType tile = gridIndex.tileAtXY(surroundingXY[XY][0], surroundingXY[XY][1]);
                    if (isTileTeam(team, tile)) {
                        surroundingTeam.add(true);
                    }
                    else {
                        surroundingTeam.add(false);
                    }
                } else {
                    surroundingTeam.add(false);
                }
            }
        } else {
            return null;
        }
        return surroundingTeam;
    }

    protected Boolean isTileTeamExBasic(Team team, TileType tile) {
        if (team == YELLOW) {
            if (tile == YELLOW_GROWER
                    || tile == YELLOW_ADVANCED) {
                return true;

            } else {
                return false;
            }
        } else if (team == RED) {
            if (tile == RED_GROWER
                    || tile == RED_ADVANCED) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    protected Boolean isTileTeam(Team team, TileType tile) {
        if (team == YELLOW) {
            if (tile == YELLOW_GROWER
                    || tile == YELLOW_ADVANCED
                    || tile == YELLOW_BASIC
                    || tile == YELLOW_ADVANCED_SNAKE) {
                return true;

            } else {
                return false;
            }
        } else if (team == RED) {
            if (tile == RED_GROWER
                    || tile == RED_ADVANCED
                    || tile == RED_BASIC
                    || tile == RED_ADVANCED_SNAKE) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    protected Team findTeam() {
        if (tileType == YELLOW_GROWER
                || tileType == YELLOW_ADVANCED
                || tileType == YELLOW_FUSE
                || tileType == YELLOW_BASIC
                || tileType == YELLOW_BASIC_NEW
                || tileType == YELLOW_GROWER_NEW) {
            return YELLOW;
        } else if (tileType == RED_GROWER
                || tileType == RED_ADVANCED
                || tileType == RED_FUSE
                || tileType == RED_BASIC
                || tileType == RED_BASIC_NEW
                || tileType == RED_GROWER_NEW) {
            return RED;
        }
        return YELLOW;
    }

    protected TileType findNewTileType() {
        switch (this.tileType) {
            case YELLOW_GROWER:
                return YELLOW_GROWER_NEW;
            case RED_GROWER:
                return RED_GROWER_NEW;
            case YELLOW_BASIC:
                return YELLOW_BASIC_NEW;
            case RED_BASIC:
                return RED_BASIC_NEW;
            case YELLOW_ADVANCED:
                return null;
            case GASOLINE:
                return GASOLINE_NEW;
        }
        return CLASH;
    }


    protected boolean isOppositeNewTile(Team team, TileType tile) {
        if (team == YELLOW) {
            if (tile == RED_GROWER_NEW || tile == RED_BASIC_NEW) {
                return true;
            }
        } else if (team == RED) {
            if (tile == YELLOW_GROWER_NEW || tile == YELLOW_BASIC_NEW) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns the index of the surrounding tiles
     *  _ _ _
     * |0|1|2|
     * |3|x|5|
     * |6|7|8|
     *
     *  the array will include a -1 if there is no index in any of the direction
     * @param x
     * @param y
     * @return
     */
    protected List<Integer> SurroundIndexOld(int x, int y) {
        List<Integer> surroundingIndex = new ArrayList<>();
        int index = gridIndex.GetIndexOf(x, y);
        if (index != -1) {
            int RowAbove = index - gridIndex.getGridX();
            int RowBelow = index + gridIndex.getGridX();
            if (y > 0 ) {
                surroundingIndex.set(0, RowAbove - 1);
                surroundingIndex.set(1, RowAbove);
                surroundingIndex.set(2, RowAbove + 1);
            } else {
                surroundingIndex.set(0, -1);
                surroundingIndex.set(1, -1);
                surroundingIndex.set(2, -1);
            }
            surroundingIndex.set(3, index - 1);
            surroundingIndex.set(4, index);
            surroundingIndex.set(5, index + 1);
            if (y < gridIndex.getGridY()) {
                surroundingIndex.set(6, RowBelow - 1);
                surroundingIndex.set(7, RowBelow);
                surroundingIndex.set(8, RowBelow + 1);
            } else {
                surroundingIndex.set(6, -1);
                surroundingIndex.set(7, -1);
                surroundingIndex.set(8, -1);
            }
            if (x == 0) {
                surroundingIndex.set(0, -1);
                surroundingIndex.set(3, -1);
                surroundingIndex.set(6, -1);
            }
            if (x == gridIndex.getGridX()) {
                surroundingIndex.set(2, -1);
                surroundingIndex.set(5, -1);
                surroundingIndex.set(8, -1);
            }
        } else {
            return null;
        }
        return surroundingIndex;
    }

    abstract void tick();
}
