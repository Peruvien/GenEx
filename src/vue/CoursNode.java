/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.PlancheTd;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author robin
 */
public class CoursNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final PlancheTd plancheTd;
    
    //CONSTRUCTEUR
    public CoursNode(PlancheTd plancheTd, String titre) {
        super(plancheTd);
        this.plancheTd = plancheTd;
    }
    
    
    //ACCESSEURS
    public PlancheTd getPlancheTd() {
        return plancheTd;
    }
    
    @Override
    public String toString() {
        return "PlancheTd n°" + plancheTd.getNumeroCours();
    }
    
    //MUTATEURS
    
    
    //NODEINFORMATIONS
    @Override
    public String getInformations() {
        return plancheTd.toString() + "\n";
    }
    
}
