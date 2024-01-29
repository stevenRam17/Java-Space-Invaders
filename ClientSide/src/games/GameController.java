package games;

import client.Client;
import games.entities.Player;
import games.model.GameStructures;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class GameController implements EventHandler<KeyEvent> {

    //Threads of the animations and movement of the user
    //VEASE LOS CONTROLLER COMO HILOS CON DIVERSAS LOGICAS QUE SE COMUNICAN 
    //DIRECTAMENTE CON EL GAME VIEW (VISTA)
    private final UserMoveController userController;
    private final AlienController alienController;
    private final SpecialAlienController specialAlienController;
    private final BulletController bulletController;
    //Game variable(view) and model
    private final Game game;
    private final Player player;
    private final GameStructures model;
    //Contants and variable
    public static final String PLAYER_BULLET = "player";
    public static final String ALIEN_BULLET = "alien";
    public static boolean GAME_RUNNING = true;
    //Client
    private final Client client;
    private final int userRecord;

    public GameController(Game game, Client client, GameStructures model) throws FileNotFoundException, IOException {
        this.game = game;
        this.client = client;
        this.player = new Player((game.getPanePrefHeigth() / 2),
                (game.getPanePrefHeigth() - 50), 80, 80);
        this.model = model;
        game.setPlayer(player);
        userRecord = client.readInt();
        game.setScoreRecord(userRecord);
        userController = new UserMoveController(model, player, this.game);
        alienController = new AlienController(model, this.game);
        specialAlienController = new SpecialAlienController(this.game, this.model);
        bulletController = new BulletController(this.game, model,
                player, alienController, this.client, this);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * The method stop stops all animations.
     */
    public void stop() {
        specialAlienController.stop();
        alienController.stop();
        bulletController.stop();
    }

    /**
     * The method start initialize all animations.
     */
    public void start() {
        specialAlienController.start();
        alienController.start();
        bulletController.start();
    }
    
    /**
     * The method remove delete all animations.
     */
    public void remove() {
        game.addAllToRemovalList();
        model.addAllToRemovalList();
    }

    @Override
    public void handle(KeyEvent event) {
        userController.handle(event);
    }

}
