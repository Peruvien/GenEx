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
 * @author robin
 * @author Vincent
 */
public class Chapitre implements Comparable<Chapitre> {
    
    //ATTRIBUTS
    private final int idChapitre;
    private final int numeroChapitre;
    private final boolean presentiel;
    private final String libelle;
    private final Set<Exercice> exercices;
    private final Set<TD> tds;
    
    
    //CONSTRUCTEUR
    public Chapitre(int idChapitre, int numeroChapitre, boolean presentiel, String libelle) {
        this.idChapitre = idChapitre;
        this.numeroChapitre = numeroChapitre;
        this.presentiel = presentiel;
        this.libelle = libelle;
        
        exercices = new TreeSet();
        tds = new TreeSet();
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
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
