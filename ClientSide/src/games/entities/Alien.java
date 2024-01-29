package games.entities;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Alien extends Sprite {

    private double alienBulletSpeed;
    private final int type;
    private final double alienSpeed = 0.5;
    private final double speedIncrement = 0.1;
    private final double x;
    private final double y;
    private double verticalSpeed = 20;
    private final String ALIEN_BULLET = "alien";
    private String pathToBulletIcon = "bomb.png";

    public Alien(int w, int h, double x, double y, int type) {
        super(w, h);
        this.x = x;
        this.y = y;
        this.type = type;
        super.setMovementSpeed(alienSpeed);
    }

    @Override
    public void setIcon(String pathToFile) {
        super.setIcon(pathToFile);
        super.setTranslate(x, y);
    }

    public void setBulletIcon(String pathToFile) {
        this.pathToBulletIcon = pathToFile;
    }

    public void incrementSpeedBy(double increment) {
        super.setMovementSpeed(alienSpeed + speedIncrement);
        this.verticalSpeed += increment;
    }

    public int getType() {
        return type;
    }

    public double getAlienBulletSpeed() {
        return alienBulletSpeed;
    }

    public void setAlienBulletSpeed(double bulletSpeed) {
        this.alienBulletSpeed = bulletSpeed;
    }

    @Override
    public void moveDown() {
        setTranslateY(getTranslateY() + verticalSpeed);
    }

    public Shoot shoot() {
        return new Shoot(getTranslateX() + 10, getTranslateY(), 30, 30,
                pathToBulletIcon, ALIEN_BULLET, alienBulletSpeed);
    }

}
