package main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import main.GenerationGame;
import main.mechanics.GridIndex;
import main.render.RectangleRender;
import main.render.SelectorRender;
import main.utility.TileMapHandler;
import main.utility.TileType;

import java.util.ArrayList;
import java.util.List;

import static main.utility.Constants.PPM;
import static main.utility.TileType.*;

public class GameScreen extends ScreenAdapter {
    private static final Logger logger = new Logger("Game Screen");
    private final GenerationGame game;

    //Rendering
    private OrthographicCamera camera;
    private static Box2DDebugRenderer debugRenderer;
    private static World world;
    private static SpriteBatch batch;
    private static ShapeRenderer colourSelecter;
    private static BitmapFont font;
    private List<ShapeRenderer> shapeRenderer;

    //Game Logic
    private GridIndex gridIndex;

    //Rendering 2
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHandler tileMapHandler;
    private int size;
    private float gapSize;
    private TileType selectedTileType;

    public GameScreen(GenerationGame game, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        logger.debug("Initialising main game screen services");
        this.game = game;
        this.camera = camera;
        this.debugRenderer = new Box2DDebugRenderer();
        this.world = new World(new Vector2(0,0), false);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(Gdx.files.internal("fonts/Dubai.fnt"));
        this.tileMapHandler = new TileMapHandler(this);
        this.orthogonalTiledMapRenderer = tileMapHandler.setupMap();
        this.size = 10;
        this.gapSize = 1;
        this.gridIndex = new GridIndex(80,50);
        this.gridIndex.setupIndex();
        this.shapeRenderer = createShapeRenders();
        this.colourSelecter = new ShapeRenderer();
        this.selectedTileType = TileType.YELLOW_GROWER;
    }

    private List<ShapeRenderer> createShapeRenders() {
        int numShapeRenderer = this.gridIndex.getGridX() * this.gridIndex.getGridY();
        List<ShapeRenderer> shapeRenderers= new ArrayList<>();
        for (int i = 0; i < numShapeRenderer; i++) {
            shapeRenderers.add(new ShapeRenderer());
        }
        return shapeRenderers;
    }

    private void update() {
        world.step(1/60f,6,2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float locX = Gdx.input.getX();
            float locY = Gdx.graphics.getHeight() - Gdx.input.getY();
            int x = (int) Math.floor(locX / (this.size + this.gapSize));
            int y = (int) Math.floor(locY / (this.size + this.gapSize));
            if (x >= 0 && x < this.gridIndex.getGridX() && y >= 0 && y < this.gridIndex.getGridY()) {
                this.gridIndex.addObject(x, y, this.selectedTileType);
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            float locX = Gdx.input.getX();
            float locY = Gdx.graphics.getHeight() - Gdx.input.getY();
            int x = (int)Math.floor(locX / (this.size + this.gapSize));
            int y = (int)Math.floor(locY / (this.size + this.gapSize));
            if (x >= 0 && x < this.gridIndex.getGridX() && y >= 0 && y < this.gridIndex.getGridY()) {
                this.gridIndex.addObject(x, y, EMPTY);
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            gridIndex.tick();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                gridIndex.tick();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                gridIndex.setupIndex();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                cycleSelectedTile();
            }
        }
    }
    private void cycleSelectedTile() {
        TileType cycleTypes[] = {YELLOW_GROWER, YELLOW_BASIC, YELLOW_ADVANCED, YELLOW_FUSE, RED_GROWER, RED_BASIC, RED_ADVANCED, RED_FUSE, GASOLINE, WALL};
        int i = 0;
        while (this.selectedTileType != cycleTypes[i]){
            i++;
        }
        if (i == cycleTypes.length - 1) {
            i = 0;
        } else {
            i++;
        }
        this.selectedTileType = cycleTypes[i];
    }

    private void cameraUpdate() {
        camera.position.set(new Vector3(0,0,0));
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.makeGrid(this.size);
        this.selectorRenderBox();

        batch.begin();
        this.selectorRenderFont();
        batch.end();
        debugRenderer.render(world, camera.combined.scl(PPM));
    }

    private void selectorRenderBox() {
        float x = (Gdx.graphics.getWidth() / 5) * 4;
        float y = (Gdx.graphics.getHeight() / 5) * 4;
        SelectorRender rectangle = new SelectorRender(colourSelecter, x, y, 100, selectedTileType);
        rectangle.createRectangle();
    }

    private void selectorRenderFont() {
        String tileName = TileToName(selectedTileType);
        int x = (0 - (Gdx.graphics.getWidth() / 2)) + ((Gdx.graphics.getWidth() / 5) * 4) + 10 - (tileName.length() * 3);
        int y = (0 - (Gdx.graphics.getHeight() / 2)) + ((Gdx.graphics.getHeight() / 5) * 4) -20;
        font.draw(batch, tileName, x, y);
    }

    private String TileToName(TileType tile) {
        if (tile == YELLOW_GROWER) {
            return "Yellow Team Grower";
        } else if (tile == YELLOW_BASIC) {
            return "Yellow Team Checkered";
        } else if (tile == YELLOW_ADVANCED) {
            return "Yellow Team Snake";
        } else if (tile == YELLOW_FUSE) {
            return "Yellow Team Snake Fuse";
        } else if (tile == RED_GROWER) {
            return "Red Team Grower";
        } else if (tile == RED_BASIC) {
            return "Red Team Checkered";
        } else if (tile == RED_ADVANCED) {
            return "Red Team Snake";
        } else if (tile == RED_FUSE) {
            return "Red Team Snake Fuse";
        } else if (tile == GASOLINE) {
            return "Gasoline";
        } else if (tile == WALL) {
            return "Wall";
        }
        return "Should not reach here";
    }

    private void makeGrid(int size) {
        int width = gridIndex.getGridX();
        int height = gridIndex.getGridY();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TileType tile = gridIndex.tileAtXY(x, y);
                ShapeRenderer rectangle = shapeRenderer.get(x + y * width);
                RectangleRender rectangleRender = new RectangleRender(rectangle, x, y, size, tile, this.gapSize);
                rectangleRender.createRectangle();
            }
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        colourSelecter.dispose();
        for (int i = 0; i >= shapeRenderer.size(); i++) {
            shapeRenderer.get(i).dispose();
        }
    }
}
