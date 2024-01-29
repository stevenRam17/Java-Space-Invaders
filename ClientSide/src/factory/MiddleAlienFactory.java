/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import games.entities.Alien;

/**
 * The class follows the Factory method design pattern
 * The purpose of this class is to create instance of middle row aliens
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class MiddleAlienFactory implements AlienFactory {

    private final String pathToAlienIcon = "ufo.png";
    private final int alienBulletSpeed = 2;
    private final int ALIEN_SIZE = 50;
    private final int TYPE = 2;

    /**
     * This method is responsible for creating alien instances, 
     * for the middle row of the alien matrix
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
