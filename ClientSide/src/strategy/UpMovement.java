package strategy;

import games.entities.Shoot;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class UpMovement implements BulletMovement {

    @Override
    public void moveBullet(Shoot shoot) {
        shoot.moveUp();
    }

}
