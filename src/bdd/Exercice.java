/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.File;
import java.sql.Time;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Exercice implements Comparable<Exercice> {
    
    //ATTRIBUTS
    private Chapitre chapitreExercice;
    private final int idExercice;
    private final int numeroExercice;
    private final Time duree;
    private final int points;
    private final String libelle;
    private final File fichier;
    
    
    //CONSTRUCTEUR
    public Exercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice, String fichierExercicePath) {
        this.idExercice = idExercice;
        this.numeroExercice = numeroExercice;
        this.duree = dureeExercice;
        this.points = pointsExercice;
        this.libelle = libelleExercice;
        this.fichier = new File(fichierExercicePath);
    }
    public Exercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice, String fichierExercicePath, Chapitre chapitreExercice) {
        this(idExercice, numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath);
        this.chapitreExercice = chapitreExercice;
    }
    
    
    //ACCESSEURS
    public Chapitre getChapitre() {
        return chapitreExercice;
    }
    
    public int getID() {
        return idExercice;
    }
    
    public int getNumero() {
        return numeroExercice;
    }
    
    public Time getDuree() {
        return duree;
    }
    
    public int getPoints() {
        return points;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    @Override
    public String toString() {
        String res = "Présentiel : " + chapitreExercice.isPresentiel() + "\n";
        res += "Numéro de chapitre : " + chapitreExercice.getNumeroChapitre() + "\n";
        res += "Numéro d'exercice : " + numeroExercice + "\n";
        res += "Libellé : " + libelle + "\n";
        return res;
    }
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(Exercice o) {
        return idExercice - o.idExercice;
    }
}
