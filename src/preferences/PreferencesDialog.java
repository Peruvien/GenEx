/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preferences;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
    
    private JPanel panelBoutons;
    private FileChooser fileBDD;
    private JButton ok;
    private JButton appliquer;
    private JButton annuler;
    
    
    //CONSTRUCTEUR
    public PreferencesDialog(JFrame parent, String title, boolean modal, Controleur controleur, Preferences preferences) {        
        super(parent, title, modal);
        setSize(600,125);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        this.preferences = preferences;
        
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
    
    private void setComponents() {
        panelBoutons.add(ok);
        panelBoutons.add(annuler);
        panelBoutons.add(appliquer);
    }
    
    
    class BoutonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();            
            if (src.equals(ok)) {
                setVisible(false);
            }
            if (src.equals(annuler)) {
                setVisible(false);
            }
            if (src.equals(appliquer)) {
                
            }
        }
        
    }
}
