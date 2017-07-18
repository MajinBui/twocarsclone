package com.mygdx.twocarsclone.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.twocarsclone.game.Assets;

/**
 * Created by Van on 2016-02-29.
 */
public class SquareToken extends AbstractToken {

    public SquareToken(){
        this(Color.BLUE);
    }

    public SquareToken(Color color) {
        super(color);
        init();
    }

    private void init() {
        collected = false;
        if (color.equals(Color.RED)) {
            token = Assets.instance.redSq.sq;
        } else if (color.equals(Color.BLUE)) {
            token = Assets.instance.blueSq.sq;
        }
        dimension.set(0.5f, 0.5f);
        bounds.set(0, 0, dimension.x, dimension.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    public int getScore() {
        Assets.instance.sounds.square_collected.play();
        setBlinkSchedule();
        return 0;
    }
}
