/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import games.entities.Alien;

/**
 * This interface function like the father of all specific factorys
 *
 * @author Steven Ramírez, Ignacio Orozco & Keylor Barboza.
 */
public interface AlienFactory {
    
    public Alien createAlien(int x, int y); 
   
}
