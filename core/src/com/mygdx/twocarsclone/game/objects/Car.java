package com.mygdx.twocarsclone.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.twocarsclone.game.Assets;
import com.mygdx.twocarsclone.util.Constants;

/**
 * Created by Van on 2016-02-29.
 */
public class Car extends AbstractGameObject {

    public static final String TAG = Car.class.getName();
    private final float MOVING_TIME_MAX = 0.3f;
    private TextureRegion car;
    public boolean hit;
    public POSITION_STATE positionState;
    public Vector2 initialPosition;
    public MOVING_STATE movingState;
    private Vector2 leftLane;
    private Vector2 rightLane;

    public float timeMoving;
    public Car(Color color) {
        init(color);
    }

    private void init(Color color) {
        timeMoving = 0;
        dimension.set(1,2);
        origin.set(dimension.x / 2, dimension.y / 2);
        bounds.set(0, 0, dimension.x, dimension.y);

        terminalVelocity.set((dimension.x/MOVING_TIME_MAX)*2.5f, 0f);

        //states
        movingState = MOVING_STATE.IN_LANE;
        if (Color.BLUE.equals(color)) {
            car = Assets.instance.blueCar.car;
            positionState = POSITION_STATE.LEFT;
            position.set((-Constants.VIEWPORT_WIDTH / 2) + Constants.VIEWPORT_WIDTH / 8 - origin.x,
                    -Constants.VIEWPORT_HEIGHT / 2 + (Constants.VIEWPORT_HEIGHT / 2 * 0.10f));

            rightLane = new Vector2(position);
            rightLane.x += dimension.x*2;
            leftLane = new Vector2(position);
        } else if (Color.RED.equals(color)){
            car = Assets.instance.redCar.car;
            positionState = POSITION_STATE.RIGHT;
            position.set((Constants.VIEWPORT_WIDTH / 2) - Constants.VIEWPORT_WIDTH / 8 - origin.x,
                    -Constants.VIEWPORT_HEIGHT / 2 + (Constants.VIEWPORT_HEIGHT / 2 * 0.10f));

            rightLane = new Vector2(position);
            leftLane = new Vector2(position);
            leftLane.x -= dimension.x*2;
        }
        initialPosition = new Vector2(position);
        hit = false;
    }
    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;

        reg = car;

        batch.draw(reg.getTexture(), position.x, position.y, origin.x,
                origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), false,
                false);
    }

    public void switchLanes(boolean sideTouched) {
        switch(movingState) {
            case LANE_SWITCH_RIGHT:
                if (sideTouched) {
                    switch (positionState) {
                        case LEFT:
                            positionState = POSITION_STATE.RIGHT;
                            break;
                        case RIGHT:
                            positionState = POSITION_STATE.LEFT;
                            break;
                    }
                }
                break;
            case LANE_SWITCH_LEFT:
                if (sideTouched) {
                    switch (positionState) {
                        case LEFT:
                            positionState = POSITION_STATE.RIGHT;
                            break;
                        case RIGHT:
                            positionState = POSITION_STATE.LEFT;
                            break;
                    }
                }
                break;
            case IN_LANE:
                if (sideTouched) {
                    switch (positionState) {
                        case LEFT:
                            movingState = MOVING_STATE.LANE_SWITCH_RIGHT;
                            break;
                        case RIGHT:
                            movingState = MOVING_STATE.LANE_SWITCH_LEFT;
                            break;
                    }
                }
                break;
        }
    }

    @Override
    protected void updateMotionX(float deltaTime) {
        int MAX_ROTATION_DEGREES = 15;
        switch(movingState) {
            case LANE_SWITCH_RIGHT:
                if (position.x <= (rightLane.x)){
                    velocity.x = terminalVelocity.x;
                    if (rotation > -MAX_ROTATION_DEGREES) {
                        rotation = (rightLane.x - position.x) / (rightLane.x - leftLane.x) * -MAX_ROTATION_DEGREES;
                    }
                } else {
                    rotation = 0;
                    velocity.x = 0;
                    movingState = MOVING_STATE.IN_LANE;
                    positionState = POSITION_STATE.RIGHT;
                }
                break;
            case LANE_SWITCH_LEFT:
                if (position.x >= (leftLane.x)){
                    velocity.x = -terminalVelocity.x;
                    if (rotation < MAX_ROTATION_DEGREES) {
                        rotation = (position.x - leftLane.x)  / (rightLane.x - leftLane.x) * MAX_ROTATION_DEGREES;
                    }
                } else {
                    rotation = 0;
                    velocity.x = 0;
                    movingState = MOVING_STATE.IN_LANE;
                    positionState = POSITION_STATE.LEFT;
                }
                break;
            case IN_LANE:
                break;
        }
        if (movingState != MOVING_STATE.IN_LANE)
            super.updateMotionX(deltaTime);
    }

    @Override
    public void update(float deltaTime) {
        updateMotionX(deltaTime);
        super.update(deltaTime);
    }

    public enum POSITION_STATE {
        LEFT,RIGHT
    }

    public enum MOVING_STATE {
        LANE_SWITCH_RIGHT, LANE_SWITCH_LEFT, IN_LANE
    }
}
