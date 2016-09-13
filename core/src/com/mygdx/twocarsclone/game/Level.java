package com.mygdx.twocarsclone.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.twocarsclone.game.objects.AbstractToken;
import com.mygdx.twocarsclone.game.objects.Background;
import com.mygdx.twocarsclone.game.objects.Car;
import com.mygdx.twocarsclone.game.objects.CircleToken;
import com.mygdx.twocarsclone.game.objects.SquareToken;
import com.mygdx.twocarsclone.util.Constants;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Van on 2016-03-05.
 */
public class Level {
    public static final String TAG = Level.class.getName();
    public final float TOKEN_SPACE = 2.25f;

    Random random;
    public float total_time;

    // Decoration
    public Background background;

    // Objects
    public Car blueCar;
    public Car redCar;

    // Tokens
    public ArrayList<AbstractToken> redTokenList;
    public ArrayList<AbstractToken> blueTokenList;

    /**
     * Default
     */
    public Level() {
        init();
    }

    /**
     * Initializes the level data
     */
    private void init() {
        background = new Background();
        blueCar = new Car(Color.BLUE);
        redCar = new Car(Color.RED);
        redTokenList = new ArrayList<AbstractToken>();
        blueTokenList = new ArrayList<AbstractToken>();

        random = new Random();
        total_time = 0;
    }

    /**
     * Renders all level data to the batch given
     * @param batch the SpriteBatch to render all level data to
     */
    public void render(SpriteBatch batch) {
        background.render(batch);
        blueCar.render(batch);
        redCar.render(batch);
        renderTokens(batch);
    }

    /**
     * Updates all level data according to the time passed
     * @param deltaTime the time passed since last call
     */
    public void update(float deltaTime) {
        total_time += deltaTime;
        blueCar.update(deltaTime);
        redCar.update(deltaTime);
        generateNextToken();
        for (AbstractToken token : redTokenList) {
            token.update(deltaTime);
        }
        for (AbstractToken token : blueTokenList) {
            token.update(deltaTime);
        }
        destroyObsoleteTokens();
    }

    /**
     * Renders all tokens to the screen
     * @param batch the SpriteBatch to render the tokens to
     */
    public void renderTokens(SpriteBatch batch) {
        for (AbstractToken token : redTokenList) {
            token.render(batch);
        }
        for (AbstractToken token : blueTokenList) {
            token.render(batch);
        }
    }

    /**
     * Generates the next token for the TokenLists
     */
    public void generateNextToken() {
        // Spawn token every time a car can safely pass through
        if (redTokenList.size() > 0){
            if (Constants.VIEWPORT_HEIGHT/2 - (redTokenList.get(redTokenList.size() - 1).position.y) > redCar.dimension.y * TOKEN_SPACE) {
                generateToken(Color.RED, redTokenList);
                generateToken(Color.BLUE, blueTokenList);
            }
        } else if (redTokenList.size() == 0){
            generateToken(Color.RED, redTokenList);
            generateToken(Color.BLUE, blueTokenList);
        }
    }

    /**
     * Search through each token created and destroy them if they are no longer needed
     */
    public void destroyObsoleteTokens() {
        // Destroy obsolete tokens
        ArrayList<AbstractToken> temp = new ArrayList<AbstractToken>();
        for (AbstractToken token : redTokenList) {
            if (!(token.position.y < -Constants.VIEWPORT_HEIGHT/2 - Constants.VIEWPORT_HEIGHT/2*.50f)) {
                temp.add(token);
            }
        }
        redTokenList = temp;
        temp = new ArrayList<AbstractToken>();
        for (AbstractToken token : blueTokenList) {
            if (!(token.position.y < -Constants.VIEWPORT_HEIGHT/2 - Constants.VIEWPORT_HEIGHT/2*.50f)) {
                temp.add(token);
            }
        }
        blueTokenList = temp;
    }

    /**
     * Creates a token and adds it to the given list
     * @param color the color of the token
     * @param list the list to add the token to
     */
    public void generateToken(Color color, ArrayList<AbstractToken> list) {
        int rand_int = random.nextInt(2);
        AbstractToken new_token = rand_int == 0 ? (AbstractToken) new CircleToken(color) : (AbstractToken) new SquareToken(color);
        rand_int = random.nextInt(2);
        new_token.position.x = (Constants.VIEWPORT_WIDTH / 4) * (rand_int) + (Constants.VIEWPORT_WIDTH / 4) / 2 - new_token.dimension.x / 2;
        new_token.position.y = Constants.VIEWPORT_HEIGHT/2;
        // Blue color offsets
        if (color.equals(Color.BLUE)) {
            new_token.position.x -= (Constants.VIEWPORT_WIDTH / 2);
            new_token.position.y += Constants.VIEWPORT_HEIGHT/2*.15f;
        }
        new_token.velocity.y = new_token.velocity.y - total_time/15;
        list.add(new_token);
    }
}
