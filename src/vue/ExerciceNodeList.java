/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Exercice;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExerciceNodeList extends ExerciceNode {
    
    //ATTRIBUTS
    
    //CONSTRUCTEUR
    public ExerciceNodeList(Exercice exercice) {
        super(exercice);
    }
    
    public static ExerciceNodeList getExerciceNodeList(ExerciceNode node) {
        return new ExerciceNodeList(node.exercice);
    }
    
    //ACCESSEURS
    @Override
    public String toString() {
        String res = "Chapitre n°" + exercice.getChapitreExercice().getNumeroChapitre();
        //String res = "Planche n°" + exercice.getCoursExercice().getNumeroCours();
        res += exercice.getChapitreExercice().getModeChapitre() < 2 ? " présentiel " : " distant ";
        //res += exercice.getCoursExercice().getModeCours() < 2 ? " présentiel " : " distant ";

        res += " Exercice n°" + exercice.getNumero();
        return res;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof ExerciceNodeList) {
            return ((ExerciceNodeList)o).exercice.equals(this.exercice);
        }
        return false;
    }
    
    //MUTATEURS
}
