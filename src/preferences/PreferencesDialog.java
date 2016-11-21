/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preferences;

import controleur.Controleur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
    public PreferencesDialog(JFrame parent, String title, boolean modal, Controleur controleur, Preferences prefs) {        
        super(parent,title,modal);
        this.preferences = prefs;
        
        initAll();
        
    }
    
    
    //ACCESSEURS
    public void showDialog() {
        setVisible(true);
    }
    
    
    //MUTATEURS
    private void initAll() {
        initButtons();
        initFileChooser();
    }
    private void initFileChooser() {
        fileBDD = new FileChooser("Base de données","",JFileChooser.FILES_ONLY,JFileChooser.OPEN_DIALOG);
    }
    private void initButtons() {
        BoutonsListener boutonListener = new BoutonsListener();
        
        ok = new JButton("OK");
        ok.addActionListener(boutonListener);
        annuler = new JButton("Annuler");
        annuler.addActionListener(boutonListener);
        appliquer = new JButton("Applquer");
        appliquer.addActionListener(boutonListener);
    }
    
    
    class BoutonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
