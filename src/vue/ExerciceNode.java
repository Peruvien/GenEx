/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ExerciceNode extends DefaultMutableTreeNode {
    
    //ATTRIBUTS
    protected final boolean presentiel;
    protected final int id;
    protected final int nbChapitre;
    protected final int numero;
    protected final String titre;
    
    //CONSTRUCTEUR
    public ExerciceNode(boolean presentiel, int id, int nbChapitre, int numero, String titre) {
        super(titre,false);
        this.presentiel = presentiel;
        this.id = id;
        this.nbChapitre = nbChapitre;
        this.numero = numero;
        this.titre = titre;
    }
    
    
    //ACCESSEURS
    public boolean isPresentiel() {
        return presentiel;
    }

    public int getId() {
        return id;
    }

    public int getNbChapitre() {
        return nbChapitre;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }
    
    @Override
    public String toString() {
        return titre;
    }
    
    public String getInformations() {
        String res = "Chapitre " + nbChapitre;
        res += presentiel ? " présentiel " : " distant ";
        res += " Exercice n°" + numero;
        return res;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof ExerciceNode) {
            ExerciceNode other = (ExerciceNode)o;
            return other.id == id;
        }
        return false;
    }
    
    //MUTATEURS
    
}
