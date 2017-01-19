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
    private final int numeroChapitre;
    private final boolean presentiel;
    private String libelle;
    private final Set<Exercice> exercices;
    
    
    //CONSTRUCTEUR
    public Chapitre(int idChapitre, int numeroChapitre, boolean presentiel, String libelle) {
        this.idChapitre = idChapitre;
        this.numeroChapitre = numeroChapitre;
        this.presentiel = presentiel;
        this.libelle = libelle;
        
        exercices = new TreeSet<>();
    }
    
    
    //ACCESSEURS
    public int getID() {
        return idChapitre;
    }
    
    public int getNumero() {
        return numeroChapitre;
    }
    
    public boolean isPresentiel() {
        return presentiel;
    }
    
    public Set<Exercice> getExercices() {
        return exercices;
    }
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
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
