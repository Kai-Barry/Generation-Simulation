package main.utility;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHandler {
    private TiledMap tiledMap;

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("maps/map0.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
}
