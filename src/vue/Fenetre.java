/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import preferences.Preferences;
import preferences.PreferencesDialog;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Fenetre extends JFrame {
    
    //
    private JPanel listeExosPanel;
    private JPanel listeExosListePanel;
    private JPanel recherchePanel;
    private JPanel infosExoPanel;
    private JPanel boutonsExoPanel;
    private JPanel listeExosBoutonsPanel;
    private JTextField infosExoField;
    private JTextField rechercheField;
    private JButton rechercheButton;
    private JButton rechercheAvanceeButton;
    private JButton addExoListeButton;
    private JButton removeExoListeButton;
    private JButton creerTDButton;
    private JButton creerExamButton;
    private JTabbedPane onglets;
    private JTree treeChapDistants;
    private JTree treeChapPresentiels;
    
    //MENU
    private JMenuBar menuBar;
    private JMenu fichier;
    private JMenuItem nouveau;
    private JMenuItem ouvrir;
    private JMenuItem ajouterChapitre;
    private JMenuItem supprimerChapitre;
    private JMenuItem ajouterExercice;
    private JMenuItem supprimerExercice;
    private JMenuItem quitter;
    private JMenu outils;
    private JMenuItem prefsMenuItem;
    
    //CLASSES PERSOS
    private final Controleur controleur;
    private final Preferences preferences;
    private final PreferencesDialog preferencesDialog;
    
    
    //CONSTRUCTEUR
    public Fenetre(Controleur controleur, Preferences preferences) {
        super("Logiciel de gestion d'exercices");
        setSize(new Dimension(900,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.controleur = controleur;
        this.preferences = preferences;
        preferencesDialog = new PreferencesDialog(null, "Préferences", true, this.controleur, this.preferences);
        
        initAll();
        setComponents();
        setMenus();
        
        setLayout(new GridLayout(0,2));
        
        setJMenuBar(menuBar);
        add(onglets);
        add(infosExoPanel);
        
        setVisible(true);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initPanels();
        initTabbedPane();
        initButtons();
        initTextFields();
        initTrees();
        initMenus();
    }
    private void initPanels() {
        listeExosPanel = new JPanel(new BorderLayout());
        listeExosListePanel = new JPanel(new ListeLayout());
        infosExoPanel = new JPanel(new BorderLayout());
        recherchePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonsExoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        listeExosBoutonsPanel = new JPanel(new FlowLayout());
    }
    private void initTabbedPane() {
        onglets = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    private void initButtons() {
        rechercheButton = new JButton("Rechercher");
        rechercheAvanceeButton = new JButton("Recherche avancée");
        addExoListeButton = new JButton("Ajouter à la liste");
        removeExoListeButton = new JButton("Retirer de la liste");
        creerTDButton = new JButton("Créer TD");
        creerExamButton = new JButton("Créer Examen");
    }
    private void initTextFields() {
        infosExoField = new JTextField("Informations sur l'exerice ou chapitre");
        rechercheField = new JTextField("Rechercher...");
        rechercheField.setColumns(15);
    }
    private void initTrees() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Chapitres");
        
        DefaultMutableTreeNode chapitre1 = new DefaultMutableTreeNode("Chapitre 1");
        chapitre1.add(new DefaultMutableTreeNode("Exercice 1"));
        DefaultMutableTreeNode chapitre2 = new DefaultMutableTreeNode("Chapitre 2");
        chapitre2.add(new DefaultMutableTreeNode("Exercice 1"));
        chapitre2.add(new DefaultMutableTreeNode("Exercice 2"));
        root.add(chapitre1);
        root.add(chapitre2);
        treeChapPresentiels = new JTree(root);
        treeChapDistants = new JTree();
    }
    private void initMenus() {
        MenuListener menuListener = new MenuListener();
        
        menuBar = new JMenuBar();
        fichier = new JMenu("Fichier");
        
        nouveau = new JMenuItem("Nouveau");
        nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
        nouveau.addActionListener(menuListener);
        
        ouvrir = new JMenuItem("Ouvrir");
        ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
        ouvrir.addActionListener(menuListener);
        
        ajouterChapitre = new JMenuItem("Ajouter chapitre");
        ajouterChapitre.addActionListener(menuListener);
        
        ajouterExercice = new JMenuItem("Ajouter exercice");
        ajouterExercice.addActionListener(menuListener);
        
        quitter = new JMenuItem("Quitter");
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK));
        quitter.addActionListener(menuListener);
        
        outils = new JMenu("Outils");
        prefsMenuItem = new JMenuItem("Préférences");
        prefsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_MASK));
        prefsMenuItem.addActionListener(menuListener);
    }
    
    private void setComponents() {
        onglets.addTab("Chapitres présentiels", treeChapPresentiels);
        onglets.addTab("Chapitre distants", treeChapDistants);
        onglets.addTab("TDs",new JPanel());
        onglets.addTab("Examens",new JPanel());
        onglets.addTab("Liste exercices",listeExosPanel);
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        infosExoPanel.add(infosExoField,BorderLayout.CENTER);
        infosExoPanel.add(recherchePanel,BorderLayout.NORTH);
        infosExoPanel.add(boutonsExoPanel,BorderLayout.SOUTH);
        
        boutonsExoPanel.add(addExoListeButton);
        
        listeExosPanel.add(listeExosListePanel);
        listeExosPanel.add(listeExosBoutonsPanel,BorderLayout.SOUTH);
        
        listeExosListePanel.add(new JCheckBox("Chapitre 3 Exercice 1"));
        listeExosListePanel.add(new JCheckBox("Chapitre 3 Exercice 2"));
        listeExosListePanel.add(new JCheckBox("Chapitre 3 Exercice 3"));
        
        listeExosBoutonsPanel.add(removeExoListeButton);
        listeExosBoutonsPanel.add(creerTDButton);
        listeExosBoutonsPanel.add(creerExamButton);
        
    }
    
    private void setMenus() {
        fichier.add(nouveau);
        fichier.add(ouvrir);
        fichier.add(ajouterChapitre);
        fichier.add(ajouterExercice);
        fichier.add(quitter);
        
        outils.add(prefsMenuItem);
        
        menuBar.add(fichier);
        menuBar.add(outils);
    }
    
    
    class MenuListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(nouveau)) {
                
            }
            if (src.equals(ouvrir)) {
                
            }
            if (src.equals(ajouterChapitre)) {
                
            }
            if (src.equals(ajouterExercice)) {
                
            }
            if (src.equals(prefsMenuItem)) {
                preferencesDialog.showDialog();
            }
            if (src.equals(quitter)) {
                controleur.deconnecter();
                System.exit(0);
            }
        }
        
    }
}
