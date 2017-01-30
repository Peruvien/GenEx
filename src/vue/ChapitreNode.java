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
public class ChapitreNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final Chapitre chapitre;
    
    
    //CONSTRUCTEUR
    public ChapitreNode(Chapitre chapitre) {
        super(chapitre);
        this.chapitre = chapitre;
    }
    
    
    //ACCESSEURS
    public Chapitre getChapitre() {
        return chapitre;
    }
    
    @Override
    public String toString() {
        return "Chapitre n°" + chapitre.getNumeroChapitre();
    }
    
    
    //MUTATEURS
    
    
    //INFORMATIONS
    @Override
    public String getInformations() {
        return chapitre.toString() + "\n";
    }
    
}
