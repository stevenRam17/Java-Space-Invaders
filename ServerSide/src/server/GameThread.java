/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import IOmanager.RWManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import model.User;

/**
 * This class administrate the logic of accept requests
 * Respond according to the request made
 * @author Steven Ramirez
 * @author Keylor Barboza
 * @author Juan Ignacio Orozco
 */
public class GameThread extends Thread {

    //Encapsulate object of streams and socket
    private final Connection connection;
    //Possible options a client can send or request
    private final byte NEW_USER = 1;
    private final byte LOGIN = 2;
    private final byte TOP_PLAYERS = 3;
    private final byte EXIT = 4;
    private final byte PLAY = 5;
    private final byte GAME_FINISH = 6;
    private final byte PLAYER_LOST_LIVE = 7;
    private final byte PLAYER_KILL_ALIEN = 8;
    //List of users
    private final UserList userList;
    private User recentUser;
    private int pastUserRecord;
    private final int LIVES = 3;
    private int playerLives;
    private boolean isAlreadyLoged = false;
    //Score related
    private final Random rand = new Random();
    private final int MIN_POINTS = 80;
    private final int MAX_POINTS = 150;

    /**
     * @param connection the socket to which connections will be allowed
     * @param userList the shared list
     * @throws IOException
     */
    public GameThread(Socket connection, UserList userList) throws IOException {
        this.connection = new Connection(connection);
        this.userList = userList;
    }
    
    /**
     * the run thread method
     */
    @Override
    public void run() {
        try {
            listenToUserRequest();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Calls the connection closeConnection method that close the streams
     * and also the socket
     */
    private void closeConnection() {
        System.out.println("\nTerminating connection\n\n");
        try {
            connection.closeConnection();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * This method contains all the logic of accepting, manage, and return 
     * data of the current users 
     * @throws IOException 
     */
    private void listenToUserRequest() throws IOException {
        int option;
        String userName;
        String password;
        boolean finish = false;
        while (!finish) {
            option = connection.readInt();
            if (option == NEW_USER) {
                userName = connection.readUTF().trim();
                password = connection.readUTF();
                if (userList.checkUserName(userName)) {
                    connection.writeUTF("El nombre de usuario ya está seleccionado!");
                    connection.writeBoolean(false);
                } else {
                    recentUser = new User(userName, password);
                    userList.addUser(recentUser);
                    userList.toString();
                    connection.writeUTF("Usuario creado correctamente!");
                    connection.writeBoolean(true);
                }
            }

            if (option == LOGIN) {
                userName = connection.readUTF().trim();
                password = connection.readUTF();
                if (userList.checkUserName(userName)
                        && userList.checkPassWord(userName, password)) {
                    recentUser = userList.getUser(userName);
                    connection.writeUTF("Bienvenido!");
                    connection.writeBoolean(true);//To continue or not
                    isAlreadyLoged = true;
                } else {
                    connection.writeUTF("Contraseña incorrecta y/o usuario "
                            + "incorrecto");
                    connection.writeBoolean(false);
                }
            }

            if (option == PLAY) {
                if (isAlreadyLoged) {
                    //Se guarda el record anterior del usuario y se le resetea
                    //con el fin de ver si aumento o no su record
                    pastUserRecord = recentUser.getScore();
                    recentUser.setScore(0);
                    //Cada vez que le da play obtiene el record a batir y sus 3 nuevas vidas
                    playerLives = LIVES;
                    connection.writeInt(pastUserRecord);
                    connection.writeInt(playerLives);
                }
            }

            if (option == TOP_PLAYERS) {
                byte counter = 0;
                String content = "";
                for (User topPlayer : userList.getTopUsers()) {
                    if (topPlayer == null) {
                        continue;
                    }
                    content += ++counter + "- " + topPlayer.toString() + "\n";
                }
                connection.writeUTF(content);
            }

            if (option == GAME_FINISH) {
                writeUserData();
            }

            if (option == EXIT) {
                finish = true;
                closeConnection();
            }

            if (option == PLAYER_LOST_LIVE) {
                playerLives--;
                connection.writeInt(playerLives);
            }

            if (option == PLAYER_KILL_ALIEN) {
                int type = connection.readInt();
                int score = getScoreByType(type);
                updateUserScore(score);
                connection.writeInt(recentUser.getScore());
            }
        }
    }
    
    /**
     * receive the points they will be added to the current score of the client
     * @param points
     */
    private void updateUserScore(int points) {
        recentUser.setScore(recentUser.getScore() + points);
    }

    private void writeUserData() throws FileNotFoundException {
        int newScore = recentUser.getScore();
        if (pastUserRecord < newScore) {
            recentUser.setScore(newScore);
        } else {
            recentUser.setScore(pastUserRecord);
        }
        String userData = recentUser.getName() + ","
                + recentUser.getPassWord() + "," + recentUser.getScore();
        RWManager.writeLn(userList);
    }

    /**
     * Return the score that by the type
     * @param alienType
     * @return 
     */
    private int getScoreByType(int alienType) {
        int score = 0;
        switch (alienType) {
            case 1 -> //Low alien
                score = 10;
            case 2 -> //Middle
                score = 20;
            case 3 -> //Top
                score = 40;
            case 4 -> //Super
                score = rand.nextInt(((MAX_POINTS - MIN_POINTS) + 1) + MIN_POINTS);
        }
        return score;
    }

}
