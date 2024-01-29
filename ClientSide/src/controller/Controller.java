package controller;

import client.Client;
import games.Game;
import games.GameController;
import games.model.GameStructures;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * This class Controller is in charge of controlling the graphical interface and
 * its functionality.
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public class Controller implements Initializable {

    private static Game gameView;
    private static Client client;
    private static boolean isAlreadyConnected = false;

    //Constants
    private final byte NEW_USER = 1;
    private final byte LOGIN = 2;
    private final byte TOP_PLAYERS = 3;
    private final byte EXIT = 4;
    private final byte PLAY = 5;
    private final byte GAME_FINISH = 6;

    private GameController gameControler;
    private GameStructures model;

    //authentication window
    //authentication window 
    @FXML
    private Button btnAuthentication;
    @FXML
    private Button btnBackAuthentication;
    @FXML
    private TextField tfUserName;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfSeePasswordAuthentication;

    //Inscription window
    @FXML
    private Button btnResgisterUser;
    @FXML
    private Button btnBackRegister;
    @FXML
    private TextField tfUserNameIns;
    @FXML
    private PasswordField tfPasswordIns;
    @FXML
    private TextField tfSeePasswordRegister;

    //Principal window
    @FXML
    private Button btnInscription;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnAuthenticationPrin;

    //Game menu window 
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnTopScore;
    @FXML
    private Button btnInstructions;
    @FXML
    private Button btnBack;

    //Top score window
    @FXML
    private TextArea topScoreTA;
    @FXML
    private Button btBackTopScore;

    //Instrcutions window
    @FXML
    private Label lbinstructions;
    @FXML
    private Button btnBackInstructions;

    public Controller() {
        try {
            if (!isAlreadyConnected) {
                Controller.client = new Client();
                isAlreadyConnected = true;
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * The method principalView is responsible for controlling the actions of
     * the buttons in the main window.
     *
     * @param event action performed on the buttons in the main window.
     * @throws IOException
     */
    @FXML
    public void principalView(ActionEvent event) throws IOException {
        if (event.getSource() == btnInscription) {
            openInscriptionWindow();
            closeView(btnInscription);
        } else if (event.getSource() == btnAuthenticationPrin) {
            openAuthenticationWindow();
            closeView(btnAuthenticationPrin);
        } else if (event.getSource() == btnExit) {
            client.writeInt(EXIT); //Para indicarle al server que el usuario termino
            closeView(btnExit);
            client.closeClient();
        }
    }

    /**
     * The method backTopScore is responsible for controlling the actions of the
     * buttons in the top score window.
     *
     * @param event action performed on the buttons in the inscription window.
     * @throws IOException
     */
    @FXML
    public void backTopScore(ActionEvent event) throws IOException {
        closeView(btBackTopScore);
        openGameMenuWindow();
    }

    /**
     * The method inscriptionView is responsible for controlling the actions of
     * the buttons in the inscription window.
     *
     * @param event action performed on the buttons in the inscription window.
     * @throws IOException
     */
    @FXML
    public void inscriptionView(ActionEvent event) throws IOException {
        if (event.getSource() == btnResgisterUser) {
            String errors = "";
            if (tfUserNameIns.getText().trim().length() < 5) {
                errors += "El nombre de usuario debe tener mas de 5 caracteres!!!\n";
            }
            if (tfPasswordIns.getText().trim().length() < 5) {
                errors += "La contraseña debe tener mas de 5 caracteres!!!\n";
            }
            char[] userNameArray = tfUserNameIns.getText().toCharArray();
            char[] passWordArray = tfPasswordIns.getText().toCharArray();
            //Verificar nombre usuario
            for (char element : userNameArray) {
                if (element == ',') {
                    errors += "El nombre de usuario no puede tener ','\n";
                    break;
                }
            }
            //Verificar contraseña
            for (char element : passWordArray) {
                if (element == ',') {
                    errors += "La contraseña no puede tener ','\n";
                    break;
                }
            }
            if (errors.isEmpty()) {
                client.writeInt(NEW_USER);
                client.writeUTF(tfUserNameIns.getText());
                client.writeUTF(tfPasswordIns.getText());
                Alert fail = new Alert(AlertType.INFORMATION);
                fail.setHeaderText(client.readUTF());
                fail.showAndWait();
                if (client.readBoolean()) {
                    openPrincipalWindow();
                    closeView(btnResgisterUser);
                }
            } else {
                Alert fail = new Alert(AlertType.WARNING);
                fail.setHeaderText("ATENCIÖN");
                fail.setContentText(errors);
                fail.showAndWait();
            }

        } else if (event.getSource() == btnBackRegister) {
            openPrincipalWindow();
            closeView(btnBackRegister);
        }
    }

    /**
     * The method seePasswordRegister is responsible for displaying the password
     * in inscription window.
     *
     * @param event action of pressing a button.
     * @throws IOException
     */
    @FXML
    public void seePasswordRegister(MouseEvent event) throws IOException {
        tfSeePasswordRegister.setText(tfPasswordIns.getText());
        tfSeePasswordRegister.setVisible(true);
        tfPasswordIns.setVisible(false);
    }

    /**
     * The method hidePasswordRegister is responsible for hiding the password in
     * inscription window.
     *
     * @param event action when a button is released.
     * @throws IOException
     */
    @FXML
    public void hidePasswordRegister(MouseEvent event) throws IOException {
        tfSeePasswordRegister.setVisible(false);
        tfPasswordIns.setVisible(true);
    }

    /**
     * The method authenticationView is responsible for controlling the actions
     * of the buttons in the authentication window.
     *
     * @param event action performed on the buttons in the authentication
     * window.
     * @throws IOException
     */
    @FXML
    public void authenticationView(ActionEvent event) throws IOException {
        if (event.getSource() == btnAuthentication) {
            client.writeInt(LOGIN);
            client.writeUTF(tfUserName.getText());
            client.writeUTF(tfPassword.getText());
            JOptionPane.showMessageDialog(null, client.readUTF(),
                    "Validación", JOptionPane.INFORMATION_MESSAGE);
            if (client.readBoolean()) {
                openGameMenuWindow();
                closeView(btnAuthentication);
            }
        } else if (event.getSource() == btnBackAuthentication) {
            openPrincipalWindow();
            closeView(btnBackAuthentication);
        }
    }

    /**
     * The method seePasswordAuthentication is responsible for displaying the
     * password in authentication window.
     *
     * @param event action of pressing a button.
     * @throws IOException
     */
    @FXML
    public void seePasswordAuthentication(MouseEvent event) throws IOException {
        tfSeePasswordAuthentication.setText(tfPassword.getText());
        tfSeePasswordAuthentication.setVisible(true);
        tfPassword.setVisible(false);
    }

    /**
     * The method hidePasswordAuthentication is responsible for hiding the
     * password in authentication window.
     *
     * @param event action when a button is released.
     * @throws IOException
     */
    @FXML
    public void hidePasswordAuthentication(MouseEvent event) throws IOException {
        tfSeePasswordAuthentication.setVisible(false);
        tfPassword.setVisible(true);
    }

    /**
     * The method gameMenuView is responsible for controlling the actions of the
     * buttons in the game menu window.
     *
     * @param event action performed on the buttons in the game menu window.
     * @throws IOException
     */
    @FXML
    public void gameMenuView(ActionEvent event) throws IOException {
        if (event.getSource() == btnPlay) {
            closeView(btnPlay);
            openGameWindow();
            checkGameState();
        } else if (event.getSource() == btnTopScore) {
            FXMLLoader loader = openTopScoreWindow();
            client.writeInt(TOP_PLAYERS);
            String top10Players = client.readUTF();
            ((Controller) loader.getController()).topScoreTA.setText(top10Players);
            closeView(btnTopScore);
        } else if (event.getSource() == btnInstructions) {
            openInstructionsWindow();
            closeView(btnInstructions);
        } else {
            openPrincipalWindow();
            closeView(btnBack);
        }
    }

    /**
     * The method gameInstructionsView is responsible for controlling the
     * actions of the buttons in the instruction window.
     *
     * @param event action performed on the buttons in the instructions window.
     * @throws IOException
     */
    @FXML
    public void gameInstructionsView(ActionEvent event) throws IOException {
        if (event.getSource() == btnBackInstructions) {
            openGameMenuWindow();
            closeView(btnBackInstructions);
        }
    }

    /**
     * The method openPrincipalWindow load and open the main window.
     */
    private void openPrincipalWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PrincipalWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("ENTRADA");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method openInscriptionWindow load and open the inscription window.
     */
    public void openInscriptionWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InscriptionWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("REGISTRO");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method openAuthenticationWindow load and open the authentication
     * window.
     */
    public void openAuthenticationWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AuthenticationWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("INGRESO");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method openGameMenuWindow load and open the game menu window.
     */
    public void openGameMenuWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("MENU DE JUEGO");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method openGameWindow load and open the game window.
     */
    public void openGameWindow() {
        try {
            client.writeInt(PLAY);
            gameView = new Game();
            gameView.start(new Stage());
            model = new GameStructures();
            gameControler = new GameController(gameView, client, model);
            model.setPlayer(gameControler.getPlayer());
            gameView.setController(gameControler);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The method openTopScoreWindow load and open the top score window.
     *
     * @return
     */
    public FXMLLoader openTopScoreWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TopScoreWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("BEST PLAYERS");
            stage.setScene(scene);
            stage.show();
            return loader;
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * The method openInstructionsWindow load and open the instructions window.
     */
    public void openInstructionsWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InstructionsWindow.fxml"));
            Parent root = loader.load();
            ((Controller) loader.getController()).lbinstructions.setText("1. Utilice las flechas del teclado para el movimiento\n(derecha-izquierda).\n"
                    + "2. Para disparar debe precionar la tecla de espaciado.\n"
                    + "3. Cada vez que recibes un disparo pierdes una vida,\n solo tienes 3 vidas.\n"
                    + "4. Al destruir un alien recibes puntos:\n\taliens de la fila 1 = 10 puntos\n\taliens de la fila 2 = 20 puntos\n"
                    + "\taliens de la fila 3 = 40 puntos\n\talien invasor = 80-150 puntos\n");//ejemplo de recibir mensajes
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("INSTRUCCIONES");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method closeView close the window.
     *
     * @param button belongs to the window to be closed.
     */
    private void closeView(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * It works as an event handler for the exit buttons, 
     * it also resumes the game
     * stop the game
     * pause the game
     */
    private void checkGameState() {
        gameView.getExitButton().setOnMouseClicked((MouseEvent e) -> {

            gameControler.stop();
            gameView.pause();

            gameView.getConfirmButton().setOnMouseClicked(event -> {
                gameControler.remove();
                model.remove();
                gameView.remove();
                try {
                    //Le indico al server que termine para que escriba los datos
                    client.writeInt(GAME_FINISH);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                if (GameController.GAME_RUNNING) {
                    gameControler.remove();
                    model.remove();
                    gameView.remove();
                } else {
                    model.remove();
                    gameView.remove();
                }
                gameView.close();
                openGameMenuWindow();
            });
            gameView.getBackButton().setOnMouseClicked(ev -> {
                gameView.dismiss();
                gameControler.start();
            });
        });
    }

}
