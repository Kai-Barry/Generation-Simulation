package main.mechanics;

import main.utility.TileType;

import java.util.ArrayList;
import java.util.List;

public class GridIndex {
    private int gridX;
    private int gridY;
    private List<List<TileType>> objectIndex;
    private List<List<TileType>> objectIndexClone;

    //Controllers
    private GrowerAlgorithm yellowGrowerAlgorithm;
    private GrowerAlgorithm redGrowerAlgorithm;
    private BasicAlgorithm yellowBasicAlgorithm;
    private BasicAlgorithm redBasicAlgorithm;
    private AdvancedAlgorithm yellowAdvancedAlgorithm;
    private AdvancedAlgorithm redAdvancedAlgorithm;
    private AdvancedAlgorithm yellowFuseAlgorithm;
    private AdvancedAlgorithm redFuseAlgorithm;

    public GridIndex(int x, int y) {
        this.gridX = x;
        this.gridY = y;
        this.yellowGrowerAlgorithm = new GrowerAlgorithm(TileType.YELLOW_GROWER, this, 0.01f,0.01f, 0.7f);
        this.redGrowerAlgorithm = new GrowerAlgorithm(TileType.RED_GROWER, this, 0.01f,0.01f, 0.7f);
        this.yellowBasicAlgorithm = new BasicAlgorithm(TileType.YELLOW_BASIC, this, 10);
        this.redBasicAlgorithm = new BasicAlgorithm(TileType.RED_BASIC, this, 10);
        this.yellowAdvancedAlgorithm = new AdvancedAlgorithm(TileType.YELLOW_ADVANCED, this, 5);
        this.redAdvancedAlgorithm = new AdvancedAlgorithm(TileType.RED_ADVANCED, this, 5);
        this.yellowFuseAlgorithm = new AdvancedAlgorithm(TileType.YELLOW_FUSE, this, 5);
        this.redFuseAlgorithm = new AdvancedAlgorithm(TileType.RED_FUSE,this,5);
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
        if (x >=gridX || x < 0 || y >=gridY || y < 0) {
            return -1;
        }
        return x + (y * this.gridX);
    }

    /**
     * Coverts index to x and y coordinates
     * @param index
     * @return
     */
    public int[] getXYOf(int index) {
        int coordinates[]= new int[2];
        if (index > gridY*gridX || index < 0) {
            coordinates[0] = -1;
            coordinates[1] = -1;
            return coordinates;
        }
        int x = index;
        int y = 0;
        while (x >= gridX) {
            x -= gridX;
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

    public TileType tileAtXY(int x, int y) {
        return this.objectIndex.get(y).get(x);
    }

    public TileType TileAtXYClone(int x, int y) {
        return this.objectIndexClone.get(y).get(x);
    }

    public List<Integer> findAllOfType(TileType tileType) {
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

    public void convertNewTiles() {
        int y = 0;
        for (List<TileType> list : this.objectIndex) {
            int x = 0;
            for (TileType tile : list) {
                if (tile == TileType.RED_GROWER_NEW) {
                    this.addObject(x, y, TileType.RED_GROWER);
                } else if (tile == TileType.YELLOW_GROWER_NEW) {
                    this.addObject(x, y, TileType.YELLOW_GROWER);
                } else if (tile == TileType.YELLOW_BASIC_NEW) {
                    this.addObject(x, y, TileType.YELLOW_BASIC);
                } else if (tile == TileType.RED_BASIC_NEW) {
                    this.addObject(x, y, TileType.RED_BASIC);
                } else if (tile == TileType.CLASH) {
                    this.addObject(x, y, TileType.EMPTY);
                }
                x++;
            }
            y++;
        }
    }

    public void tick() {
        if (Math.random() > 0.5) {
            redBasicAlgorithm.tick();
            yellowBasicAlgorithm.tick();
        } else {
            yellowBasicAlgorithm.tick();
            redBasicAlgorithm.tick();
        }
        if (Math.random() > 0.5) {
            yellowGrowerAlgorithm.tick();
            redGrowerAlgorithm.tick();
        } else {
            redGrowerAlgorithm.tick();
            yellowGrowerAlgorithm.tick();
        }
        if (Math.random() > 0.5) {
            this.redAdvancedAlgorithm.tick();
            this.yellowAdvancedAlgorithm.tick();
        } else {
            this.yellowAdvancedAlgorithm.tick();
            this.redAdvancedAlgorithm.tick();
        }
        if (Math.random() > 0.5) {
            this.redFuseAlgorithm.tick();
            this.yellowFuseAlgorithm.tick();
        } else {
            this.yellowFuseAlgorithm.tick();
            this.redFuseAlgorithm.tick();
        }

        this.convertNewTiles();
    }
}
