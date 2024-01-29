/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strategy;

import games.entities.Shoot;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class DownMovement implements BulletMovement {

    @Override
    public void moveBullet(Shoot shoot) {
        shoot.moveDown();
    }
   
    
}
