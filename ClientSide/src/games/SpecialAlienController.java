package games;

import factory.SuperAlienFactory;
import games.entities.SuperAlien;
import games.model.GameStructures;
import java.util.Random;
import javafx.animation.AnimationTimer;

/**
 * The class SpecialAlienController is responsible for controlling super alien.
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class SpecialAlienController {

    private final Game game;
    private final GameStructures model;
    private SuperAlien specialAlien;
    private final SuperAlienFactory factory;
    private int counter;
    private static AnimationTimer alienAnimation;
    private final int MIN_TIME = 100;
    private final int MAX_TIME = 500;
    private final int PANE_LOCATION = 70;
    private final Random rand = new Random();
    private int randTime;

    public SpecialAlienController(Game game, GameStructures model) {
        this.game = game;
        this.model = model;
        factory = new SuperAlienFactory();
        createAlien();
        alienAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveAlien();
            }
        };
        alienAnimation.start();
    }

    /**
     * The method stop stops the animation of the super alien.
     */
    public void stop() {
        alienAnimation.stop();
    }
    /**
     * The method start initialize the animation of the super alien.
     */
    public void start() {
        alienAnimation.start();
    }
    
    /**
     * The method createAlien is responsible to create super aliens.
     */
    private void createAlien() {
        if (GameController.GAME_RUNNING) {
            specialAlien = factory.createAlien(0, PANE_LOCATION);
            model.addSprite(specialAlien);
            game.addSprite(specialAlien);
            specialAlien.setVisible(false);
            randTime = rand.nextInt(((MAX_TIME - MIN_TIME) + 1) + MIN_TIME);
        }
    }
    
    /**
     * The method moveAlien is responsible for moving the super alien to the sides.
     */
    private void moveAlien() {
        counter++;
        boolean hasPassed = false;
        if (GameController.GAME_RUNNING) {
            if (counter > randTime) {
                specialAlien.setVisible(true);
                if (specialAlien.getTranslateX() < game.getPanePrefWidth()) {
                    if (specialAlien.isAlive()) {
                        specialAlien.moveRight();
                    } else {
                        hasPassed = true;
                        specialAlien.kill();
                        model.addToRemovalList(specialAlien);
                        game.addToRemovalList(specialAlien);
                        createAlien();
                    }
                } else {
                    hasPassed = true;
                    specialAlien.kill();
                    model.addToRemovalList(specialAlien);
                    game.addToRemovalList(specialAlien);
                    createAlien();
                }
                if (hasPassed) {
                    counter = 0;
                }
            }
        }
    }

}
