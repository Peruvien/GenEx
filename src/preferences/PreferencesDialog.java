/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import vue.FileChooser;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class PreferencesDialog extends JDialog {
    
    //ATTRIBUTS
    private final Preferences preferences;
    
    
    private FileChooser fileBDD;
    private JButton ok;
    private JButton appliquer;
    private JButton annuler;
    
    
    //CONSTRUCTEUR
    public PreferencesDialog(Preferences prefs) {
        this.preferences = prefs;
        
        
        
    }
    
    
    //ACCESSEURS
    
    
    
    //MUTATEURS
    private void initAll() {
        
    }
    private void initFileChooser() {
        fileBDD = new FileChooser("Base de données","",JFileChooser.FILES_ONLY,JFileChooser.OPEN_DIALOG);
    }
    private void initButtons() {
        //BoutonsListener boutonListener = new BoutonsListener();
        
        ok = new JButton("OK");
        //ok.addActionListener(boutonListener);
        annuler = new JButton("Annuler");
        //annuler.addActionListener(boutonListener);
        appliquer = new JButton("Applquer");
        //appliquer.addActionListener(boutonListener);
    }
}
