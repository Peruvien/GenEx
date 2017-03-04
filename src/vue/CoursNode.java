/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Planche;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author robin
 */
public class CoursNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final Planche planche;
    
    //CONSTRUCTEUR
    public CoursNode(Planche planche, String titre) {
        super(planche);
        this.planche = planche;
    }
    
    
    //ACCESSEURS
    public Planche getPlanche() {
        return planche;
    }
    
    @Override
    public String toString() {
        return "Planche n°" + planche.getNumeroCours();
    }
    
    //MUTATEURS
    
    
    //NODEINFORMATIONS
    @Override
    public String getInformations() {
        return planche.toString() + "\n";
    }
    
}
