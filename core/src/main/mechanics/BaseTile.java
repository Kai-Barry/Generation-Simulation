package main.mechanics;

import main.utility.TileType;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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
     * |0|1|2|
     * |3|x|5|
     * |6|7|8|
     *
     *  the array will include a -1 if there is no index in any of the direction
     * @param x
     * @param y
     * @return
     */
    protected List<Integer> SurroundIndex(int x, int y) {
        List<Integer> surroundingIndex = new ArrayList<>();
        int index = gridIndex.GetIndexOf(x, y);
        if (index != -1) {
            surroundingIndex.set(0, gridIndex.GetIndexOf(x - 1, y - 1));
            surroundingIndex.set(0, gridIndex.GetIndexOf(x, y - 1));
            surroundingIndex.set(0, gridIndex.GetIndexOf(x + 1, y - 1));
            surroundingIndex.set(0, gridIndex.GetIndexOf(x - 1, y));
            surroundingIndex.set(0, gridIndex.GetIndexOf(x, y));
            surroundingIndex.set(0, gridIndex.GetIndexOf(x + 1, y));
            surroundingIndex.set(0, gridIndex.GetIndexOf(x - 1, y + 1));
            surroundingIndex.set(0,gridIndex.GetIndexOf(x, y + 1));
            surroundingIndex.set(0,gridIndex.GetIndexOf(x + 1, y + 1));
        } else {
            return null;
        }
        return surroundingIndex;
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
}
