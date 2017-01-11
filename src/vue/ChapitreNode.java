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
public class ChapitreNode extends DefaultMutableTreeNode {
    
    //ATTRIBUTS
    private final boolean presentiel;
    private final int id;
    private final int numero;
    private final String titre;
    
    //CONSTRUCTEUR
    public ChapitreNode(boolean presentiel, int id, int numero, String titre) {
        super(titre);
        this.presentiel = presentiel;
        this.id = id;
        this.numero = numero;
        this.titre = titre;
    }
    
    //ACCESSEURS
    public boolean isPresentiel() {
        return presentiel;
    }

    public int getID() {
        return id;
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
    
    
    //MUTATEURS
    
}
