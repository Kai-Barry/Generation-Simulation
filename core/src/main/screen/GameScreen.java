package main.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import main.utility.TileMapHandler;
import main.utility.TileType;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

import static main.utility.Constants.PPM;

public class GameScreen extends ScreenAdapter {
    private static final Logger logger = new Logger("Game Screen");
    private final GenerationGame game;

    //Rendering
    private OrthographicCamera camera;
    private static Box2DDebugRenderer debugRenderer;
    private static World world;
    private static SpriteBatch batch;
    private List<ShapeRenderer> shapeRenderer;

    //Game Logic
    private GridIndex gridIndex;

    //Rendering 2
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHandler tileMapHandler;

    public GameScreen(GenerationGame game, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
        logger.debug("Initialising main game screen services");
        this.game = game;
        this.camera = camera;
        this.debugRenderer = new Box2DDebugRenderer();
        this.world = new World(new Vector2(0,0), false);
        this.batch = new SpriteBatch();
        this.tileMapHandler = new TileMapHandler(this);
        this.orthogonalTiledMapRenderer = tileMapHandler.setupMap();
        this.gridIndex = new GridIndex(20,10);
        this.gridIndex.setupIndex();
        this.gridIndex.addObject(19,5,TileType.YELLOW_GROWER);
        this.gridIndex.addObject(0,5,TileType.RED_GROWER);
        this.shapeRenderer = createShapeRenders();
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
//        gridIndex.tick();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                gridIndex.tick();

            }
        }
    }

    private void cameraUpdate() {
        camera.position.set(new Vector3(0,0,0));
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(.25f,.25f,.25f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        this.makeGrid(30);

        batch.begin();
        //renders objects

        batch.end();
        debugRenderer.render(world, camera.combined.scl(PPM));
    }

    public float findEdgeToEdgeSize() {
        float x = this.gridIndex.getGridX();
        return (1280 / x) - x;
    }



    public void makeGrid(int size) {
        int width = gridIndex.getGridX();
        int height = gridIndex.getGridY();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TileType tile = gridIndex.TileAtXY(x, y);
                ShapeRenderer rectangle = shapeRenderer.get(x + y * width);
                RectangleRender rectangleRender = new RectangleRender(rectangle, x, y, this.findEdgeToEdgeSize(), tile);
                rectangleRender.createRectangle();
            }
        }
    }

}
