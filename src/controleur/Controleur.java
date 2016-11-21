/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.Modele;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Controleur {
    
    //ATTRIBUTS
    private final Modele modele;
    
    
    //CONSTRUCTEUR
    public Controleur(Modele modele) {
        this.modele = modele;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
