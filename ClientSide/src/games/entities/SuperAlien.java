package games.entities;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class SuperAlien extends Alien {

    private final double rightSpeed = 3;
    private final String pathToIcon = "ufoBoss.png";

    public SuperAlien(int w, int h, int x, int y, int type) {
        super(w, h, x, y, type);
        super.setAlienBulletSpeed(0);
        super.setIcon(pathToIcon);
        super.setMovementSpeed(rightSpeed);
    }

}
