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
    private String tags;
    
    
    //CONSTRUCTEUR
    public Exercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice, String fichierExercicePath, String tags) {
        this.idExercice = idExercice;
        this.numeroExercice = numeroExercice;
        this.duree = dureeExercice;
        this.points = pointsExercice;
        this.libelle = libelleExercice;
        this.fichier = new File(fichierExercicePath);
        this.tags = tags;
    }
    public Exercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice, String fichierExercicePath, String tags, Chapitre chapitreExercice) {
        this(idExercice, numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
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
    
    public String getTags() {
        return tags;
    }
    
    @Override
    public String toString() {
        String res = "Présentiel : " + chapitreExercice.isPresentiel() + "\n";
        res += "Numéro de chapitre : " + chapitreExercice.getNumeroChapitre() + "\n";
        res += "Numéro d'exercice : " + numeroExercice + "\n";
        res += "Temps : " + duree + "\n";
        res += "Points : " + points + "\n";
        res += "Libellé : " + libelle + "\n";
        res += "Fichier : " + fichier.getAbsolutePath();
        return res;
    }
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public boolean equals(Object o) {
        if (o instanceof Exercice) {
            return ((Exercice)o).compareTo(this) == 0;
        }
        return false;
    }
    
    @Override
    public int compareTo(Exercice o) {
        return idExercice - o.idExercice;
    }
}
