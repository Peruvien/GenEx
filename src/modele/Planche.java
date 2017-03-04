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
    private final int idPlanche;
    private Chapitre chapitre;
    //0 for both
    //1 for presentiel
    //2 for distance
    private int modePlanche;
    private final int numeroPlanche;
    private String libellePlanche;
    private File fichierPlanche;
    private final Set<Exercice> exercices;
    
    
    //CONSTRUCTEUR
    public Planche(int idPlanche, int numeroPlanche, String libellePlanche, String fichierPlanchePath) {
        this.idPlanche = idPlanche;
        this.numeroPlanche = numeroPlanche;
        this.libellePlanche = libellePlanche;
        this.fichierPlanche = new File(fichierPlanchePath);
        exercices = new TreeSet<>();
    }
    public Planche(int idCours, int numeroCours, String libelleCours, String fichierCoursPath, Chapitre chapitreCours) {
        this(idCours, numeroCours, libelleCours, fichierCoursPath);
        this.chapitre = chapitreCours;
    }
    
    
    //ACCESSEURS
    public int getIDPlanche() {
        return idPlanche;
    }

    public Chapitre getChapitre() {
        return chapitre;
    }

    public int getNumeroCours() {
        return numeroPlanche;
    }

    public String getFichier() {
        return fichierPlanche.getAbsolutePath();
    }

    public int getModePlanche() {
        return modePlanche;
    }
    
    public Set<Exercice> getExercices() {
        return exercices;
    }
    
    @Override
    public String toString() {
         String res = "Présentiel : " + chapitre.isPresentiel() + "\n";
        res += "Numéro de chapitre : " + chapitre.getNumeroChapitre() + "\n";
        res += "Numéro de cours : " + numeroPlanche + "\n";
        res += "Libellé : " + libellePlanche + "\n";
        res += "Fichier : " + fichierPlanche.getAbsolutePath();
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
            res = numeroPlanche - o.numeroPlanche;
        }
        return res;
    }
}
