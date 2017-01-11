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
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.TransferHandler;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class TransferNodeHandler extends TransferHandler {
    
    /**
    * Méthode permettant à l'objet de savoir si les données reçues
    * via un drop sont autorisées à être importées
    * @param info
    * @return boolean
    */
    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        if (info.getComponent() instanceof JList) {
            DataFlavor[] dfs = info.getDataFlavors();
            for (DataFlavor df : dfs) {
                if (df.equals(TransferableNode.nodeFlavor)) {
                    return true;
                }
                if (df.equals(DataFlavor.stringFlavor)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * C'est ici que l'insertion des données dans notre composant est réalisée
    * @param support
    * @return boolean
    */
    @Override
    public boolean importData(TransferHandler.TransferSupport support){
        if (!canImport(support)) {
            return false;
        }
        
        Transferable data = support.getTransferable();
        String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + ExerciceNode.class.getName();
        DataFlavor df = null;
        try {
            df = new DataFlavor(mimeType);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TransferNodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        JList list = (JList)support.getComponent();
        DefaultListModel model = (DefaultListModel)list.getModel();
        
        try {
            model.addElement(data.getTransferData(df));
        } catch (UnsupportedFlavorException | IOException ex) {
            Logger.getLogger(TransferNodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    /**
    * Cette méthode est invoquée à la fin de l'action DROP
    * Si des actions sont à faire ensuite, c'est ici qu'il faudra coder le comportement désiré
    * @param c
    * @param t
    * @param action
    */
    @Override
    protected void exportDone(JComponent c, Transferable t, int action) {
        
    }
    
    /**
    * Dans cette méthode, nous allons créer l'objet utilisé par le système de drag'n drop
    * afin de faire circuler les données entre les composants
    * @param c
    * @return
    */
    @Override
    protected Transferable createTransferable(JComponent c) {
        if (c instanceof JTree) {
            JTree tree = (JTree)c;
            if (tree.getSelectionPath().getLastPathComponent() instanceof ExerciceNode) {
                ExerciceNode node = (ExerciceNode)tree.getSelectionPath().getLastPathComponent();
                return new TransferableNode(node);
            }
        }
        return null;
    }
    
    /**
    * Cette méthode est utilisée afin de déterminer le comportement 
    * du composant vis-à-vis du drag'n drop : nous retrouverons
    * nos variables statiques COPY, MOVE, COPY_OR_MOVE, LINK ou NONE 
    * @param c
    * @return int
    */
    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }
    
}
