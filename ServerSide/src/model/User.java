package model;

import java.util.Objects;

/**
 *
 * @author Steven Ramirez
 * @author Keylor Barboza
 * @author Juan Ignacio Orozco
 */
public class User implements Comparable<User> {
    
    private final String name; 
    private final String passWord; 
    private int score; 

    /**
     * Only receives the name and password, and then the score is set
     * @param name the name of the object User
     * @param passWord the password of the object User
     */
    public User(String name, String passWord) {
        this.name = name;
        this.passWord = passWord;
        this.score = 0; 
    }

    /**
     * 
     * @return the name of the object User
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the password of the object User
     */
    public String getPassWord() {
        return passWord;
    }
    
    /**
     *
     * @return the score of the object User
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return the object string representation
     */
    @Override
    public String toString() {
        return "Nombre: " + name + ", puntuación: " + score;
    }

    @Override
    public int compareTo(User u) {
        return this.score - u.getScore();
    }
    
    
}
