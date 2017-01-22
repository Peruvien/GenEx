/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExercicesDeCours {
     
    //ATTRIBUTS
    private final Cours td;
    private final ArrayList<Exercice> exercices;
    private final Date dateUtilisation;
    
    
    //CONSTRUCTEUR
    public ExercicesDeCours(Cours td, Date dateUtilisation) {
        this.td = td;
        this.dateUtilisation = dateUtilisation;
        exercices = new ArrayList<>();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
}
