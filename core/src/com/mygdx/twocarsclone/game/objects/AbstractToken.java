package com.mygdx.twocarsclone.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;


/**
 * Created by Van on 2016-03-05.
 */
public abstract class AbstractToken extends AbstractGameObject {
    public static final String TAG = AbstractToken.class.getName();

    private float BLINK_INTERVAL = 0.5f;

    protected boolean isBlinking;

    protected TextureRegion token;
    public boolean collected;
    Color color;

    public AbstractToken() {
        this(Color.BLUE);
    }
    public AbstractToken(Color color) {
        this.isBlinking = false;

        collected = false;
        this.color = color;
        acceleration.set(0, 0f);
        position.set(0,0);
        velocity.set(0, -5f);
        terminalVelocity.set(0, 10f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        reg = token;
        if (!isBlinking()) {
            batch.draw(reg.getTexture(), position.x, position.y,
                    origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                    rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
        }
    }

    protected void setBlinkSchedule() {

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                isBlinking = (isBlinking ? false : true);
            }
        }, 0 ,BLINK_INTERVAL);
        Gdx.app.debug(TAG, "setBlinkSchedule");
    }

    public boolean isBlinking() {
        return isBlinking;
    }
}
