package main.utility;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import main.screen.GameScreen;

import java.util.Iterator;

public class TileMapHandler {
    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHandler(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("maps/map0.tmx");
        pasrseMapObjects(tiledMap.getLayers().get("tile").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void pasrseMapObjects(MapObjects mapObjects) {
        Iterator mapObjectsIterator = mapObjects.iterator();
        while (mapObjectsIterator.hasNext()) {
            Object nextObject = mapObjectsIterator.next();
            if (nextObject instanceof PolygonMapObject) {
                createEnemy((PolygonMapObject)nextObject);
            }
        }
    }

    private void createEnemy(PolygonMapObject polygonMapObject) {
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
    }
}
