/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import games.entities.SuperAlien;

/**
 * The class follows the Factory method design pattern
 * The purpose of this class is to create instance of super aliens
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class SuperAlienFactory implements AlienFactory {

    private final int SUPER_ALIEN_SIZE = 80;
    private final int TYPE = 4;

    /**
     * This method is responsible for creating disc alien
     * @param x the X axis the alien going to be positionated
     * @param y the Y axis the alien going to be positionated
     * @return a instance of a new super alien with the attributes specified in this 
     * class
     */
    @Override
    public SuperAlien createAlien(int x, int y) {
        SuperAlien superAlien = new SuperAlien(SUPER_ALIEN_SIZE, SUPER_ALIEN_SIZE,
                x, y, TYPE);
        return superAlien;
    }

}
