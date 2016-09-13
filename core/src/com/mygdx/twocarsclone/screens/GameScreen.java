package com.mygdx.twocarsclone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.twocarsclone.game.WorldController;
import com.mygdx.twocarsclone.game.WorldRenderer;

/**
 * Created by Van on 2016-03-07.
 */
public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    private boolean paused;
    public GameScreen(Game game) {
        super(game);
    }
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(255, 255, 255, 1); // Sets the background color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the screen
        // RENDER BEFORE UPDATE
        worldRenderer.render();
        if (!paused) {
            // Update the game objects
            worldController.update(Gdx.graphics.getDeltaTime());
        }

    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide() {
        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }
}
