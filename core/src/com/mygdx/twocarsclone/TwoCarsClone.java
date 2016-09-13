package com.mygdx.twocarsclone;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.twocarsclone.game.Assets;
import com.mygdx.twocarsclone.game.WorldController;
import com.mygdx.twocarsclone.game.WorldRenderer;
import com.mygdx.twocarsclone.screens.MenuScreen;

public class TwoCarsClone extends Game implements ApplicationListener {

	public static final String TAG = TwoCarsClone.class.getName();

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.log(TAG, "Initializing game objects");
		Assets.instance.init(new AssetManager());

		setScreen(new MenuScreen(this));
	}
}
