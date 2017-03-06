/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import modele.Connexion;
import modele.Modele;
import modele.Sql;

/**
 *
 * @author Robin
 * @author Vincent
 */

/*
mode chapitre
    //0 for both
    //1 for presentiel
    //2 for distance
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
    public void actualiserAffichage() {
        modele.actualiserAffichage();
    }
    
    public boolean  creerBDD(String chemin) {
        boolean created = false;
        if (Connexion.isSQLite(chemin)) {
            created = modele.creerBDD(chemin);
        }
        return created;
    }
    
    public void ouvrirBDD(String chemin) {
        if (Connexion.isSQLite(chemin)) {
            modele.ouvrirBDD(chemin);
        }
    }
    
    public void ajouterChapitre(int modeChapitre, int numero, String libelle) {
        System.out.println(this.toString());
        if (Sql.addChapitre(numero, modeChapitre, libelle)) {
            this.actualiserAffichage();
        }
    }
    
    public void modifierChapitre(int modeChapitre, int numero, String libelle) {
        
    }
    
    public void supprimerChapitre(int modeChapitre, int numero) {
        
    }

    //TODO faire les fonctions pour utiliser les exercices à chapitre/devoir/examen
    
    public void ajouterExercice(int modeChapitre, int numeroChapitre, int numeroExercice, String dureeExercice, int pointExercice, String libelleExercice, String fichierExercice, String tagsExercice) {
        //TODO Vérifier dans Sql que ça fonctionne bien
        Sql.addExercice(numeroExercice, dureeExercice, pointExercice, libelleExercice, fichierExercice, tagsExercice, numeroChapitre);
        this.actualiserAffichage();
    }
    
    public void modifierExercice(int modeChapitre, int numeroChapitre, int numeroExercice, String dureeExercice, int pointExercice, String libelleExercice, String fichierExercice, String tagsExercice) {
        
    }
    
    public void supprimerExercice(int modeChapitre, int numeroChapitre, int numeroExercice) {
        
    }
    
    public void rechercherExercice(String tags) {
        String[] tagsTab = tags.split(",");
        modele.rechercherExercice(tagsTab);
    }
    
    public void rechercherExercice(String tags, java.util.Date dateDebut, java.util.Date dateFin) {
        String[] tagsTab = tags.split(",");
        java.sql.Date dateDebutSQL = dateDebut != null ? new java.sql.Date(dateDebut.getTime()) : null;
        java.sql.Date dateFinSQL = dateFin != null ? new java.sql.Date(dateFin.getTime()) : null;
        modele.rechercherExercice(dateDebutSQL, dateFinSQL, tagsTab);
    }
    
    public void deconnecter() {
        modele.deconnecter();
    }
    
}
