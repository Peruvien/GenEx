/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Cours implements Comparable<Cours> {
    
    //ATTRIBUTS
    private final int idCours;
    private Chapitre chapitre;
    private final int numeroCours;
    private String libelle;
    private File fichierCours;
    private final Set<Exercice> exercices;
    
    //CONSTRUCTEUR
    public Cours(int idCours, int numeroCours, String fichierCoursPath) {
        this.idCours = idCours;
        this.numeroCours = numeroCours;
        this.fichierCours = new File(fichierCoursPath);
        exercices = new TreeSet<>();
    }
    public Cours(int idCours, int numeroCours, String fichierCoursPath, Chapitre chapitreCours) {
        this(idCours,numeroCours,fichierCoursPath);
        this.chapitre = chapitreCours;
    }
    
    
    //ACCESSEURS
    public int getIDCours() {
        return idCours;
    }

    public Chapitre getChapitre() {
        return chapitre;
    }

    public int getNumeroCours() {
        return numeroCours;
    }

    public File getFichier() {
        return fichierCours;
    }
    
    @Override
    public String toString() {
         String res = "Présentiel : " + chapitre.isPresentiel() + "\n";
        res += "Numéro de chapitre : " + chapitre.getNumeroChapitre() + "\n";
        res += "Numéro de cours : " + numeroCours + "\n";
        res += "Fichier : " + fichierCours.getAbsolutePath();
        return res;
    }
    
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
    //COMPARABLE
    @Override
    public int compareTo(Cours o) {
        int res = chapitre.compareTo(o.chapitre);
        if (res == 0) {
            res = numeroCours - o.numeroCours;
        }
        return res;
    }
}
