/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Planche implements Comparable<Planche> {
    
    //ATTRIBUTS
    private final int idCours;
    private Chapitre chapitre;
    //0 for both
    //1 for presentiel
    //2 for distance
    private int modeCours;
    private final int numeroCours;
    private String libelleCours;
    private File fichierCours;
    private final Set<Exercice> exercices;
    
    
    //CONSTRUCTEUR
    public Planche(int idCours, int numeroCours, String libelleCours, String fichierCoursPath) {
        this.idCours = idCours;
        this.numeroCours = numeroCours;
        this.libelleCours = libelleCours;
        this.fichierCours = new File(fichierCoursPath);
        exercices = new TreeSet<>();
    }
    public Planche(int idCours, int numeroCours, String libelleCours, String fichierCoursPath, Chapitre chapitreCours) {
        this(idCours, numeroCours, libelleCours, fichierCoursPath);
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

    public String getFichier() {
        return fichierCours.getAbsolutePath();
    }

    public int getModeCours() {
        return modeCours;
    }
    
    public Set<Exercice> getExercices() {
        return exercices;
    }
    
    @Override
    public String toString() {
         String res = "Présentiel : " + chapitre.isPresentiel() + "\n";
        res += "Numéro de chapitre : " + chapitre.getNumeroChapitre() + "\n";
        res += "Numéro de cours : " + numeroCours + "\n";
        res += "Libellé : " + libelleCours + "\n";
        res += "Fichier : " + fichierCours.getAbsolutePath();
        return res;
    }
    
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
    //COMPARABLE
    @Override
    public int compareTo(Planche o) {
        int res = chapitre.compareTo(o.chapitre);
        if (res == 0) {
            res = numeroCours - o.numeroCours;
        }
        return res;
    }
}
