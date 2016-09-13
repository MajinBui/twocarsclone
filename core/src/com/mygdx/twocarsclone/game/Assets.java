package com.mygdx.twocarsclone.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.twocarsclone.util.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * Created by Van on 2016-02-28.
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    public AssetBlueCar blueCar;
    public AssetRedCar redCar;
    public AssetRedToken redToken;
    public AssetBlueToken blueToken;
    public AssetRedSq redSq;
    public AssetBlueSq blueSq;
    public AssetBackGround backGround;

    public AssetSounds sounds;
    public AssetMusic music;

    public AssetFonts fonts;

    private AssetManager assetManager;
    // prevent the creation of the object
    private Assets () {}

    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
                TextureAtlas.class);
        // SOund and music files
        assetManager.load("sounds/square_collected.wav", Sound.class);
        assetManager.load("sounds/circle_collected.wav", Sound.class);
        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: "
                + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        // create game resource objects
        blueCar = new AssetBlueCar(atlas);
        redCar = new AssetRedCar(atlas);
        redToken = new AssetRedToken(atlas);
        blueToken = new AssetBlueToken(atlas);
        redSq = new AssetRedSq(atlas);
        blueSq = new AssetBlueSq(atlas);
        backGround = new AssetBackGround(atlas);
        fonts = new AssetFonts();

        sounds = new AssetSounds(assetManager);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultBig.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultSmall.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +
                asset.fileName + "'", (Exception) throwable);
    }

    public void error (String filename, Class type, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '"
                + filename + "'", (Exception)throwable);
    }

    public class AssetBlueCar {
        public final AtlasRegion car;

        public AssetBlueCar(TextureAtlas atlas) {
            car = atlas.findRegion("blueCar");
        }
    }
    public class AssetRedCar {
        public final AtlasRegion car;

        public AssetRedCar(TextureAtlas atlas) {
            car = atlas.findRegion("redCar");
        }
    }
    public class AssetRedToken {
        public final AtlasRegion token;

        public AssetRedToken(TextureAtlas atlas) {
            token = atlas.findRegion("redToken");
        }
    }

    public class AssetBlueToken {
        public final AtlasRegion token;

        public AssetBlueToken(TextureAtlas atlas) {
            token = atlas.findRegion("blueToken");
        }
    }
    public class AssetRedSq {
        public final AtlasRegion sq;

        public AssetRedSq(TextureAtlas atlas) {
            sq = atlas.findRegion("redSq");
        }
    }
    public class AssetBlueSq {
        public final AtlasRegion sq;

        public AssetBlueSq(TextureAtlas atlas) {
            sq = atlas.findRegion("blueSq");
        }
    }

    public class AssetBackGround {
        public final AtlasRegion background;

        public AssetBackGround(TextureAtlas atlas) {
            background = atlas.findRegion("twoCarsBG");
        }
    }

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts() {
            defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), false);
            defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), false);
            defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), false);

            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);

            defaultSmall.getRegion().getTexture().setFilter(
                    TextureFilter.Linear, TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(
                    TextureFilter.Linear, TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(
                    TextureFilter.Linear, TextureFilter.Linear);
        }
    }
    public class AssetSounds {
        public final Sound square_collected;
        public final Sound circle_collected;

        public AssetSounds(AssetManager am) {
            square_collected = am.get("sounds/square_collected.wav", Sound.class);
            circle_collected = am.get("sounds/circle_collected.wav", Sound.class);
        }
    }

    public class AssetMusic {

    }
}
