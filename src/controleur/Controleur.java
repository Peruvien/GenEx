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
    public void creerBDD(String chemin) {
        String lowerCase = chemin.toLowerCase();
        if (lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
         || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
         || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
         || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
         || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
         || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
         || lowerCase.endsWith(".sl3")) {
            modele.creerBDD(chemin);
        }
    }
    
    
    public void ouvrirBDD(String chemin) {
        String lowerCase = chemin.toLowerCase();
        if (lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
         || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
         || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
         || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
         || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
         || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
         || lowerCase.endsWith(".sl3") || lowerCase.endsWith(".kexi")) {
            modele.ouvrirBDD(chemin);
        }
    }
    
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
