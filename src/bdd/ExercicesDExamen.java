/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.util.ArrayList;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExercicesDExamen {
    
    //ATTRIBUTS
    private final Examen examen;
    private final ArrayList<Exercice> exercices;
    
    //CONSTRUCTEUR
    public ExercicesDExamen(Examen examen) {
        this.examen = examen;
        exercices = new ArrayList<>();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void addExercice(Exercice exercice) {
        exercices.add(exercice);
    }
    
}
