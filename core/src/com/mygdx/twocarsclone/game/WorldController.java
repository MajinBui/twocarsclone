package com.mygdx.twocarsclone.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.twocarsclone.game.objects.AbstractToken;
import com.mygdx.twocarsclone.game.objects.CircleToken;
import com.mygdx.twocarsclone.game.objects.SquareToken;
import com.mygdx.twocarsclone.screens.MenuScreen;
import com.mygdx.twocarsclone.util.CameraHelper;
import com.mygdx.twocarsclone.util.Constants;

/**
 * Created by Van on 2016-02-28.
 */
public class WorldController extends InputAdapter {
    private Game game;
    public static final String TAG = WorldController.class.getName();
    // Camera helper, unneeded for this game
    public CameraHelper cameraHelper;
    // World data
    public Level level;
    public int lives;
    public int score;
    public boolean godmode = false;
    public float timeLeftGameOverDelay;
    public boolean gameOver;

    // Hitboxes
    private Polygon polygon1 = new Polygon();
    private Polygon polygon2 = new Polygon();
    private Circle circle1 = new Circle();
    /**
     * Default
     */
    public WorldController(){
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        init();
        //initLevel();
    }

    public WorldController(Game game) {
        this.game = game;
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        init();
    }

    /**
     * Initializes the level data
     */
    private void initLevel() {
        score = 0;
        level = new Level();

    }
    /**
     * Initializes the WorldController.  Useful to reinitialize the object without recreating it.
     */
    private void init(){
        lives = 1;
        timeLeftGameOverDelay = 0;
        gameOver = false;
        initLevel();
    }

    /**
     * Updates the game logic depending on the time that has past by deltaTime
     * @param deltaTime The difference in time past
     */
    public void update(float deltaTime) {
        if (isGameOver()) {
            if (lives > 0 ) {
                timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
                lives--;
            }
            timeLeftGameOverDelay -= deltaTime;
            if (timeLeftGameOverDelay < 0) backToMenu();
        } else {
            handleInputGame(deltaTime);
            level.update(deltaTime);
            testCollisions();
            cameraHelper.update(deltaTime);
        }
    }

    /**
     * Handle game input
     * @param deltaTime the difference in time since last call
     */
    public void handleInputGame(float deltaTime) {
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth()/2) {
                level.blueCar.switchLanes(true);
            } else if (Gdx.input.getX() > Gdx.graphics.getWidth()/2) {
                level.redCar.switchLanes(true);
            }
        }
    }
    @Override
    public boolean keyUp(int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        } // Back to Menu
        else if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
            backToMenu();
        } else if (keycode == Input.Keys.LEFT) {
            level.blueCar.switchLanes(true);
        } else if (keycode == Input.Keys.RIGHT) {
            level.redCar.switchLanes(true);
        } else if (keycode == Input.Keys.G) {
            godmode = !(godmode);
        }
        return false;
    }

    /**
     * Handles car collision with a token
     * @param token the token that has collided with car
     */
    private void onCollisionCarWithToken(AbstractToken token) {
        if (token instanceof CircleToken && !token.collected) {
            score += ((CircleToken)token).getScore(); // Gain point if circle token
            token.collected = true;
        } else if (token instanceof SquareToken && !token.collected) {
            ((SquareToken) token).getScore();
            token.collected = true;
        }
    }

    /**
     * Checks all collisions with current objects
     */
    private void testCollisions() {
        // Check collisions with blue car
        polygon1.setVertices(new float[]{0, 0, level.blueCar.bounds.width, 0, level.blueCar.bounds.width, level.blueCar.bounds.height, 0, level.blueCar.bounds.height});
        polygon1.setPosition(level.blueCar.position.x, level.blueCar.position.y);
        polygon1.setOrigin(level.blueCar.bounds.width / 2, level.blueCar.bounds.height / 2);
        polygon1.setRotation(level.blueCar.rotation);

        for (AbstractToken token : level.blueTokenList){
            if (token instanceof SquareToken) {
                polygon2.setVertices(new float[]{0, 0, token.bounds.width, 0, token.bounds.width, token.bounds.height, 0, token.bounds.height});
                polygon2.setPosition(token.position.x, token.position.y);
                if (!Intersector.overlapConvexPolygons(polygon1, polygon2)) continue;
            } else {
                circle1.set(token.position.x, token.position.y, token.bounds.width/2);
                if (!overlaps(polygon1, circle1)) continue;
            }
            //if (!Intersector.overlapConvexPolygons(polygon1, polygon2)) continue;
            onCollisionCarWithToken(token);
            break;
        }

        // Check collisions with red car
        polygon1.setVertices(new float[]{0, 0, level.redCar.bounds.width, 0, level.redCar.bounds.width, level.redCar.bounds.height, 0, level.redCar.bounds.height});
        polygon1.setPosition(level.redCar.position.x, level.redCar.position.y);
        polygon1.setOrigin(level.blueCar.bounds.width / 2, level.blueCar.bounds.height / 2);
        polygon1.setRotation(level.redCar.rotation);

        for (AbstractToken token : level.redTokenList){
            if (token instanceof SquareToken) {
                polygon2.setVertices(new float[]{0, 0, token.bounds.width, 0, token.bounds.width, token.bounds.height, 0, token.bounds.height});
                polygon2.setPosition(token.position.x, token.position.y);
                if (!Intersector.overlapConvexPolygons(polygon1, polygon2)) continue;
            } else {
                circle1.set(token.position.x, token.position.y, token.bounds.width/2);
                if (!overlaps(polygon1, circle1)) continue;
            }
            //if (!Intersector.overlapConvexPolygons(polygon1, polygon2)) continue;
            onCollisionCarWithToken(token);
            break;
        }
    }

    /**
     * Returns true if game over conditions are met
     * @return true if game over conditions are met
     */
    public boolean isGameOver() {
        if (godmode) return false;
        if (!gameOver && (missedCircleToken() || squareTouched()))
            gameOver = true;
        return gameOver;
    }

    /**
     * Returns true if any CircleTokens in the Level data were missed
     * @return true if any CircleTokens were missed
     */
    public boolean missedCircleToken() {
        for (AbstractToken token : level.redTokenList){
            if (token instanceof CircleToken && !token.collected && token.position.y < -Constants.VIEWPORT_HEIGHT/2) {
                ((CircleToken) token).missed();
                return true;
            }
        }
        for (AbstractToken token : level.blueTokenList){
            if (token instanceof CircleToken && !token.collected && token.position.y < -Constants.VIEWPORT_HEIGHT/2) {
                ((CircleToken) token).missed();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any SquareTokens were collected
     * @return true if any SquareTokens were collected
     */
    public boolean squareTouched() {
        for (AbstractToken token : level.redTokenList){
            if (token instanceof SquareToken && token.collected) {
                return true;
            }
        }
        for (AbstractToken token : level.blueTokenList){
            if (token instanceof SquareToken && token.collected) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the two object are overlapping each other
     * @param polygon the polygon object to test
     * @param circle the circle object to test
     * @return if the two shapes are overlapping
     */
    public static boolean overlaps(Polygon polygon, Circle circle) {
        float []vertices=polygon.getTransformedVertices();
        Vector2 center=new Vector2(circle.x, circle.y);
        float squareRadius=circle.radius*circle.radius;
        for (int i=0;i<vertices.length;i+=2){
            if (i==0){
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length - 2], vertices[vertices.length - 1]), new Vector2(vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                    return true;
            }
        }
        return polygon.contains(circle.x, circle.y);
    }

    private void backToMenu() {
        // switch to menu screen
        game.setScreen(new MenuScreen(game));
    }
}
