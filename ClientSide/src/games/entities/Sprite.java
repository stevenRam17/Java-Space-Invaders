package games.entities;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Sprite {

    private boolean isAlive;
    private double movementSpeed;
    private Image icon;
    private ImageView view;
    private final int width;
    private final int heigth;

    public Sprite(int w, int h) {
        this.isAlive = true;
        this.width = w;
        this.heigth = h;
    }

    public void setIcon(String pathToIcon) {
        icon = new Image("/images/" + pathToIcon);
        view = new ImageView(icon);
        view.setSmooth(true);
        view.setFitWidth(width);
        view.setFitHeight(heigth);
    }

    public ImageView getImage() {
        return view;
    }

    public void kill() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setTranslate(double x, double y) {
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    public double getTranslateX() {
        return view.getTranslateX();
    }

    public double getTranslateY() {
        return view.getTranslateY();
    }

    public void setTranslateX(double x) {
        view.setTranslateX(x);
    }

    public void setTranslateY(double y) {
        view.setTranslateY(y);
    }

    public Bounds getBoundsInParent() {
        return view.getBoundsInParent();
    }

    public void setVisible(boolean b) {
        view.setVisible(b);
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void moveUp() {
        view.setTranslateY(view.getTranslateY() - movementSpeed);
    }

    public void moveDown() {
        view.setTranslateY(view.getTranslateY() + movementSpeed);
    }

    public void moveLeft() {
        view.setTranslateX(view.getTranslateX() - movementSpeed);
    }

    public void moveRight() {
        view.setTranslateX(view.getTranslateX() + movementSpeed);
    }

}
