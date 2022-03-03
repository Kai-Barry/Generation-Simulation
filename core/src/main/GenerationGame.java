package main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;

import main.files.UserSettings;
import main.screen.GameScreen;
import main.screen.MainMenuScreen;
import main.screen.SettingsScreen;
import org.graalvm.compiler.lir.LIRInstruction;


public class GenerationGame extends Game {
	private static final Logger logger = new Logger("Game Loading", 0);
	
	@Override
	public void create () {
		logger.debug("Game is Created");
		this.loadSettings();

		Gdx.gl.glClearColor(126 / 255f, 255 / 255f, 138 / 255f, 1);

		setScreen(ScreenType.MAIN_MENU);
	}

	private void loadSettings() {
		logger.debug("settings are being Loaded");
		UserSettings settings = new UserSettings();
		settings.applySettings();
	}

	public void setScreen(ScreenType screenType) {
		logger.debug("Setting Screen");
		Screen currentScreen = getScreen();
		if (currentScreen != null) {
			currentScreen.dispose();
		}
		switch (screenType) {
			case MAIN_MENU:
				setScreen(new MainMenuScreen(this));
				break;
			case SETTINGS:
				setScreen(new SettingsScreen(this));
				break;
			case GAME_SCREEN:
				setScreen(new GameScreen(this));
				break;
			default:
				setScreen(new MainMenuScreen(this));
				break;
		}
	}

	public enum ScreenType {
		MAIN_MENU, SETTINGS, GAME_SCREEN
	}
}
