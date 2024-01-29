/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.entities;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Shoot extends Sprite {

    private final String shootDeliver; 

    public Shoot(double x, double y, int w, int h, String pathToFile
            , String shootDeliver, double bulletSpeed) {
        super(w, h);
        super.setIcon(pathToFile);
        this.shootDeliver = shootDeliver; 
        super.setTranslate(x, y);
        super.setMovementSpeed(bulletSpeed);
    }
    
    public String getShootDeliver() {
        return shootDeliver;
    }

}
