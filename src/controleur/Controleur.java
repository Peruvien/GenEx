/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.Date;
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
    private boolean isSQLite(String lowerCase) {
        return (lowerCase.endsWith(".sdb") || lowerCase.endsWith(".sqlite")
             || lowerCase.endsWith(".db2") || lowerCase.endsWith(".s2db")
             || lowerCase.endsWith(".sqlite2") || lowerCase.endsWith(".sl2")
             || lowerCase.endsWith(".db3") || lowerCase.endsWith(".s3db")
             || lowerCase.endsWith(".sqlite3") || lowerCase.endsWith(".sl3"));
    }
    
    
    //MUTATEURS
    public void actualiserAffichage() {
        modele.actualiserAffichage();
    }
    
    public boolean  creerBDD(String chemin) {
        String lowerCase = chemin.toLowerCase();
        boolean isSQLite = isSQLite(lowerCase);
        boolean created = false;
        if (isSQLite) {
            created = modele.creerBDD(chemin);
        }
        return created;
    }
    
    public void ouvrirBDD(String chemin) {
        String lowerCase = chemin.toLowerCase();
        if (isSQLite(lowerCase)) {
            modele.ouvrirBDD(chemin);
        }
    }
    
    public void ajouterChapitre(boolean presentiel, int numero, String libelle) {
        
    }
    
    public void modifierChapitre(boolean presentiel, int numero, String libelle) {
        
    }
    
    public void supprimerChapitre(boolean presentiel, int numero) {
        
    }
    
    public void rechercherExercice(String tags) {
        String[] tagsTab = tags.split(",");
        modele.rechercherExercice(tagsTab);
    }
    
    public void rechercherExercice(String tags, Date dateDebut, Date dateFin) {
        String[] tagsTab = tags.split(",");
        java.sql.Date dateDebutSQL = dateDebut != null ? new java.sql.Date(dateDebut.getTime()) : null;
        java.sql.Date dateFinSQL = dateFin != null ? new java.sql.Date(dateFin.getTime()) : null;
        modele.rechercherExercice(dateDebutSQL, dateFinSQL, tagsTab);
    }
    
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
