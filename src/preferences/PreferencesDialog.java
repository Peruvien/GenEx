/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preferences;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import vue.FileChooser;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class PreferencesDialog extends JDialog {
    
    //ATTRIBUTS
    private final Preferences preferences;
    private final Controleur controleur;
    
    
    private JPanel panelBoutons;
    private FileChooser fileBDD;
    private JButton ok;
    private JButton appliquer;
    private JButton annuler;
    private boolean stateChanged;
    
    
    //CONSTRUCTEUR
    public PreferencesDialog(JFrame parent, String title, boolean modal, Controleur controleur, Preferences preferences) {        
        super(parent, title, modal);
        setSize(600,125);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        this.controleur = controleur;
        this.preferences = preferences;
        stateChanged = false;
        
        initAll();
        setComponents();
        
        add(fileBDD,BorderLayout.CENTER);
        add(panelBoutons,BorderLayout.SOUTH);
    }
    
    
    //ACCESSEURS
    public void showDialog() {
        setVisible(true);
    }
    
    
    //MUTATEURS
    private void initAll() {
        initPanel();
        initButtons();
        initFileChooser();
    }
    private void initPanel() {
        panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    }
    private void initFileChooser() {
        fileBDD = new FileChooser("Dossier des bases de données","",JFileChooser.DIRECTORIES_ONLY,JFileChooser.OPEN_DIALOG);
        fileBDD.setText(preferences.getDossierBDD());
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
    
    private void setComponents() {
        panelBoutons.add(ok);
        if (controleur != null) {
            panelBoutons.add(annuler);
            panelBoutons.add(appliquer);
        }
    }
    
    
    class BoutonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();            
            if (src.equals(ok)) {
                stateChanged = true;
                appliquer();
                preferences.savePreferences();
                stateChanged = false;
                setVisible(false);
            }
            if (src.equals(annuler)) {
                preferences.load();
                
                stateChanged = false;
                setVisible(false);
            }
            if (src.equals(appliquer)) {
                stateChanged = true;
                appliquer();
            }
        }
        
        private void appliquer() {
            String pathBDD = fileBDD.getPath();
            boolean bddChanged = !pathBDD.equals(preferences.getDossierBDD());
            preferences.setDossierBDD(pathBDD);
            if (bddChanged) {
                if (controleur != null) {
                    controleur.ouvrirBDD(pathBDD);
                }
            }
        }
        
    }
}
