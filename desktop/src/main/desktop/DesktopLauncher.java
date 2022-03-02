package main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import main.GenerationGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Generation Simulator";
		config.foregroundFPS = 120;
		config.backgroundFPS = 45;
		new LwjglApplication(new GenerationGame(), config);
	}
}
