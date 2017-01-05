/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

/**
 *
 * @author Robin
 */
public class ExerciceNodeList extends ExerciceNode {
    
    //ATTRIBUTS
    
    //CONSTRUCTEUR
    public ExerciceNodeList(boolean presentiel, int id, int nbChapitre, int numero, String titre) {
        super(presentiel, id, nbChapitre, numero, titre);
    }
    
    public static ExerciceNodeList getExerciceNodeList(ExerciceNode node) {
        return new ExerciceNodeList(node.presentiel, node.id, node.nbChapitre, node.numero, node.titre);
    }
    
    //ACCESSEURS
    @Override
    public String toString() {
        String res = "Chapitre " + nbChapitre;
        res += presentiel ? " présentiel " : " distant ";
        res += " Exercice n°" + numero;
        return res;
    }
    
    
    //MUTATEURS
}
