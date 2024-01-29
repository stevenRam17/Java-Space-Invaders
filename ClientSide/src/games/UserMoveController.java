package games;

import games.entities.Player;
import games.entities.Shoot;
import games.model.GameStructures;
import javafx.scene.input.KeyEvent;

/**
 * The class UserMoveController is responsible for controlling user.
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class UserMoveController {

    private final Player player;
    private final GameStructures model;
    private final Game game;

    public UserMoveController(GameStructures model, Player player, Game game) {
        this.player = player;
        this.model = model;
        this.game = game;
    }
    
    /**
     * The methos handle is responsible for moving the user to the sides. 
     * @param e side to move user.
     */
    public void handle(KeyEvent e) {
        if (player.isAlive()) {
            switch (e.getCode()) {
                case LEFT:
                    if (player.getTranslateX() > 0) {
                        player.moveLeft();
                    }
                    break;
                case RIGHT:
                    if (player.getTranslateX() < (game.getPanePrefWidth()
                            - player.getWidth())) {
                        player.moveRight();
                    }
                    break;
                case SPACE:
                    if (model.getUserShoots() == 0) {
                        Shoot shoot = player.shoot();
                        model.addSprite(shoot);
                        game.addSprite(shoot);
                    }
                    break;
            }
        }
    }

}
