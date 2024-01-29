/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import model.User;
import server.UserList;

/**
 * This class is in charge of write and read the storage users
 * Write the new users his user id, score and the password
 * @author Steven Ramirez
 * @author Keylor Barboza
 * @author Juan Ignacio Orozco
 */
public final class RWManager {

    private final static String ROUTE = "userData.properties";
    private static final File file = new File(ROUTE);

    /**
     * Read the users, use the FileReader, BufferedReader to read
     * @param list receive the list that work as the temp storage and also
     * constais all the logic of the users, is a list Of User objects.
     * @throws FileNotFoundException
     */
    public static void read(UserList list) throws FileNotFoundException {
        try {
            if (!file.exists()) {
                file.createNewFile();
                return;
            }
            FileReader rw = new FileReader(file);
            BufferedReader reader = new BufferedReader(rw);
            String content = "";
            String[] usersData;
            while ((content = reader.readLine()) != null) {
                usersData = content.split(",");
                User newUser = new User(usersData[0], usersData[1]);
                newUser.setScore(Integer.parseInt(usersData[2]));
                list.addUser(newUser);
            }
            reader.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Write all the users to the file
     * @param list receives the list that contains all the users
     * @throws FileNotFoundException
     */
    public static void writeLn(UserList list) throws FileNotFoundException {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            Collection<User> values = list.getValues();
            String userStringLine = "";
            for (User user : values) {
                userStringLine = user.getName() + ","
                        + user.getPassWord() + "," + user.getScore();
                bw.write(userStringLine + "\n");
            }
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
