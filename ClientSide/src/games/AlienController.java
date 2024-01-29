package games;

import factory.MiddleAlienFactory;
import factory.LowAlienFactory;
import factory.TopAlienFactory;
import games.entities.Alien;
import games.entities.Shoot;
import games.model.GameStructures;
import java.util.Random;
import javafx.animation.AnimationTimer;
import factory.AlienFactory;

/**
 * The class AlienController is responsible for controlling aliens.
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class AlienController {

    //strategy 
    private AlienFactory factory;
    //window attributes
    private final Game game;
    private final GameStructures model;
    //matrix constants
    private final int COLUMNS = 10;
    private final int ROWS = 3;
    //constants and variables
    private final int SPEED_INCREMENT = 2;
    private final int MIN_TIME = 60;
    private final int MAX_TIME = 120;
    boolean movingLeft = false;
    boolean movingRight = false;
    private final Random rand = new Random();
    private int counter = 0;
    private int randTime = 0;
    //data structure
    private final Alien[][] alienMatrix;
    //animation
    private final AnimationTimer alienAnimation;

    public AlienController(GameStructures model, Game game) {
        alienMatrix = new Alien[ROWS][COLUMNS];
        this.model = model;
        this.game = game;
        addAliens();
        getTime();
        alienAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) { //Execute every moment
                moveAliens();
                makeAliensFire();
            }
        };
        alienAnimation.start();
    }

    /**
     * The method stop stops the animation of the aliens.
     */
    public void stop() {
        alienAnimation.stop();
    }

    /**
     * The method start initialize the animation of the aliens.
     */
    public void start() {
        alienAnimation.start();
    }

    /**
     * The method addAliens is responsible of adding the aliens to the matrix.
     */
    public void addAliens() {
        int xPos = 100;
        int yPos = 150;
        Alien alien;
        for (int i = 0; i < alienMatrix.length; i++, yPos += 50) {
            switch (i) {
                case 0:
                    factory = new TopAlienFactory();
                    break;
                case 1:
                    factory = new MiddleAlienFactory();
                    break;
                case 2:
                    factory = new LowAlienFactory();
                    break;
            }
            for (int j = 0; j < alienMatrix[0].length; j++, xPos += 50) {
                alien = factory.createAlien(xPos, yPos);
                model.addSprite(alien);
                game.addSprite(alien);
                alienMatrix[i][j] = alien;
            }
            xPos = 100;
        }
    }
    /**
     * The method shutDownAliens is responsible to remove the aliens from the matrix.
     */
    private void shutDownAliens() {
        for (int i = 0; i < alienMatrix.length; i++) {
            for (int j = 0; j < alienMatrix[0].length; j++) {
                Alien alien = alienMatrix[i][j];
                model.addToRemovalList(alien);
                game.addToRemovalList(alien);
            }
        }
    }
    
    /**
     * The method moveAliensDown is responsible for moving the aliens down.
     */
    private void moveAliensDown() {
        for (int i = 0; i < alienMatrix.length; i++) {
            for (int j = 0; j < alienMatrix[0].length; j++) {
                Alien sprite = alienMatrix[i][j];
                sprite.moveDown();
                sprite.incrementSpeedBy(SPEED_INCREMENT);
            }
        }
    }
    /**
     * The method moveAliens is responsible for moving the aliens to the sides.
     */
    private void moveAliens() {
        if (GameController.GAME_RUNNING) { //See class GameUpdater, method updateGame
            for (int i = 0; i < alienMatrix.length; i++) {
                for (int j = 0; j < alienMatrix[0].length; j++) {
                    Alien sprite = alienMatrix[i][j];
                    if (sprite.isAlive()) {
                        if (!model.checkPlayerHitWithAlien(sprite.getBoundsInParent())) {
                            if (!movingRight) {
                                if (sprite.getTranslateX() >= 0) {
                                    movingLeft = true;
                                    sprite.moveLeft();
                                } else {
                                    movingLeft = false;
                                    moveAliensDown();
                                }
                            }
                            if (!movingLeft) { //Cuando choca con el limite izquierdo del contenedor
                                if (sprite.getTranslateX() <= (game.getPanePrefWidth() - 50)) {
                                    movingRight = true;
                                    sprite.moveRight();
                                } else {
                                    movingRight = false;
                                    moveAliensDown();
                                }
                            }
                        } else {
                            model.killPlayer();
                        }
                    }
                }
            }
        } else {
            shutDownAliens();
        }
    }
    
    /**
     * The method getTime is responsible for assigning a ramdom time.
     */
    private void getTime() {
        randTime = rand.nextInt(((MAX_TIME - MIN_TIME) + 1) + MIN_TIME);
    }
    
    /**
     * The method makeAliensFire is responsible for making aliens shoot.
     */
    private void makeAliensFire() {
        counter++;//Allow take control of the random time that the bullets gonna be fired
        boolean fired = false;
        if (counter > randTime && !model.allAliensDeath() && GameController.GAME_RUNNING) {
            Alien randomAlien = alienMatrix[rand.nextInt(ROWS)][rand.nextInt(COLUMNS)];
            if (randomAlien.isAlive()) {
                Shoot alienShoot = randomAlien.shoot();
                model.addSprite(alienShoot);
                game.addSprite(alienShoot);
                fired = true;
            }
        }
        if (fired) {
            counter = 0;
            getTime();//New random time that the bullet gonna be fired
        }
    }

}
