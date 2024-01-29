/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import games.entities.Alien;

/**
 * The class follows the Factory method design pattern
 * The purpose of this class is to create instance of top row alien matrix
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class TopAlienFactory implements AlienFactory {

    private final String pathToAlienIcon = "ufo3.png";
    private final int alienBulletSpeed = 3;
    private final String pathToBulletIcon = "bomb3.png"; 
    private final int ALIEN_SIZE = 50;
    private final int TYPE = 3;

    /**
     * This method is responsible for creating alien instances, 
     * for the top row of the alien matrix
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
        alien.setBulletIcon(pathToBulletIcon);
        return alien;
    }
}
