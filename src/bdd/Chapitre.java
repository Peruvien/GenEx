/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Chapitre implements Comparable<Chapitre> {
    
    //ATTRIBUTS
    private final int idChapitre;
    private int numeroChapitre;
    private boolean presentiel;
    private String libelle;
    private Set<Exercice> exercices;
    private Set<Cours> cours;
    
    
    //CONSTRUCTEUR
    public Chapitre(int idChapitre, int numeroChapitre, boolean presentiel, String libelle) {
        this.idChapitre = idChapitre;
        this.numeroChapitre = numeroChapitre;
        this.presentiel = presentiel;
        this.libelle = libelle;
        
        exercices = new TreeSet<>();
        cours = new TreeSet<>();
    }
    
    
    //ACCESSEURS
    public boolean isPresentiel() {
        return presentiel;
    }
    
    public Set<Exercice> getExercices() {
        return exercices;
    }
    
    public Set<Cours> getCours() {
        return cours;
    }
    
    public int getIdChapitre() {
        return idChapitre;
    }

    public int getNumeroChapitre() {
        return numeroChapitre;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    @Override
    public String toString() {
        String res = "Présentiel : " + presentiel + "\n";
        res += "Numéro de chapitre : " + numeroChapitre + "\n";
        res += "Libellé : " + libelle;
        return res;
    }
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
    public void setNumeroChapitre(int numeroChapitre) {
        this.numeroChapitre = numeroChapitre;
    }
    
    public void setPresentiel(boolean presentiel) {
        this.presentiel = presentiel;
    }
    
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    public void setExercices(Set<Exercice> exercices) {
        this.exercices = exercices;
    }
    
    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }
    
    public void addCours(Cours td){
        this.cours.add(td);
    }
    
    public void resetCours(){
        this.cours.clear();
    }
    
    
    //COMPARABLE
    @Override
    public boolean equals(Object o) {
        if (o instanceof Chapitre) {
            return idChapitre == ((Chapitre) o).idChapitre;
        }
        return false;
    }
    
    @Override
    public int compareTo(Chapitre o) {
        return idChapitre - o.idChapitre;
    }
}
