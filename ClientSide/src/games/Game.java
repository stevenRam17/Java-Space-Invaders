package games;

import games.entities.Player;
import games.entities.Sprite;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The class
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Game extends Application {

    //Graphic containers
    private static Pane pane;//root
    private static HBox box;
    //Variable and constans
    private final String INIT_LIVES = "3";
    private final int PANE_WIDTH = 600;
    private final int PANE_HEIGTH = 800;
    //Data structure
    private ArrayList<Sprite> spriteListInView;
    private ArrayList<Sprite> removeList;
    //Graphic elements 
    private Label lbPoints;
    private Label lbLives;
    private Label tfPoints;
    private Label tfLives;
    private Label lbRecord;
    private Label tfRecord;
    private Label gameStatusLabel;
    private Button exitButton;
    private Button confirmButton;
    private Button backButton;
    //sound 
    private MediaPlayer mediaPlayer;
    //Control
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Java Space Invaders");
        playSong();
        stage.show();
        this.stage = stage;
    }

    public void setController(GameController controller) {
        this.stage.addEventFilter(KeyEvent.KEY_PRESSED, controller);
    }

    /**
     * The method playSong is responsible to the start music in game view.
     */
    public void playSong() {
        String s = "/sounds/song.mp3";
        Media media = new Media(getClass().getResource(s).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public void setPlayer(Player player) {
        pane.getChildren().add(player.getImage());
        spriteListInView.add(player);
    }

    public void dismiss() {
        resumeMediaPlayer();
        pane.getChildren().remove(confirmButton);
        pane.getChildren().remove(backButton);
    }

    public void close() {
        try {
            stopMediaPlayer();
            stage.close();
        } catch (Exception ex) {
            System.err.println("Stopping error");
        }
    }

    public void createGameStatusLabel() {
        gameStatusLabel = new Label("");
        gameStatusLabel.setVisible(false);
        gameStatusLabel.setTextAlignment(TextAlignment.CENTER);
        gameStatusLabel.setAlignment(Pos.BASELINE_CENTER);
        gameStatusLabel.setFont(Font.font("JetBrains Mono",
                FontWeight.BOLD, FontPosture.REGULAR, 25));
        gameStatusLabel.setStyle("-fx-background-color: black; -fx-text-fill: blueviolet;");
        gameStatusLabel.setPrefSize(200, 100);
        gameStatusLabel.setTranslateX((pane.getPrefWidth() / 2) - 100);
        gameStatusLabel.setTranslateY(pane.getPrefHeight() / 2);
        pane.getChildren().add(gameStatusLabel);
    }

    public void pause() {
        confirmButton = new Button("Confirmar");
        confirmButton.setId("confirmButton");
        confirmButton.setTextAlignment(TextAlignment.CENTER);
        confirmButton.setAlignment(Pos.BASELINE_CENTER);
        confirmButton.setFont(Font.font("Consolas",
                FontWeight.BOLD, FontPosture.REGULAR, 25));
        confirmButton.setStyle("-fx-background-color: black; -fx-text-fill: yellow;");
        confirmButton.setPrefSize(200, 100);
        confirmButton.setTranslateX((pane.getPrefWidth() / 2) - 100);
        confirmButton.setTranslateY(pane.getPrefHeight() / 2);
        pane.getChildren().add(confirmButton);

        backButton = new Button("Volver");
        backButton.setTextAlignment(TextAlignment.CENTER);
        backButton.setAlignment(Pos.BASELINE_CENTER);
        backButton.setFont(Font.font("Consolas",
                FontWeight.BOLD, FontPosture.REGULAR, 25));
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: orangered;");
        backButton.setPrefSize(200, 100);
        backButton.setTranslateX((pane.getPrefWidth() / 2) - 100);
        backButton.setTranslateY(confirmButton.getTranslateY() + 155);
        pane.getChildren().add(backButton);
        pauseMediaPlayer();
    }

    private Parent createContent() throws FileNotFoundException {
        pane = new Pane();
        box = new HBox();
        spriteListInView = new ArrayList<>();
        removeList = new ArrayList<>();
        pane.setPrefSize(PANE_WIDTH, PANE_HEIGTH);
        pane.setStyle("-fx-background-image: url('/images/ground.jpg');"
                + " -fx-background-size: 100% 100%;");
        setFields();
        return pane;
    }

    private void setFields() {
        box.setTranslateX(0);
        box.setTranslateY(0);
        box.setPrefSize(pane.getPrefWidth(), 35);
        box.setStyle("-fx-background-color: black;");
        box.setSpacing(30);
        pane.getChildren().add(box);
        //Exit button
        exitButton = new Button("SALIR");
        exitButton.setId("exitButton");
        exitButton.setTextAlignment(TextAlignment.CENTER);
        exitButton.setAlignment(Pos.BASELINE_CENTER);
        exitButton.setFont(Font.font("Consolas",
                FontWeight.BOLD, FontPosture.REGULAR, 15));
        exitButton.setStyle("-fx-background-color: black; -fx-text-fill: red;"
                + "-fx-border-color: red; -fx-border-width: 2;");
        exitButton.setPrefSize(70, 35);
        //Label for the points
        lbPoints = new Label("PUNTOS: ");
        lbPoints.setTextAlignment(TextAlignment.CENTER);
        lbPoints.setAlignment(Pos.BASELINE_CENTER);
        lbPoints.setFont(Font.font("Consolas",
                FontWeight.BOLD, FontPosture.REGULAR, 20));
        lbPoints.setStyle("-fx-background-color: black; -fx-text-fill: orangered;");
        //Text field for the points 
        tfPoints = new Label("0");
        tfPoints.setTextAlignment(TextAlignment.CENTER);
        tfPoints.setAlignment(Pos.BASELINE_CENTER);
        tfPoints.setFont(Font.font("Jetbrains Mono",
                FontWeight.BOLD, FontPosture.REGULAR, 20));
        tfPoints.setStyle("-fx-background-color: black; -fx-text-fill: lime;");
        //Label for the lives
        lbLives = new Label("VIDAS RESTANTES: ");
        lbLives.setTextAlignment(TextAlignment.CENTER);
        lbLives.setAlignment(Pos.BASELINE_CENTER);
        lbLives.setFont(Font.font("Consolas",
                FontWeight.BOLD, FontPosture.REGULAR, 20));
        lbLives.setStyle("-fx-background-color: black; -fx-text-fill: orangered;");
        //Text field for the lives
        tfLives = new Label(INIT_LIVES);
        tfLives.setTextAlignment(TextAlignment.CENTER);
        tfLives.setAlignment(Pos.BASELINE_CENTER);
        tfLives.setFont(Font.font("Jetbrains Mono",
                FontWeight.BOLD, FontPosture.REGULAR, 20));
        tfLives.setStyle("-fx-background-color: black; -fx-text-fill: lime;");
        box.getChildren().add(lbPoints);
        box.getChildren().add(tfPoints);
        box.getChildren().add(lbLives);
        box.getChildren().add(tfLives);
        box.getChildren().add(exitButton);
        //ADD
        HBox.setMargin(lbPoints, new Insets(5, 5, 5, 5));
        HBox.setMargin(lbLives, new Insets(5, 5, 5, 5));
        HBox.setMargin(tfPoints, new Insets(5, 5, 5, 2));
        HBox.setMargin(tfLives, new Insets(5, 5, 5, 2));
        HBox.setMargin(exitButton, new Insets(5, 5, 5, 2));

        //Label to show the score record obteined
        lbRecord = new Label("RECORD: ");
        lbRecord.setFont(Font.font("Consolas",
                FontWeight.BOLD, FontPosture.REGULAR, 15));
        lbRecord.setStyle("-fx-text-fill: lime;");
        lbRecord.setTranslateX(0);
        lbRecord.setTranslateY(box.getHeight() + 50);
        lbRecord.setPrefSize(70, 30);
        //Text field for the lives
        tfRecord = new Label("0");
        tfRecord.setFont(Font.font("Jetbrains Mono",
                FontWeight.BOLD, FontPosture.REGULAR, 15));
        tfRecord.setStyle("-fx-text-fill: lime;");
        tfRecord.setTranslateX(lbRecord.getPrefWidth() + 2);
        tfRecord.setTranslateY(box.getHeight() + 55);
        tfRecord.setPrefSize(50, 20);
        //Label for winning or losing message
        createGameStatusLabel();
        //Adding to the pane
        pane.getChildren().add(lbRecord);
        pane.getChildren().add(tfRecord);
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public void pauseMediaPlayer() {
        mediaPlayer.pause();
    }

    public void resumeMediaPlayer() {
        mediaPlayer.play();
    }

    public void stopMediaPlayer() {
        mediaPlayer.dispose();
    }

    public void setGameStatusLabelText(String text) {
        gameStatusLabel.setText(text);
    }

    public void enableStatusLabel(boolean b) {
        gameStatusLabel.setVisible(b);
    }

    public void addSprite(Sprite sprite) {
        spriteListInView.add(sprite);
        pane.getChildren().add(sprite.getImage());
    }

    public void hideSprites() {
        spriteListInView.forEach(sprite -> {
            sprite.setVisible(false);
        });
    }

    public void remove() {
        if (!removeList.isEmpty()) {
            removeList.forEach(sprite -> {
                removeSprite(sprite);
            });
        }
    }

    public void addAllToRemovalList() {
        spriteListInView.forEach(sprite -> {
            addToRemovalList(sprite);
        });
    }

    public void addToRemovalList(Sprite sprite) {
        removeList.add(sprite);
    }

    private void removeSprite(Sprite sprite) {
        spriteListInView.remove(sprite);
        pane.getChildren().remove(sprite.getImage());
    }

    public void setPoints(String points) {
        tfPoints.setText(points);
    }

    public void setScoreRecord(int points) {
        tfRecord.setText(points + "");
    }

    public int getScore() {
        int score = Integer.parseInt(tfPoints.getText());
        return score;
    }

    public void decreaseLives() {
        Integer currentLives = Integer.parseInt(tfLives.getText());
        currentLives -= 1;
        tfLives.setText(currentLives.toString());
    }

    public double getPanePrefWidth() {
        return pane.getPrefWidth();
    }

    public double getPanePrefHeigth() {
        return pane.getPrefHeight();
    }

}
