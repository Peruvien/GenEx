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
    private Set<TD> tds;

    //CONSTRUCTEUR
    public Chapitre(int idChapitre, int numeroChapitre, boolean presentiel, String libelle) {
        this.idChapitre = idChapitre;
        this.numeroChapitre = numeroChapitre;
        this.presentiel = presentiel;
        this.libelle = libelle;
        
        exercices = new TreeSet<>();
        tds = new TreeSet<>();
    }
    
    
    //ACCESSEURS
    public int getIdChapitre() {
        return idChapitre;
    }

    public int getNumeroChapitre() {
        return numeroChapitre;
    }

    public boolean isPresentiel() {
        return presentiel;
    }

    public String getLibelle() {
        return libelle;
    }

    public Set<Exercice> getExercices() {
        return exercices;
    }

    public Set<TD> getTds() {
        return tds;
    }
    
    //MUTATEURS
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

    public void setTds(Set<TD> tds) {
        this.tds = tds;
    }

    public void addTd(TD td){
        this.tds.add(td);
    }

    public void resetTd(){
        this.tds.clear();
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
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idChapitre;
        return hash;
    }
    
    
    @Override
    public int compareTo(Chapitre o) {
        return idChapitre - o.idChapitre;
    }
}
