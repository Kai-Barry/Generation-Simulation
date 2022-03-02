package main.files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class FileHandler {
    static final Json json = new Json();

    public static class Settings {
        public int fps = 60;
        public boolean fullscreen = true;
        public boolean vsync = true;
    }

}
