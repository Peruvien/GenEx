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
 * @author robin
 * @author Vincent
 */
public class Exercice implements Comparable<Exercice> {
    
    //ATTRIBUTS
    private final int idExercice;
    private final int numeroExercice;
    private final Time duree;
    private final int points;
    private final String libelle;
    private final File fichier;
    
    
    //CONSTRUCTEUR
    public Exercice(int idExercice, int numeroExercice, Time duree, int points, String libelle, String fichier) {
        this.idExercice = idExercice;
        this.numeroExercice = numeroExercice;
        this.duree = duree;
        this.points = points;
        this.libelle = libelle;
        this.fichier = new File(fichier);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(Exercice o) {
        return idExercice - o.idExercice;
    }
}
