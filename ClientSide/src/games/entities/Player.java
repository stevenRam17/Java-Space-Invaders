package games.entities;

import java.io.FileNotFoundException;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Player extends Sprite {

    private final int playerWidth;
    private int currentLives;
    private final int playerBulletSpeed = 10;
    private final int playerSpeed = 15;
    private final int BULLET_OFFSET = 40;
    private final int BULLET_HEIGTH = 30;
    private final int BULLET_WIDTH = 30;
    private final String PLAYER_BULLET = "player";
    private final String pathToBulletIcon = "bullet.png";
    private final String pathToPlayerIcon = "ship.png";

    public Player(double x, double y, int w, int h) throws FileNotFoundException {
        super(w, h);
        super.setIcon(pathToPlayerIcon);
        super.setTranslate(x, y);
        this.playerWidth = w;
    }

    public Shoot shoot() {
        return new Shoot(getTranslateX() + BULLET_OFFSET, getTranslateY(), BULLET_WIDTH,
                BULLET_HEIGTH, pathToBulletIcon, PLAYER_BULLET, playerBulletSpeed);
    }

    @Override
    public void moveLeft() {
        setTranslateX(getTranslateX() - playerSpeed);
    }

    @Override
    public void moveRight() {
        setTranslateX(getTranslateX() + playerSpeed);
    }

    public int getPlayerBulletSpeed() {
        return playerBulletSpeed;
    }

    public int getWidth() {
        return playerWidth;
    }

    public void decreaseLives() {
        currentLives--;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }
    


}
