package main.mechanics;

import main.utility.Team;
import main.utility.TileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.utility.TileType.*;

public class BasicAlgorithm extends BaseTile {
    private final TileType newTileType;
    private final Team team;
    private final int maxTile;
    public BasicAlgorithm(TileType tileType, GridIndex gridIndex, int maxTile) {
        super(tileType, gridIndex);
        this.newTileType = findNewTileType();
        this.team = super.findTeam();
        this.maxTile = maxTile;
    }

    @Override
    public void tick() {
        List<Integer> indexList = gridIndex.findAllOfType(tileType);
        for (int index : indexList) {
            int XY[] = gridIndex.getXYOf(index);
            List<Integer> surroundingIndex = super.surroundIndex(XY[0], XY[1]);
            List<Boolean> surroundingTeam = super.surroundIsTeam(XY[0], XY[1], team);
            /*for (int i = 0; i < surroundingTeam.size(); i++) {
                System.out.print(String.format("%d : %b\n",i, surroundingTeam.get(i)));
            }*/
            if (surroundingTeam.get(1)
                    || surroundingTeam.get(3)
                    || surroundingTeam.get(5)
                    || surroundingTeam.get(7)) {
                int counter = 0;
                List<Integer> diagonals= new ArrayList<Integer>(Arrays.asList(0,2,6,8));
                for (int surroundIndex : surroundingIndex) {
                    int surroundX = gridIndex.getXYOf(surroundIndex)[0];
                    int surroundY = gridIndex.getXYOf(surroundIndex)[1];
                    int random = (int)(Math.random() * 4);
                    if (surroundX >= 0 && surroundY >= 0) {
                        TileType surroundTile = gridIndex.tileAtXY(surroundX ,surroundY);
                        if (counter == 0 && surroundingTeam.get(1)) {
                            if (!isOppositeNewTile(this.team, surroundTile)) {
                                if (gridIndex.findAllOfType(this.tileType).size() < maxTile) {
                                    gridIndex.addObject(surroundX, surroundY, this.newTileType);
                                }
                            } else if (isOppositeNewTile(this.team, surroundTile)) {
                                gridIndex.addObject(surroundX, surroundY, CLASH);
                            }
                        } else if (counter == 2 && surroundingTeam.get(3)) {
                            if (!isOppositeNewTile(this.team, surroundTile)) {
                                if (gridIndex.findAllOfType(this.tileType).size() < maxTile) {
                                    gridIndex.addObject(surroundX, surroundY, this.newTileType);
                                }
                            } else {
                                gridIndex.addObject(surroundX, surroundY, CLASH);
                            }
                        } else if (counter == 6 && surroundingTeam.get(5)) {
                            if (!isOppositeNewTile(this.team, surroundTile)) {
                                if (gridIndex.findAllOfType(this.tileType).size() < maxTile) {
                                    gridIndex.addObject(surroundX, surroundY, this.newTileType);
                                }
                            } else {
                                gridIndex.addObject(surroundX, surroundY, CLASH);
                            }
                        } else if (counter == 8 && surroundingTeam.get(7)) {
                            if (!isOppositeNewTile(this.team, surroundTile)) {
                                if (gridIndex.findAllOfType(this.tileType).size() < maxTile) {
                                    gridIndex.addObject(surroundX, surroundY, this.newTileType);
                                }
                            } else {
                                gridIndex.addObject(surroundX, surroundY, CLASH);
                            }
                        }
                    }
                    counter++;
                }
            }
        }
    }
}
