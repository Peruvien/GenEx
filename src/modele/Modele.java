/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import bdd.Connexion;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Modele {
    
    //ATTRIBUTS
    private final Connexion connexion;
    
    //CONSTRUCTEUR
    public Modele() {
        connexion = new Connexion();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void deconnecter() {
        connexion.deconnecter();
    }
    
}
