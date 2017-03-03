/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

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
    //0 for both
    //1 for presentiel
    //2 for distance
    private int modeChapitre;
    private String libelle;
    private Set<Exercice> exercices;
    private Set<Cours> cours;
    
    
    //CONSTRUCTEUR
    public Chapitre(int idChapitre, int numeroChapitre, int modeChapitre, String libelle) {
        this.idChapitre = idChapitre;
        this.numeroChapitre = numeroChapitre;
        this.modeChapitre = modeChapitre;
        this.libelle = libelle;
        
        exercices = new TreeSet<>();
        cours = new TreeSet<>();
    }
    
    
    //ACCESSEURS
    public int isPresentiel() {
        return modeChapitre;
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
        String res = "Présentiel : ";
        switch (modeChapitre){
            case 0:
                res += "presentiel et distance ";
                break;
            case 1:
                res += "presentiel ";
                break;
            case 2:
                res += "distance ";
                break;
            default:
                res += "erreur ";
                break;
        }
        res += "\nNuméro de chapitre : " + numeroChapitre + "\n";
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
    
    public void setPresentiel(int modeChapitre) {
        this.modeChapitre = modeChapitre;
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

    public int getModeChapitre() {
        return modeChapitre;
    }
}
