package games;

import client.Client;
import static games.GameController.ALIEN_BULLET;
import static games.GameController.PLAYER_BULLET;
import games.entities.Alien;
import games.entities.Player;
import games.entities.Shoot;
import games.entities.Sprite;
import games.model.GameStructures;
import java.io.IOException;
import strategy.*;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

/**
 * The class BulletController is responsible for controlling bullets.
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class BulletController implements BulletMover {

    //window attributes 
    private final Game game;
    private final GameStructures model;
    private final AlienController alienController;
    private final GameController gameController;
    private final Player player;
    private final ArrayList<Sprite> sprites;
    private static AnimationTimer updater;
    private int playerCurrentLives;
    private Integer score;
    private final int verticalHboxOffset = 40;
    private BulletMovement bulletMovement;
    //client attributes
    private final Client client;
    private final byte PLAYER_LOST_LIVE = 7;
    private final byte PLAYER_KILL_ALIEN = 8;

    public BulletController(Game game, GameStructures model, Player player,
            AlienController controller, Client client, GameController gameController) throws IOException {
        this.client = client;
        this.game = game;
        this.model = model;
        this.player = player;
        this.alienController = controller;
        this.gameController = gameController;
        score = 0;
        sprites = model.getSprites();
        int lives = client.readInt();
        this.playerCurrentLives = lives;
        updater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
                game.remove();
                model.remove();
            }
        };
        updater.start();
    }
    /**
     * The method stop the animations bullets.
     */
    public void stop() {
        updater.stop();
    }

    /**
     * The method start initialize the animations bullets.
     */
    public void start() {
        updater.start();
    }

    @Override
    public void setBulletMovement(BulletMovement movement) {
        this.bulletMovement = movement;
    }

    @Override
    public void moveBullet(Shoot shoot) {
        bulletMovement.moveBullet(shoot);
    }
    
    /**
     * The method updateGame is responsible for updating the game.
     */
    public void updateGame() {
        if (model.allAliensDeath() && (player.isAlive()
                || playerCurrentLives > 0)) {
            alienController.addAliens();
        }
        if (playerCurrentLives == 0 || !player.isAlive()) {
            game.setGameStatusLabelText("HA PERDIDO!");
            game.enableStatusLabel(true);
            GameController.GAME_RUNNING = false;
            player.kill();
            game.hideSprites();
        }
        if (GameController.GAME_RUNNING) {
            for (Sprite sprite : sprites) {
                if (sprite instanceof Shoot) {
                    Shoot shoot = (Shoot) sprite;//To access the specific methods of Shoot child
                    if (shoot.getShootDeliver().equals(ALIEN_BULLET)) {
                        setBulletMovement(new DownMovement());
                        bulletMovement.moveBullet(shoot);
                        if (sprite.getTranslateY() < game.getPanePrefHeigth()) {
                            if (model.checkPlayerHit(sprite.getBoundsInParent())) {
                                try {
                                    //indicate to the server that he lost a life
                                    client.writeInt(PLAYER_LOST_LIVE);
                                    playerCurrentLives = client.readInt();
                                    sprite.kill();
                                    game.decreaseLives();
                                    game.addToRemovalList(sprite);
                                    model.addToRemovalList(sprite);
                                } catch (IOException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            }
                        } else {
                            sprite.kill();
                            game.addToRemovalList(sprite);
                            model.addToRemovalList(sprite);
                        }
                    }
                    if (shoot.getShootDeliver().equals(PLAYER_BULLET)) {
                        if ((sprite.getTranslateY() > verticalHboxOffset)) {
                            setBulletMovement(new UpMovement());
                            bulletMovement.moveBullet(shoot);
                            Alien alien = model.checkAlienHit(sprite);
                            if (alien != null) {
                                try {
                                    client.writeInt(PLAYER_KILL_ALIEN);
                                    //1 low alien, 2 middle alien, 3 top alien, 4 super alien
                                    client.writeInt(alien.getType());
                                    score = client.readInt();//Updated score
                                    sprite.kill();
                                    game.addToRemovalList(sprite);
                                    model.addToRemovalList(sprite);
                                    alien.kill();
                                    game.addToRemovalList(alien);
                                    model.addToRemovalList(alien);
                                    game.setPoints(score.toString());
                                } catch (IOException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            }
                        } else {
                            sprite.kill();
                            game.addToRemovalList(sprite);
                            model.addToRemovalList(sprite);
                        }
                    }
                }
            }
        }
    }

}
