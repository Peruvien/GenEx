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
public class Node  extends DefaultMutableTreeNode{
    
    //ATTRIBUTS
    private final int id;
    private final boolean isPresentiel;
    private final boolean isChapitre;
    private final int numero;
    private final String titre;
    
    
    //CONSTRUCTEUR
    public Node(int id, boolean isPresentiel, boolean isChapitre, int numero, String titre) {
        this.id = id;
        this.isPresentiel = isPresentiel;
        this.isChapitre = isChapitre;
        this.numero = numero;
        this.titre = titre;
    }
    
    
    //ACCESSEURS
    public int getID() {
        return id;
    }
    
    public boolean isPresentiel() {
        return isPresentiel;
    }
    
    public boolean isChapitre() {
        return isChapitre;
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
