package strategy;

import games.entities.Shoot;

/**
 * The interface
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public interface BulletMover {
    
    public void setBulletMovement(BulletMovement movement); 
    
    public void moveBullet(Shoot shoot); 
    
}
