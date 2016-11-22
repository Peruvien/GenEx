/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

/**
 *
 * @author robin
 * @author Vincent
 */
public class TD implements Comparable<TD> {
    
    //ATTRIBUTS
    private final int idTD;
    private Chapitre chapitre;
    private final int numeroTD;
    
    //CONSTRUCTEUR
    public TD(int idTD, int numeroTD) {
        this.idTD = idTD;
        this.numeroTD = numeroTD;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(TD o) {
        int res = chapitre.compareTo(o.chapitre);
        if (res == 0) {
            res = numeroTD - o.numeroTD;
        }
        return res;
    }
}
