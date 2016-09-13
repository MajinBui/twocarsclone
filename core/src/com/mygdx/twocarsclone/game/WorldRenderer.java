package com.mygdx.twocarsclone.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.twocarsclone.util.Constants;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * Created by Van on 2016-02-28.
 */

public class WorldRenderer implements MediaDisposer.Disposable{
    public static final String TAG = WorldRenderer.class.getName();
    // Camera and viewports
    private OrthographicCamera cameraGUI;
    private OrthographicCamera camera;
    private Viewport viewportGUI;
    private Viewport viewport;

    //  Data to render
    private WorldController worldController;
    private SpriteBatch batch;

    /**
     *  Should always be used
     * @param worldController the WorldController the rendered will receive game objects from
     */
    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }
    /**
     * Initializes the WorldRenderer.  Useful to reinitialize the object without recreating it.
     */
    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
        viewport.apply();

        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        viewportGUI = new ExtendViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT, cameraGUI);
        viewportGUI.apply();
        cameraGUI.position.set(0, 0, 0);
    }

    /**
     * Determines the order the game draws the game objects
     */
    public void render() {
        renderWorld(batch);
        renderGui(batch);
    }

    /**
     * Renders the game world to the SpriteBatch given
     * @param batch the SpriteBatch to render the world to
     */
    private void renderWorld (SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    /**
     * Resizes the viewport if the screen size changes
     * @param width the new screen width
     * @param height the new screen height
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewportGUI.update(width, height);
    }

    /**
     * Renders the game score to the screen
     * @param batch the SpriteBatch to render the score to
     */
    private void renderGuiScore (SpriteBatch batch) {
        float x = -15;
        float y = -15;
        Assets.instance.fonts.defaultBig.draw(batch,
                "" + worldController.score,
                -Constants.VIEWPORT_GUI_WIDTH / 2 + Constants.VIEWPORT_GUI_HEIGHT * .01f,
                Constants.VIEWPORT_GUI_HEIGHT / 2 - Constants.VIEWPORT_GUI_HEIGHT * .01f);
    }

    /**
     * Renders the game time to the screen
     * @param batch the SpriteBatch to render the time to
     */
    private void renderTotalTime(SpriteBatch batch) {
        float x = 0;
        BitmapFont timeFont = Assets.instance.fonts.defaultNormal;
        float y = Constants.VIEWPORT_GUI_HEIGHT/2 - Constants.VIEWPORT_GUI_HEIGHT * .01f;
        timeFont.draw(batch, "TIME " + Math.round(worldController.level.total_time), x, y, 0, Align.center, true);
        timeFont.setColor(1, 1, 1, 1);
    }

    /**
     * Renders the game fps to the screen
     * @param batch the SpriteBatch to render the game fps to
     */
    private void renderGuiFpsCounter (SpriteBatch batch) {
        float x = Constants.VIEWPORT_GUI_WIDTH/2 - Constants.VIEWPORT_GUI_WIDTH*.15f;
        float y = Constants.VIEWPORT_GUI_HEIGHT/2 - Constants.VIEWPORT_GUI_HEIGHT*.01f;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
        // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
        // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
        // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }

    /**
     * Renders the game gui to the screen
     * @param batch the SpriteBatch to render the GUI to
     */
    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        renderGuiScore(batch);
        renderGuiFpsCounter(batch);
        renderTotalTime(batch);
        renderGuiGameOverMessage(batch);
        batch.end();
    }

    /**
     * Renders the game over message to the screen
     * @param batch the SpriteBatch to render the game over message to
     */
    private void renderGuiGameOverMessage (SpriteBatch batch) {
        float x = 0;
        float y = 0;
        if (worldController.isGameOver()) {
            BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            final GlyphLayout layout = new GlyphLayout(fontGameOver, "GAME OVER");
            fontGameOver.draw(batch, "GAME OVER", x, y, 0, Align.center, true);
            fontGameOver.setColor(1, 1, 1, 1);
        }
    }

    /**
     *  Disposes of game objects no longer in use
     */
    @Override
    public void dispose() {
        batch.dispose();
    }
}