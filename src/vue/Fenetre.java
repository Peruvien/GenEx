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
import java.awt.event.KeyEvent;
import javax.swing.JButton;
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

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Fenetre extends JFrame {
    
    //ATTRIBUTS
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
    private JMenuItem quitter;
    private JMenu outils;
    private JMenuItem prefsMenuItem;
    
    //
    private final Controleur controleur;
    private final Preferences preferences;
    
    
    //CONSTRUCTEUR
    public Fenetre(Controleur controleur, Preferences preferences) {
        super("Logiciel de gestion d'exercices");
        setSize(new Dimension(900,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.controleur = controleur;
        this.preferences = preferences;
        
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
        onglets = new JTabbedPane();
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
        root.add(chapitre1);
        root.add(new DefaultMutableTreeNode("Chapitre 2"));
        treeChapPresentiels = new JTree(root);
        treeChapDistants = new JTree();
    }
    private void initMenus() {
        menuBar = new JMenuBar();
        fichier = new JMenu("Fichier");
        nouveau = new JMenuItem("Nouveau");
        nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
        ouvrir = new JMenuItem("Ouvrir");
        ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
        quitter = new JMenuItem("Quitter");
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,KeyEvent.CTRL_MASK));
        outils = new JMenu("Outils");
        prefsMenuItem = new JMenuItem("Préférences");
        prefsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_MASK));
    }
    
    private void setComponents() {
        onglets.addTab("Chapitres présentiels", treeChapPresentiels);
        onglets.addTab("Chapitre distants", treeChapDistants);
        onglets.addTab("Liste exercices",listeExosPanel);
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        infosExoPanel.add(infosExoField,BorderLayout.CENTER);
        infosExoPanel.add(recherchePanel,BorderLayout.NORTH);
        infosExoPanel.add(boutonsExoPanel,BorderLayout.SOUTH);
        
        boutonsExoPanel.add(addExoListeButton);
        
        
        listeExosPanel.add(listeExosBoutonsPanel,BorderLayout.SOUTH);
        
        listeExosBoutonsPanel.add(removeExoListeButton);
        listeExosBoutonsPanel.add(creerTDButton);
        listeExosBoutonsPanel.add(creerExamButton);
        
    }
    
    private void setMenus() {
        fichier.add(nouveau);
        fichier.add(ouvrir);
        fichier.add(quitter);
        
        outils.add(prefsMenuItem);
        
        menuBar.add(fichier);
        menuBar.add(outils);
    }
    
}
