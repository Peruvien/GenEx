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
    private int idExercice;
    private int numeroExercice;
    private Time duree;
    private int points;
    private String libelle;
    private File fichier;
    
    
    //CONSTRUCTEUR
    public Exercice() {
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(Exercice o) {
        return idExercice - o.idExercice;
    }
}
