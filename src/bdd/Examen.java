/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Examen implements Comparable<Examen> {
    
    //ATTRIBUTS
    private final int idExamen;
    private final Date date;
    private final Time duree;
    private final String libelle;
    private final File fichier;
    private final Set<Exercice> exercices;
    
    
    //CONSTRUCTEUR
    public Examen(int idExamen, Date date, Time duree, String libelle, String fichier) {
        this.idExamen = idExamen;
        this.date = date;
        this.duree = duree;
        this.libelle = libelle;
        this.fichier = new File(fichier);
        this.exercices = new TreeSet<>();
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        String res = "Date : " + date.toString() + "\n";
        res += "Durée : " + duree.toString() + "\n";
        res += "Libellé : " + libelle + "\n";
        res += "Fichier : " + fichier.getAbsolutePath() + "\n";
        return res;
    }
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
    
    //COMPARABLE
    @Override
    public int compareTo(Examen o) {
        return idExamen - o.idExamen;
    }
}
