/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author robin
 */
public class ChapitreNode extends DefaultMutableTreeNode {
    
    //ATTRIBUTS
    private final boolean presentiel;
    private final String titre;
    
    //CONSTRUCTEUR
    public ChapitreNode(boolean presentiel, String titre) {
        this.presentiel = presentiel;
        this.titre = titre;
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
}
