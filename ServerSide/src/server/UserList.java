/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import model.User;

/**
 * this class is used to store users, to get them during execution, 
 * and also to do certain validations
 * @author Steven ramirez
 * @author Keylor Barboza
 * @author Juan Ignacio Orozco
 */
public final class UserList {

    private final HashMap<String, User> userList;
    private static UserList instance;

    private UserList() {
        this.userList = new HashMap<>();
    }

    /**
     * Follow the Singleton principle
     * @return the static instance
     */
    public static UserList getInstance() {
        if (instance == null) {
            return new UserList();
        } else {
            return instance;
        }
    }

    /**
     * The values of the current hashMap are traversed 
     * and stored in a TreeSet, in order to obtain the 10 best scores
     * the 10 best scores
     * @return an arraylist of the top users
     */
    public ArrayList<User> getTopUsers() {
        TreeSet<User> users = new TreeSet<>(userList.values());
        ArrayList<User> topUsers = new ArrayList<>();
            for (int i = 10, j = 0; i >= 0; i--, j++) {
                topUsers.add(j, users.pollLast());
            }
        return topUsers;
    }

    /**
     * It is used in the GameThread to check if a user already exist
     * @param userName check if in the current hashmap exist the username
     * given, if it exist return true, if not return false
     * @return
     * @see GameThread class
     */
    public boolean checkUserName(String userName) {
        return userList.containsKey(userName);
    }

    /**
     * it verifies if a key (password) is associated according to the given 
     * username. If the given password does not match the current 
     * saved username, it returns false
     * @param userName User username attribute
     * @param passWord User password attribute
     * @return
     */
    public boolean checkPassWord(String userName, String passWord) {
        return userList.get(userName).getPassWord().equals(passWord);
    }

    /**
     * add a user to the hashMap
     * @param user
     */
    public void addUser(User user) {
        userList.put(user.getName(), user);
    }
    
    /**
     * return a User object by the id associated
     * @param userName
     * @return
     */
    public User getUser(String userName) {
        return userList.get(userName);
    }
    
    /**
     * This method is used in order to write all the users, and storage 
     * they locally
     * @return a Collection of users
     */
    public Collection<User> getValues() {
        return userList.values();
    }

    @Override
    public String toString() {
        return "";
    }

}
