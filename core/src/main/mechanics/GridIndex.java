package main.mechanics;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.utility.TileType;

import java.util.ArrayList;
import java.util.List;

public class GridIndex {
    private int gridX;
    private int gridY;
    private List<List<TileType>> objectIndex;
    private List<List<TileType>> objectIndexClone;

    //Controllers
    private GrowerAlgorithm YellowGrowerAlgorithm;

    public GridIndex(int x, int y) {
        this.gridX = x;
        this.gridY = y;
        this.YellowGrowerAlgorithm = new GrowerAlgorithm(TileType.YELLOW_GROWER, this);
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
            for (int y = 0; y < this.gridY; y++) {
                objectIndex.add(new ArrayList<TileType>());
                for (int x = 0; x < this.gridX; x++) {
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
        return x + (y * this.gridX);
    }

    /**
     * Coverts index to x and y coordinates
     * @param index
     * @return
     */
    public int[] GetXYOf(int index) {
        int coordinates[]= new int[2];
        if (index > gridY*gridX || index < 0) {
            coordinates[0] = -1;
            coordinates[1] = -1;
            return coordinates;
        }
        int x = index;
        int y = 0;
        while (x > (gridX - 1)) {
            x = x - gridX;
            y++;
        }
        coordinates[0] = x;
        coordinates[1] = y;
        return  coordinates;
    }

    private List<List<TileType>> getObjectIndex() {
        return objectIndex;
    }

    public void addObjectToClone(int x, int y, TileType tileType) {
        this.objectIndexClone.get(y).set(x,tileType);
    }
    public void addObject(int x, int y, TileType tileType) {
        this.objectIndex.get(y).set(x,tileType);
    }

    public TileType TileAtXY(int x, int y) {
        return this.objectIndex.get(y).get(x);
    }

    public TileType TileAtXYClone(int x, int y) {
        return this.objectIndexClone.get(y).get(x);
    }

    public List<Integer> FindAllOfType(TileType tileType) {
        List<Integer> typeAll = new ArrayList<>();
        int index = 0;
        for (List<TileType> list : this.objectIndex) {
            for (TileType tile : list) {
                if (tile == tileType) {
                    typeAll.add(index);
                }
                index++;
            }
        }
        return typeAll;
    }

    public void tick() {
        YellowGrowerAlgorithm.tick();
    }
}
