/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.util.Set;

/**
 *
 * @author robin
 * @author Vincent
 */
public class Chapitre implements Comparable<Chapitre> {
    
    //ATTRIBUTS
    private int idChapitre;
    private int numeroChapitre;
    private boolean presentiel;
    private String libelle;
    private Set<Exercice> exercices;
    private Set<TD> tds;
    
    
    //CONSTRUCTEUR
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(Chapitre o) {
        int res = numeroChapitre - o.numeroChapitre;
        
        return res;
    }
}
