package main.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import main.GenerationGame;

import javax.swing.*;

public class MainMenuScreen extends ScreenAdapter {
    private static final Logger logger = new Logger("Mainmenu Screen");
    private final GenerationGame game;
    private static Renderer renderer;


    public MainMenuScreen(GenerationGame game) {
        this.game = game;

    }

}
