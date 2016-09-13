package com.mygdx.twocarsclone.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.twocarsclone.game.Assets;
import com.mygdx.twocarsclone.util.Constants;

/**
 * Created by Van on 2016-02-29.
 */
public class Background extends AbstractGameObject{

    private TextureRegion background;

    public Background() {
        init();
    }
    private void init() {
        dimension.set(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        position.set(-Constants.VIEWPORT_WIDTH / 2, -Constants.VIEWPORT_HEIGHT / 2);
        scale.set(1,1);
        background = Assets.instance.backGround.background;
    }
    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;

        reg = background;
        batch.draw(reg.getTexture(), position.x, position.y, origin.x,
                origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), false,
                false);
    }
}
