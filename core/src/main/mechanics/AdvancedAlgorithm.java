package main.mechanics;

import main.utility.Team;
import main.utility.TileType;
import static main.utility.TileType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedAlgorithm extends BaseTile {
    private final Team team;
    private final int ticksPerMove;
    private final TileType snakeTile;
    private int ticks;

    public AdvancedAlgorithm(TileType tileType, GridIndex gridIndex, int ticksPerMove) {
        super(tileType, gridIndex);
        this.team = super.findTeam();
        this.ticksPerMove = ticksPerMove;
        this.snakeTile = this.findSnakeTile();
        this.ticks = 1;
    }

    private TileType findSnakeTile() {
            if (this.team == Team.YELLOW) {
                if (this.tileType == YELLOW_ADVANCED) {
                    return YELLOW_ADVANCED_SNAKE;
                } else if (this.tileType == YELLOW_FUSE){
                    return YELLOW_FUSE_SNAKE;
                }
            } else if (this.team == Team.RED) {
                if (this.tileType == RED_ADVANCED) {
                    return RED_ADVANCED_SNAKE;
                } else if (this.tileType == RED_FUSE){
                    return RED_FUSE_SNAKE;
                }
            }
            return null;
    }

    private boolean isTurn() {
        if (ticks % ticksPerMove == 0) {
            ticks++;
            return true;
        }
        ticks++;
        return false;
    }

    @Override
    public void tick() {
        if (this.tileType == RED_FUSE) {
            if (isTurn()) {
                List<Integer> indexList = gridIndex.findAllOfType(tileType);
                for (int index : indexList) {
                    int XY[] = gridIndex.getXYOf(index);
                    List<Integer> surrounding = super.surroundIndex(XY[0], XY[1]);
                    List<Integer> horizontals = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7));
                    int direction = ((int) (Math.random() * 4)) * 2 + 1;
                    horizontals.remove(horizontals.indexOf(direction));
                    int surroundX = gridIndex.getXYOf(surrounding.get(direction))[0];
                    int surroundY = gridIndex.getXYOf(surrounding.get(direction))[1];
                    boolean snakePlaced = false;
                    while (horizontals.size() > 0) {
                        if (surroundX >= 0 && surroundY >= 0) {
                            TileType surroundTile = gridIndex.tileAtXY(surroundX, surroundY);
                            if (surroundTile != this.snakeTile && surroundTile != WALL) {
                                gridIndex.addObject(surroundX, surroundY, this.tileType);
                                gridIndex.addObject(XY[0], XY[1], this.snakeTile);
                                snakePlaced = true;
                                break;
                            }
                        }
                        direction = horizontals.get((int) (Math.random() * horizontals.size()));
                        surroundX = gridIndex.getXYOf(surrounding.get(direction))[0];
                        surroundY = gridIndex.getXYOf(surrounding.get(direction))[1];
                        horizontals.remove(horizontals.indexOf(direction));
                    }
                    if (!snakePlaced) {
                        List<Integer> overrideHorizontals = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7));
                        while (overrideHorizontals.size() > 0) {
                            int directionOverride = overrideHorizontals.get((int) (Math.random() * overrideHorizontals.size()));
                            overrideHorizontals.remove(overrideHorizontals.indexOf(directionOverride));
                            int surroundXOverride = gridIndex.getXYOf(surrounding.get(direction))[0];
                            int surroundYOverride = gridIndex.getXYOf(surrounding.get(direction))[1];
                            if (surroundXOverride >= 0 && surroundYOverride >= 0) {
                                gridIndex.addObject(surroundX, surroundY, this.tileType);
                                gridIndex.addObject(XY[0], XY[1], this.snakeTile);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
