/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.icepdf.core.pobjects.fonts.FontFactory;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 *
 * @author robin
 */
public class LecteurPDFrame extends JFrame {
    
    //ATTRIBUTS
    SwingController controller;
    SwingViewBuilder factory;
    JPanel viewerComponentPanel;
    
    //CONSTRUCTEUR
    public LecteurPDFrame() {
        super("Affichage");
        
        controller = new SwingController();
        
        factory = new SwingViewBuilder(controller);
        viewerComponentPanel = factory.buildViewerPanel();
        // add copy keyboard command
        ComponentKeyBinding.install(controller, viewerComponentPanel);
        
        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
              new org.icepdf.ri.common.MyAnnotationCallback(
                     controller.getDocumentViewController()));
        setLocationRelativeTo(null);
        setSize(800, 800);
        add(viewerComponentPanel);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    public void openFichier(String nomFichier) {
        controller.openDocument(nomFichier);
    }
    
}
