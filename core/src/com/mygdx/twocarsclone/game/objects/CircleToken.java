package com.mygdx.twocarsclone.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.twocarsclone.game.Assets;

/**
 * Created by Van on 2016-02-29.
 */
public class CircleToken extends AbstractToken {
    public CircleToken(Color color) {
        super(color);
        init();
    }

    private void init() {
        collected = false;
        if (color.equals(Color.RED)) {
            token = Assets.instance.redToken.token;
        } else if (color.equals(Color.BLUE)) {
            token = Assets.instance.blueToken.token;
        }
        dimension.set(0.5f, 0.5f);
        bounds.set(0, 0, dimension.x, dimension.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (collected) return;
        super.render(batch);
    }
    public void missed() {
        setBlinkSchedule();
    }
    public int getScore() {
        return 1;
    }
}
