/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import bdd.*;
import controleur.Controleur;
import modele.Modele;
import preferences.Preferences;
import vue.Fenetre;

import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dossPrefs = new File(System.getProperty("user.home") + File.separator + ".GenEx");
        dossPrefs.mkdir();
        Preferences preferences = new Preferences(dossPrefs.getPath() + File.separator + "Preferences.ini");
        
        Modele modele = new Modele();
        
        Controleur controleur = new Controleur(modele);
        
        Fenetre fenetre = new Fenetre(controleur, preferences);
        
        modele.setObserver(fenetre);
        modele.actualiserAffichage();
    }
    
}
