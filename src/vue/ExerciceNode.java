/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Exercice;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExerciceNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    protected Exercice exercice;
    
    
    //CONSTRUCTEUR
    public ExerciceNode(Exercice exercice) {
        super(exercice,false);
        this.exercice =  exercice;
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        return "Exercice n°" + exercice.getNumero();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof ExerciceNode) {
            ExerciceNode other = (ExerciceNode)o;
            return this.exercice.equals(other.exercice);
        }
        return false;
    }
    
    
    //MUTATEURS
    
    
    //INFORMATIONS
    @Override
    public String getInformations() {
        return exercice.toString() + "\n";
    }
    
}
