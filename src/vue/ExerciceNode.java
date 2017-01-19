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
public class ExerciceNode extends DefaultMutableTreeNode implements NodeInformations {
    
    //ATTRIBUTS
    protected Exercice exercice;
    protected final String titre;
    
    //CONSTRUCTEUR
    public ExerciceNode(Exercice exercice, String titre) {
        super(titre,false);
        this.exercice =  exercice;
        this.titre = titre;
    }
    
    
    //ACCESSEURS
    public boolean isPresentiel() {
        return exercice.getChapitre().isPresentiel();
    }

    public int getId() {
        return exercice.getID();
    }

    public int getNbChapitre() {
        return exercice.getChapitre().getNumeroChapitre();
    }

    public int getNumero() {
        return exercice.getNumero();
    }

    public String getTitre() {
        return titre;
    }
    
    @Override
    public String toString() {
        return titre;
    }
    
    @Override
    public String getInformations() {
        return exercice.toString();
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
    
}
