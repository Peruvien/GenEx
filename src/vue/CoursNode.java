/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.Cours;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author robin
 */
public class CoursNode extends DefaultMutableTreeNode implements Informations {
    
    //ATTRIBUTS
    private final Cours cours;
    
    //CONSTRUCTEUR
    public CoursNode(Cours cours, String titre) {
        super(cours);
        this.cours = cours;
    }
    
    
    //ACCESSEURS
    public Cours getCours() {
        return cours;
    }
    
    @Override
    public String toString() {
        return "Cours n°" + cours.getNumeroCours();
    }
    
    //MUTATEURS
    
    
    //NODEINFORMATIONS
    @Override
    public String getInformations() {
        return cours.toString() + "\n";
    }
    
}
