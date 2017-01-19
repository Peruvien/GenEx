/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Chapitre;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class ChapitreNode extends DefaultMutableTreeNode implements NodeInformations {
    
    //ATTRIBUTS
    private final Chapitre chapitre;
    private final String titre;
    
    //CONSTRUCTEUR
    public ChapitreNode(Chapitre chapitre, String titre) {
        super(titre);
        this.chapitre = chapitre;
        this.titre = titre;
    }
    
    //ACCESSEURS
    public String getTitre() {
        return titre;
    }
    
    @Override
    public String toString() {
        return titre;
    }
    
    
    //MUTATEURS
    
    
    //NODEINFORMATIONS
    @Override
    public String getInformations() {
        return chapitre.toString();
    }
    
}
