/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Fenetre extends JFrame {
    
    //ATTRIBUTS
    private JPanel recherchePanel;
    private JPanel infosExoPanel;
    private JTextField infosExoField;
    private JTextField rechercheField;
    private JButton rechercheButton;
    private JButton rechercheAvanceeButton;
    private JTabbedPane matieres;
    private JTabbedPane onglets;
    private JTree treeChapDistants;
    private JTree treeChapPresentiels;
    
    
    //CONSTRUCTEUR
    public Fenetre() {
        super("Logiciel de gestion d'exercices");
        setSize(new Dimension(800,500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        initAll();
        setComponents();
        
        setLayout(new GridLayout(0,2));
        
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
    }
    private void initPanels() {
        infosExoPanel = new JPanel(new BorderLayout());
        recherchePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    }
    private void initTabbedPane() {
        matieres = new JTabbedPane();
        onglets = new JTabbedPane();
    }
    private void initButtons() {
        rechercheButton = new JButton("Rechercher");
        rechercheAvanceeButton = new JButton("Recherche avancée");
    }
    private void initTextFields() {
        infosExoField = new JTextField("Informations sur l'exerice ou chapitre");
        rechercheField = new JTextField("Rechercher...");
    }
    private void initTrees() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Chapitres");
        root.add(new DefaultMutableTreeNode("Chapitre 1"));
        root.add(new DefaultMutableTreeNode("Chapitre 2"));
        treeChapPresentiels = new JTree(root);
        treeChapDistants = new JTree();
    }
    
    private void setComponents() {
        onglets.addTab("Chapitres présentiels", treeChapPresentiels);
        onglets.addTab("Chapitre distants", treeChapDistants);
        onglets.addTab("Liste exercices",new JPanel());
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        infosExoPanel.add(infosExoField,BorderLayout.CENTER);
        infosExoPanel.add(recherchePanel,BorderLayout.NORTH);
        
        
        
    }
    
}
