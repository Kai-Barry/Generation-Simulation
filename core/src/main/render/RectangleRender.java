package main.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import main.utility.TileType;

public class RectangleRender {
    protected ShapeRenderer rectangle;
    protected int x;
    protected int y;
    protected float size;
    protected TileType tile;

    public RectangleRender(ShapeRenderer rectangle, int x, int y, float size, TileType tileType) {
        this.rectangle = rectangle;
        this.x = x;
        this.y = y;
        this.size = size;
        this.tile = tileType;
    }
    public void createRectangle() {
        rectangle.begin(ShapeRenderer.ShapeType.Filled);
        switch (this.tile) {
            case EMPTY:
                rectangle.setColor(0.8f, 0.8f, 0.8f, 1);
                break;
            case CLASH:
                rectangle.setColor(0.6f, 0.6f, 0.6f, 1);
                break;
            case WALL:
                rectangle.setColor(0.25f, 0.25f, 0.25f, 1);
                break;
            case YELLOW_GROWER:
                rectangle.setColor(0.976f, 1f, 0.58f, 1);
                break;
            case YELLOW_BASIC:
                rectangle.setColor(0, 0f, 0.6f, 1);
                break;
            case YELLOW_ADVANCED:
                rectangle.setColor(1, 0.9f, 0.2f, 1);
                break;
            case YELLOW_ADVANCED_SNAKE:
                rectangle.setColor(1, 0.4f, 0.2f, 1);
                break;
            case YELLOW_FUSE:
                rectangle.setColor(1, 0.4f, 0.4f, 1);
                break;
            case YELLOW_FUSE_SNAKE:
                rectangle.setColor(0.5f, 0.5f, 0.5f, 1);
                break;
            case RED_BASIC:
                rectangle.setColor(1, 0.1f, 0, 1);
                break;
            case RED_GROWER:
                rectangle.setColor(1f, 0.1f, 0.4f, 0);
                break;
            case RED_ADVANCED:
                rectangle.setColor(1, 0.1f, 0.11f, 1);
                break;
            case RED_ADVANCED_SNAKE:
                rectangle.setColor(1, 0.1f, 0.31f, 1);
                break;
            case RED_FUSE:
                rectangle.setColor(1, 0.1f, 0.51f, 1);
                break;
            case RED_FUSE_SNAKE:
                rectangle.setColor(0.1f, 0.1f, 0.1f, 1);
                break;
            default:
                rectangle.setColor(1, 1, 0.25f, 1);
                break;
        }
        rectangle.rect((x * size) + x * 3, (y * size) + y * 3,  size,size);
        rectangle.end();
    }
}
