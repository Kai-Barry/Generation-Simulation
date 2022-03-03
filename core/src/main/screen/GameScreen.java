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
import main.utility.TileMapHandler;

import javax.swing.*;

import static main.utility.Constants.PPM;

public class GameScreen extends ScreenAdapter {
    private static final Logger logger = new Logger("Game Screen");
    private final GenerationGame game;
    private OrthographicCamera camera;
    private static Box2DDebugRenderer debugRenderer;
    private static World world;
    private static SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

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
        this.shapeRenderer = shapeRenderer;
    }
    private void update() {
        world.step(1/60f,6,2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {

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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(100,100,100,100);
        shapeRenderer.end();

        batch.begin();
        //renders objects

        batch.end();
        debugRenderer.render(world, camera.combined.scl(PPM));
    }

    public void makeGrid(int x, int y, int size) {
        if (x > 0 && y > 0) {
            for (int width = 0; x < width; width++) {
                for (int height = 0; y < height; height++) {
                    float xLoc = width * size;
                    float yLoc = height * size;

                }
            }
        }
    }

}
