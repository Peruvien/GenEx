/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author robin
 */
public class TransferableNode implements Transferable {
    
    //ATTRIBUTS
    public static DataFlavor nodeFlavor;
    private ExerciceNode node;
    
    
    //CONSTRUCTEURS
    public TransferableNode(ExerciceNode node) {
        super();
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + DefaultMutableTreeNode.class.getName();
        try {
            nodeFlavor = new DataFlavor(mimeType);
            this.node = node;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TransferableNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public TransferableNode(boolean isPresentiel, int id, int nbChapitre, int numero, String titre) {
        this(new ExerciceNode(isPresentiel, id, nbChapitre, numero, titre));
    }
    
    
    //ACCESSEURS
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { nodeFlavor, DataFlavor.stringFlavor };
    }
    
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(nodeFlavor) || flavor.equals(DataFlavor.stringFlavor);
    }
    
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor == null) {
            throw new IOException();
        }
        if (flavor.equals(nodeFlavor)) {
            return node;
        }
        return DataFlavor.stringFlavor;
        
    }
    
    
    //MUTATEURS
    
}
