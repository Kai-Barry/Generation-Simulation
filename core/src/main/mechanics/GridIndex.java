package main.mechanics;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.utility.TileType;

import java.util.ArrayList;
import java.util.List;

public class GridIndex {
    private int gridX;
    private int gridY;
    private List<List<TileType>> objectIndex;
    private GridIndex(int x, int y) {
        this.gridX = x;
        this.gridY = y;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setupIndex() {
        objectIndex= new ArrayList<>();
        if (checkXY()) {
            for (int x = 0; x <= this.gridX; x++) {
                objectIndex.add(new ArrayList<TileType>());
                for (int y = 0; y <= this.gridX; y++) {
                    objectIndex.get(y).add(TileType.EMPTY);
                }
            }
        }
    }
    private Boolean checkXY() {
        if (this.gridY <= 3 || this.gridX <= 3) {
            System.out.println("NOT GOOD");
            return false;
        }
        return true;
    }

    public Integer GetIndexOf(int x, int y) {
        if (x > gridX || x < 0 || y > gridY || y < 0) {
            return -1;
        }
        return x + (y * this.gridY);
    }

    /**
     * Coverts index to x and y coordinates
     * @param index
     * @return
     */
    public int[] coordinateToIndex(int index) {
        int coordinates[]= new int[2];
        if (index > gridY*gridX) {
            coordinates[0] = -1;
            coordinates[1] = -1;
            return coordinates;
        }
        int x = index;
        int y = 0;
        while (x > gridX) {
            x =- gridY;
            y++;
        }
        coordinates[0] = x;
        coordinates[1] = y;
        return  coordinates;
    }

    public List<List<TileType>> getObjectIndex() {
        return objectIndex;
    }

    private void addObjectToIndex(int x, int y, TileType tileType) {
        this.objectIndex.get(x).set(y,tileType);
    }
}
