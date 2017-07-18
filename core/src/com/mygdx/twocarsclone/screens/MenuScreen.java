package com.mygdx.twocarsclone.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.twocarsclone.util.Constants;

/**
 * Created by Van on 2016-03-07.
 */
public class MenuScreen extends AbstractGameScreen {
    private Stage stage;
    //private OrthographicCamera cameraGUI;
    private Skin skinTwoCars;
    // menu
    private Image imgBackground;
    private Image imgTitle;
    private Image imgSubtitle;
    private Button btnMenuPlay;
    private Button btnMenuOptions;
    // options
    private Window winOptions;
    //private TextButton btnWinOptSave;
    //private TextButton btnWinOptCancel;
    //private CheckBox chkSound;
    //private Slider sldSound;
    //private CheckBox chkMusic;
    //private Slider sldMusic;
    //private SelectBox<CharacterSkin> selCharSkin;
    //private Image imgCharSkin;
    private CheckBox chkShowFpsCounter;

    private static final String TAG = MenuScreen.class.getName();
    public MenuScreen(Game game) {
        super(game);
    }
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        if(Gdx.input.isTouched())
//            game.setScreen(new GameScreen(game));
        stage.act(deltaTime);
        stage.draw();
        //Table.drawDebug(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        //cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        FitViewport viewPort = new FitViewport(Constants.VIEWPORT_GUI_WIDTH,
                Constants.VIEWPORT_GUI_HEIGHT);
        //cameraGUI.position.set(0, 0, 0);
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    private void rebuildStage () {
        skinTwoCars = new Skin(
                Gdx.files.internal(Constants.SKIN_2CARS_UI),
        new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
//        private Image imgBackground;
//        private Image imgTitle;
//        private Image imgSubtitle;
//        private Button btnMenuPlay;
//        private Button btnMenuOptions;
        Table layerBackground = buildBackgroundLayer();
        Table layerObjects = buildObjectsLayer();
        Table layerLogos = buildLogosLayer();
        Table layerControls = buildControlsLayer();
        Table layerOptionsWindow = buildOptionsWindowLayer();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
                Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerBackground);
        stack.add(layerObjects);
        stack.add(layerLogos);
        stack.add(layerControls);
        stage.addActor(layerOptionsWindow);
    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(skinTwoCars, "background");
        //imgBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        layer.add(imgBackground).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight()).expand(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return layer;
    }
    private Table buildObjectsLayer () {
        Table layer = new Table();
        return layer;
    }
    private Table buildLogosLayer () {
        Table layer = new Table();
        layer.top();
        layer.padTop(200f);
        imgTitle = new Image(skinTwoCars, "TwoVansTitle");
        layer.add(imgTitle);
        layer.row().expandY();
        imgSubtitle = new Image(skinTwoCars, "subtitle");
//        layer.add(imgSubtitle).bottom();
        return layer;
    }
    private Table buildControlsLayer () {
        Table layer = new Table();
        btnMenuPlay = new Button(skinTwoCars, "play_button");
        btnMenuPlay.setOrigin(Align.center);
        btnMenuPlay.setTransform(true);
        btnMenuPlay.setScale(0.20f, 0.20f);
        btnMenuPlay.center();
        layer.add(btnMenuPlay);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();
        btnMenuOptions = new Button(skinTwoCars, "options_button");
        //layer.add(btnMenuOptions);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });
        return layer;
    }

    private void onPlayClicked () {
        game.setScreen(new GameScreen(game));
    }
    private void onOptionsClicked () { }

    private Table buildOptionsWindowLayer () {
        Table layer = new Table();
        return layer;
    }
}
