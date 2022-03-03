package main.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.Logger;
import main.GenerationGame;

import javax.swing.*;

public class SettingsScreen extends ScreenAdapter {
    private static final Logger logger = new Logger("Game Screen");
    private final GenerationGame game;
    private static Renderer renderer;

    public SettingsScreen(GenerationGame game) {
        this.game = game;
    }
}
