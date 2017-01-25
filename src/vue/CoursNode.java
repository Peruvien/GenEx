/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Cours;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author robin
 */
public class CoursNode extends DefaultMutableTreeNode implements NodeInformations {
    
    //ATTRIBUTS
    private final Cours cours;
    private final String titre;
    
    //CONSTRUCTEUR
    public CoursNode(Cours cours, String titre) {
        super(titre);
        this.cours = cours;
        this.titre = titre;
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    
    
    //NODEINFORMATIONS
    @Override
    public String getInformations() {
        return cours.toString() + "\n";
    }
    
}
