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
 * @author robin
 * @author Vincent
 */
public class ExercicesDeTD {
     
    //ATTRIBUTS
    private final TD td;
    private final ArrayList<Exercice> exercices;
    private final Date dateUtilisation;
    
    
    //CONSTRUCTEUR
    public ExercicesDeTD(TD td, Date dateUtilisation) {
        this.td = td;
        this.dateUtilisation = dateUtilisation;
        exercices = new ArrayList();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
}
