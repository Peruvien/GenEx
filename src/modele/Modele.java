/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import bdd.BDD;
import bdd.Connexion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Modele {
    
    //ATTRIBUTS
    private final Connexion connexion;
    
    private BDD bdd;
    
    //CONSTRUCTEUR
    public Modele() {
        connexion = new Connexion();
        /*
        try {
            BDD.createBDD(connexion,"/shared/test.sqlite3");
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void ouvrirBDD(String chemin) {
        bdd = new BDD(connexion, chemin);
    }
    
    public void creerBDD(String chemin) {
        try {
            bdd = BDD.createBDD(connexion, chemin);
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
}
