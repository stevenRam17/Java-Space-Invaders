package games.model;

import games.GameController;
import games.entities.Alien;
import games.entities.Player;
import games.entities.Shoot;
import games.entities.Sprite;
import games.entities.SuperAlien;
import java.util.ArrayList;
import javafx.geometry.Bounds;

/**
 * This class purpose has is the differents data structures 
 * that the logic interacts with
 * Also has the different method to add, remove, return instances
 * It has too diffrent validations methods like check if a bullet crash with
 * an alien or the player
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class GameStructures {

    private final ArrayList<Sprite> sprites;
    private final ArrayList<Alien> aliens;
    private final ArrayList<Shoot> shoots;
    private final ArrayList<Sprite> removeList;
    private Player player;

    public GameStructures() {
        sprites = new ArrayList<>();
        aliens = new ArrayList<>();
        shoots = new ArrayList<>();
        removeList = new ArrayList<>();
    }

    public void setPlayer(Player player) {
        this.player = player;
        addSprite(player);
    }

    public Player getPlayer() {
        return player;
    }
    
    /**
     * Calls the kill method which is inherited from the parent which is Sprite
     */
    public void killPlayer() {
        player.kill();
    }
    
    /**
     * add a sprite to the different data structures
     * evaluate what instance is
     * @param sprite 
     */
    public void addSprite(Sprite sprite) {
        if (sprite instanceof Alien) {
            aliens.add((Alien) sprite);
        }
        if (sprite instanceof Shoot) {
            shoots.add((Shoot) sprite);
        }
        sprites.add(sprite);
    }

    private void removeSprite(Sprite sprite) {
        if (sprite instanceof Alien) {
            aliens.remove((Alien) sprite);
        }
        if (sprite instanceof Shoot) {
            shoots.remove((Shoot) sprite);
        }
        sprites.remove(sprite);
    }
    
    /**
     * remove all the object that are in the removal list
     * Also works like a garbage collector
     */
    public void remove() {
        if (!removeList.isEmpty()) {
            removeList.forEach(sprite -> {
                removeSprite(sprite);
            });
        }
    }
    

    /**
     * Add all the Objects that are currently in the sprites 
     * list to the removal list
     */
    public void addAllToRemovalList() {
        sprites.forEach(sprite -> {
            addToRemovalList(sprite);
        });
    }
    
    /**
     * Add an specific sprite object to the removal list
     * @param sprite 
     */
    public void addToRemovalList(Sprite sprite) {
        removeList.add(sprite);
    }
    
    /**
     * 
     * @return the data structure of all the sprites
     */
    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    /**
     * This mehtod is here because the object Shoot is a child of a sprite too
     * and also work like a one too, so there is list of shoots too
     * This method is used to evaluate how many shoots the user hit
     * @return 
     */
    public int getUserShoots() {
        int counter = 0;
        for (Shoot shoot : shoots) {
            if (shoot.getShootDeliver().equals(GameController.PLAYER_BULLET)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * 
     * @return true if all the aliens are death, false if one alien is alive
     */
    public boolean allAliensDeath() {
        for (Alien alien : aliens) {
            if (alien.isAlive() && !(alien instanceof SuperAlien)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * check if the given bounds that correponds to a enemy bullet 
     * intersects with the player
     * @param enemyBulletBounds the given bounds that are going to be evaluate
     * @return true if it hits
     */
    public boolean checkPlayerHit(Bounds enemyBulletBounds) {
        return enemyBulletBounds.intersects(player.getBoundsInParent());
    }
    
    /**
     * check if the given bounds that correspond to a bullet
     * intersects with an enemy
     * @param playerBulletBounds
     * @param enemyBounds
     * @return 
     */
    public boolean checkAlienHit(Bounds playerBulletBounds, Bounds enemyBounds) {
        return playerBulletBounds.intersects(enemyBounds);
    }
    
    /**
     * if the actual row of alien are also in the Y position of the player
     * This means the aliens reached the player
     * @param alienBounds
     * @return 
     */
    public boolean checkPlayerHitWithAlien(Bounds alienBounds) {
        return alienBounds.intersects(player.getBoundsInParent());
    }
    
    /**
     * check if the player bullet hit an alien 
     * @param playerBullet
     * @return 
     */
    public Alien checkAlienHit(Sprite playerBullet) {
        for (Alien alien : aliens) {
            if (checkAlienHit(playerBullet.getBoundsInParent(),
                    alien.getBoundsInParent())) {
                return alien;
            }
        }
        return null;
    }

}
