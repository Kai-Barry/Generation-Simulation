package main.mechanics;

import main.utility.TileType;

import java.util.List;

public class GasolineAlgorithm extends BaseTile {
    private final float spreadRate;
    private int ticks;
    private final int size;
    private boolean sizeReached;

    public GasolineAlgorithm(TileType tileType, GridIndex gridIndex, float spreadRate, int size) {
        super(tileType, gridIndex);
        this.spreadRate = spreadRate;
        this.ticks = 1;
        this.size = size;
        this.sizeReached = false;
    }

    private boolean isTurn() {
        if (!sizeReached) {
            int amount = gridIndex.findAllOfType(tileType).size();
            if (amount >= size || amount == 0) {
                sizeReached = true;
                return false;
            }
        } else {
            return false;
        }
        if (ticks % spreadRate == 0) {
            ticks++;
            return true;
        }
        ticks++;
        return false;
    }

    @Override
    void tick() {
        if (isTurn()) {
            List<Integer> indexList = gridIndex.findAllOfType(tileType);
            for (int index : indexList) {
                int XY[] = gridIndex.getXYOf(index);
                List<Integer> surrounding = super.surroundIndex(XY[0], XY[1]);
                for (int surroundIndex : surrounding) {
                    int surroundX = gridIndex.getXYOf(surroundIndex)[0];
                    int surroundY = gridIndex.getXYOf(surroundIndex)[1];
                    if (surroundX >= 0 && surroundY >= 0) {
                        TileType surroundTileType =  gridIndex.tileAtXY(surroundX, surroundY);
                        if (surroundTileType == TileType.EMPTY) {
                            gridIndex.addObject(surroundX, surroundY, TileType.GASOLINE_NEW);
                        }
                    }
                }
            }
        }
    }
}
