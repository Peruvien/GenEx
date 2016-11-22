/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.File;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Robin
 */
public class Examen implements Comparable<Examen> {
    
    //ATTRIBUTS
    private int idExamen;
    private Chapitre chapitre;
    private Date date;
    private Time heure;
    private Time duree;
    private String libelle;
    private File fichier;
    
    //CONSTRUCTEUR
    public Examen() {
        
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(Examen o) {
        int res = chapitre.compareTo(o.chapitre);
        if (res == 0) {
            res = idExamen - o.idExamen;
        }
        return res;
    }
}
