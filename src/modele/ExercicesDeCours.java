/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExercicesDeCours {
     
    //ATTRIBUTS
    private final Planche planche;
    private final ArrayList<Exercice> exercices;
    private final Date dateUtilisation;
    
    
    //CONSTRUCTEUR
    public ExercicesDeCours(Planche planche, Date dateUtilisation) {
        this.planche = planche;
        this.dateUtilisation = dateUtilisation;
        exercices = new ArrayList<>();
    }
    
    //ACCESSEURS


    public Date getDateUtilisation() {
        return dateUtilisation;
    }

    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
}
