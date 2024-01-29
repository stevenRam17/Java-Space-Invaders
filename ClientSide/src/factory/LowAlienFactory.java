package factory;

import games.entities.Alien;

/**
 * The class follows the Factory method design pattern
 * The purpose of this class is to create instance of bottom row alien matrix
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class LowAlienFactory implements AlienFactory {

    private final String pathToAlienIcon = "ufo1.png";
    private final int alienBulletSpeed = 1;
    private final int ALIEN_SIZE = 50;
    private final int TYPE = 1;

    /**
     * This method is responsible for creating alien instances, 
     * for the bottom row of the alien matrix
     * @param x the X axis the alien going to be positionated
     * @param y the Y axis the alien going to be positionated
     * @return a instance of a new alien with the attributes specified in this 
     * class
     */
    @Override
    public Alien createAlien(int x, int y) {
        Alien alien = new Alien(ALIEN_SIZE, ALIEN_SIZE, x, y, TYPE); 
        alien.setIcon(pathToAlienIcon);
        alien.setAlienBulletSpeed(alienBulletSpeed);
        return alien;
    }

}
