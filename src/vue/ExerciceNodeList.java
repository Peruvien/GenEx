/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Exercice;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExerciceNodeList extends ExerciceNode {
    
    //ATTRIBUTS
    
    //CONSTRUCTEUR
    public ExerciceNodeList(Exercice exercice, String titre) {
        super(exercice,  titre);
    }
    
    public static ExerciceNodeList getExerciceNodeList(ExerciceNode node) {
        return new ExerciceNodeList(node.exercice, node.titre);
    }
    
    //ACCESSEURS
    @Override
    public String toString() {
        String res = "Chapitre " + exercice.getChapitre().getNumeroChapitre();
        res += exercice.getChapitre().isPresentiel() ? " présentiel " : " distant ";
        res += " Exercice n°" + exercice.getNumero();
        return res;
    }
    
    
    //MUTATEURS
}
