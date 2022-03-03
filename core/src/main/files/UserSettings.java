package main.files;

import javax.xml.stream.Location;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.utils.Logger;
import main.GenerationGame;

import java.awt.*;
import java.io.*;
import java.util.Scanner;


public class UserSettings {
    private static final Logger logger = new Logger("User Settings load");

    private static final int windowWidth = 1280;
    private static final int windowHeight = 720;

    private static final String root = "GenerationGame";
    private static final String settingFileName = "settings.txt";

    //Main settings
    private int fps = 60;
    private boolean fullscreen = false;
    private float uiScale = 1f;

    //display Settings
    private boolean vsync = true;
    private int width = 1280;
    private int height = 720;

    public UserSettings() {
        this.loadSettings();
    }

    /**
     * Gets the width
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the Height
     * @return
     */
    public int getHeight() {
        return height;
    }


    /**
     * Sets the fps
     *
     * @param fps
     */
    private void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * Sets if Fullscreen
     *
     * @param fullscreen
     */
    private void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    /**
     * Sets the UI Scale
     *
     * @param uiScale
     */
    private void setUiScale(float uiScale) {
        this.uiScale = uiScale;
    }


    /**
     * sets the Width
     * @param width
     */
    private void setWidth(int width) {
        this.width = width;
    }

    /**
     * sets the height
     *
     * @param height
     */
    private void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the parameters for all the settings
     *
     * @param fps
     * @param fullscreen
     * @param uiScale
     * @param width
     * @param height
     */
    private void setAll(int fps, boolean fullscreen, float uiScale, int width, int height) {
        this.fps = fps;
        this.fullscreen = fullscreen;
        this.uiScale = uiScale;
        this.width = width;
        this.height = height;
    }

    /**
     * Loads files in from the settings file located at the root//settingsFileName
     *
     * Files are in the format of:
     * fps(int)
     * fullscreen(bool)
     * uiScale(float)
     * width(int)
     * height(int)
     */
    private void loadSettings() {
        String path = root + File.separator + settingFileName;
        try {
            File settingsFile = new File(path);
            Scanner lineReader = new Scanner(settingsFile);

            int readFps = 0;
            boolean readFullscreen = false;
            float readUiScale = 0f;
            int readWidth = 0;
            int readHeight = 0;

            int lineCount = 0;
            while (lineReader.hasNextLine()) {
                String line = lineReader.nextLine();
                switch (lineCount) {
                    case 0:
                        try {
                            readFps = Integer.valueOf(line);
                        } catch (NumberFormatException nfe) {
                                throw new FileNotFoundException();
                        }
                        break;
                    case 1:
                        if (!line.isEmpty()) {
                            readFullscreen = Boolean.valueOf(line);
                        } else {
                            throw new FileNotFoundException();
                        }
                        break;
                    case 2:
                        try {
                            readUiScale = Float.valueOf(line);
                        } catch (NumberFormatException nfe) {
                            throw new FileNotFoundException();
                        }
                        break;
                    case 3:
                        try {
                            readWidth = Integer.valueOf(line);
                        } catch (NumberFormatException nfe) {
                            throw new FileNotFoundException();
                        }
                        break;
                    case 4:
                        try {
                            readHeight = Integer.valueOf(line);
                        } catch (NumberFormatException nfe) {
                            throw new FileNotFoundException();
                        }
                        break;
                }
                if (lineCount > 4)  {
                    throw new FileNotFoundException();
                } else if(lineCount == 4 && validSettings(readFps, readUiScale, readWidth, readHeight)) {
                    this.setAll(readFps, readFullscreen, readUiScale, readWidth, readHeight);
                }
                lineCount++;
            }
        } catch (FileNotFoundException fnfe) {
            logger.debug("No existing logger settings file found or is in wrong format");
        }
    }

    private void writeSettings() {
        String path = root + File.separator + settingFileName;
        try {
            File settingsFile = new File(path);
            Writer lineWriter = new FileWriter(settingsFile);
            lineWriter.write(String.format("%d\n", this.fps));
            lineWriter.write(String.format("%b\n", this.fullscreen));
            lineWriter.write(String.format("%f\n", this.uiScale));
            lineWriter.write(String.format("%d\n", this.width));
            lineWriter.write(String.format("%d\n", this.height));
        } catch (IOException e) {
            logger.debug("failed to write to the settings");
        }
    }

    /**
     * Validates if the values given are within range for the settings
     * @param readFps
     * @param readUiScale
     * @param readWidth
     * @param readHeight
     * @return
     */
    private boolean validSettings(int readFps, float readUiScale, int readWidth, int readHeight) {
        if (readFps <= 0 || readFps > 1000) {
            return false;
        }
        if (readUiScale <= 0 || readUiScale > 10) {
            return false;
        }
        if (readWidth <= 0 || readWidth > 7680) {
            return false;
        }
        if (readHeight <= 0 || readHeight > 4320) {
            return false;
        }
        return true;
    }

    public void applySettings() {
        Gdx.graphics.setForegroundFPS(this.fps);
        Gdx.graphics.setVSync(this.vsync);

        if (this.fullscreen) {
            DisplayMode displayMode = findMatching(this.height, this.width, this.fps);
            if (displayMode == null) {
                displayMode = Gdx.graphics.getDisplayMode();
            }
            Gdx.graphics.setFullscreenMode(displayMode);
        } else {
            Gdx.graphics.setWindowedMode(this.width, this.height);
        }
    }

    private static DisplayMode findMatching(int height, int width, int refreshRate) {
        for (DisplayMode displayMode : Gdx.graphics.getDisplayModes()) {
            if (displayMode.refreshRate == refreshRate
                    && displayMode.height == height
                    && displayMode.width == width) {
                return displayMode;
            }
        }
        return null;
    }

}
