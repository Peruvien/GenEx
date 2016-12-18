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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import preferences.Preferences;
import preferences.PreferencesDialog;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Fenetre extends JFrame {
    
    //ATTRIBUTS
    //INTERFACE PRINCIPALE
    private JPanel recherchePanel;
    private JPanel dropPanel;
    private JPanel infosExoPanel;
    private JPanel boutonsExoPanel;
    private DefaultListModel tdModelList;
    private JList tdList;
    private DefaultListModel examModelList;
    private JList examList;
    private DefaultListModel exosModelList;
    private JList exosList;
    private JTextPane infosTextPane;
    private JTextField rechercheField;
    private JButton rechercheButton;
    private JButton rechercheAvanceeButton;
    private JButton creerTDButton;
    private JButton creerExamButton;
    private JTabbedPane ongletsTabbedPane;
    private JSplitPane splitPaneCentral;
    private JSplitPane splitPaneDroit;
    //ARBRES
    private JTree treeChapPresentiels;
    private JTree treeChapDistants;
    private DefaultMutableTreeNode rootPresentiels;
    private DefaultMutableTreeNode rootDistants;
    private Map<Integer,ChapitreNode> chapitresPresentiels;
    private Map<Integer,ChapitreNode> chapitresDistants;
    private DataFlavor nodeFlavor;
    //PREFS BDD
    private DefaultListModel bddModelList;
    private JList bddList;
    
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
    private PreferencesDialog preferencesDialog;
    private RechercheAvanceePanel rechercheAvanceePanel;
    
    
    //CONSTRUCTEUR
    public Fenetre(Controleur controleur, Preferences preferences) {
        super("Logiciel de gestion d'exercices");
        try {
            this.nodeFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + DefaultMutableTreeNode.class.getName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
        setSize(new Dimension(950,600));
        setLocationRelativeTo(null);
        
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controleur.deconnecter();
                System.exit(0);
            }
        };
        
        addWindowListener(exitListener);
        
        this.controleur = controleur;
        this.preferences = preferences;
        
        initAll();
        setComponents();
        setMenus();
        
        setLayout(new GridLayout(1,1));
        
        setJMenuBar(menuBar);
        add(splitPaneCentral);
        
        setVisible(true);
        ouvrirDossierBDD();
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initPreferences();
        initPanels();
        initLists();
        initSplitPane();
        initTabbedPane();
        initButtons();
        initTextFields();
        initTrees();
        initMenus();
    }
    private void initPreferences() {
        preferencesDialog = new PreferencesDialog(null, "Préferences", true, this.controleur, this.preferences);
        rechercheAvanceePanel = new RechercheAvanceePanel();
    }
    private void initPanels() {
        dropPanel = new JPanel(new BorderLayout());
        infosExoPanel = new JPanel(new BorderLayout());
        recherchePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonsExoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }
    private void initLists() {
        tdModelList = new DefaultListModel();
        tdList = new JList(tdModelList);
        
        examModelList = new DefaultListModel();
        examList = new JList(examModelList);
        
        exosModelList = new DefaultListModel();
        exosList = new JList(exosModelList);
        exosList.setDropTarget(new DropTarget(exosList,new DropListTarget()));
        
        bddModelList = new DefaultListModel();
        bddList = new JList(bddModelList);
        File fileBDD = new File(preferences.getBDD());
        java.io.FilenameFilter fileFilter = new java.io.FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowerCase = name.toLowerCase();
                return lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb")
                    || lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
                    || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db2")
                    || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
                    || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".db3")
                    || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
                    || lowerCase.endsWith(".sl3");
            }
        };
        File[] filesBDD = fileBDD.listFiles(fileFilter);
        for (File file : filesBDD) {
            bddModelList.addElement(file.getName());
        }
    }
    private void initSplitPane() {
        splitPaneCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneDroit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    }
    private void initTabbedPane() {
        ongletsTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    private void initButtons() {
        rechercheButton = new JButton("Rechercher");
        rechercheAvanceeButton = new JButton("Recherche avancée");
        rechercheAvanceeButton.addActionListener(new RechercheListener());
        creerTDButton = new JButton("Créer TD");
        creerExamButton = new JButton("Créer Examen");
    }
    private void initTextFields() {
        infosTextPane = new JTextPane();
        infosTextPane.setText("Informations sur l'exerice ou chapitre");
        rechercheField = new JTextField("Rechercher...");
        rechercheField.setColumns(15);
    }
    private void initTrees() {
        rootPresentiels = new DefaultMutableTreeNode("Chapitres");
        rootDistants = new DefaultMutableTreeNode("Chapitres");
        
        ChapitreNode chapitre1 = new ChapitreNode(true,1,1,"Chapitre 1");
        chapitre1.add(new ExerciceNode(true,1,1,1,"Exercice 1"));
        chapitre1.add(new ExerciceNode(true,2,1,2,"Exercice 2"));
        
        ChapitreNode chapitre2 = new ChapitreNode(true,2,2,"Chapitre 2");
        chapitre2.add(new ExerciceNode(true,3,2,1,"Exercice 1"));
        chapitre2.add(new ExerciceNode(true,4,2,2,"Exercice 2"));
        
        ChapitreNode chapitre1Distant = new ChapitreNode(false,3,1,"Chapitre 1");
        chapitre1Distant.add(new ExerciceNode(false,5,1,1,"Exercice 1"));
        
        rootPresentiels.add(chapitre1);
        rootPresentiels.add(chapitre2);
        
        rootDistants.add(chapitre1Distant);
        
        treeChapPresentiels = new JTree(rootPresentiels);
        treeChapPresentiels.setDragEnabled(true);
        treeChapPresentiels.setTransferHandler(new TransferNodeHandler());
        
        treeChapDistants = new JTree(rootDistants);
        treeChapDistants.setDragEnabled(true);
        treeChapDistants.setTransferHandler(new TransferNodeHandler());
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
        splitPaneCentral.add(ongletsTabbedPane);
        splitPaneCentral.add(infosExoPanel);
        
        splitPaneCentral.setDividerLocation(400);
        
        ongletsTabbedPane.addTab("Chapitres présentiels", treeChapPresentiels);
        ongletsTabbedPane.addTab("Chapitre distants", treeChapDistants);
        ongletsTabbedPane.addTab("TDs",tdList);
        ongletsTabbedPane.addTab("Examens",examList);
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        boutonsExoPanel.add(creerTDButton);
        boutonsExoPanel.add(creerExamButton);
        
        dropPanel.add(exosList,BorderLayout.CENTER);
        dropPanel.add(boutonsExoPanel,BorderLayout.SOUTH);
        
        splitPaneDroit.add(dropPanel);
        splitPaneDroit.add(infosTextPane);
        splitPaneDroit.setDividerLocation(250);
        
        infosExoPanel.add(recherchePanel,BorderLayout.NORTH);
        infosExoPanel.add(splitPaneDroit,BorderLayout.CENTER);
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
    
    private void ouvrirDossierBDD() {
        Object[] options = { "Ouvrir", "Créer" };
        JPanel test = new JPanel(new BorderLayout());
        test.add(bddList);
        int res = JOptionPane.showOptionDialog(this, bddList, "Choisir une base de données", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
        if (res == JOptionPane.YES_OPTION) {
            System.out.println(bddList.getSelectedValue());
            controleur.ouvrirBDD(bddList.getSelectedValue().toString());
        }
        else {
            this.creerBDD();
        }
    }
    
    private void creerBDD() {
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
        FileChooser fileBDD = new FileChooser("Base de données","",JFileChooser.FILES_ONLY,JFileChooser.SAVE_DIALOG);
        fileBDD.setFilter(fileFilter);
        
        int res = JOptionPane.showConfirmDialog(null,fileBDD,"Créer la base de données",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            controleur.creerBDD(fileBDD.getPath());
        }
    }
    
    
    //OBSERVER
    public void addChapitre(boolean presentiel) {
        ChapitreNode chapitreAdd = new ChapitreNode(presentiel,1,1,"Chapitre 1");
        if (presentiel) {
            chapitresPresentiels.put(1, chapitreAdd);
            rootPresentiels.add(chapitreAdd);
        }
        else {
            chapitresDistants.put(1, chapitreAdd);
            rootDistants.add(chapitreAdd);
        }
    }
    
    public void addExercice( boolean presentiel, int chapitre) {
        ExerciceNode exerciceAdd = new ExerciceNode(presentiel,1,chapitre,1,"Exercice 1");
        if (presentiel) {
            chapitresDistants.get(chapitre).add(exerciceAdd);
        }
        else {
            chapitresPresentiels.get(chapitre).add(exerciceAdd);
        }
    }
    
    
    //CLASSES INTERNES
    class DropListTarget extends DropTargetAdapter {
        
        @Override
        public void drop(DropTargetDropEvent dtde) {
            
            try {
                ExerciceNode node = (ExerciceNode)dtde.getTransferable().getTransferData(nodeFlavor);
                if (!exosModelList.contains(node)) {
                    exosModelList.addElement(node);
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    
    class MenuListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(nouveau)) {
                creerBDD();
            }
            if (src.equals(ouvrir)) {
                FileChooser fileBDD = new FileChooser("Base de données",preferences.getBDD(),JFileChooser.FILES_ONLY,JFileChooser.OPEN_DIALOG);
                FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.kexi,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","kexi","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
                fileBDD.setFilter(fileFilter);
                
                int res = JOptionPane.showConfirmDialog(null,fileBDD,"Ouvrir la base de données",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    controleur.ouvrirBDD(fileBDD.getPath());
                }
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
    
    class RechercheListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src.equals(rechercheAvanceeButton)) {
                Object[] options = {"Rechercher","Annuler"};
                int res = JOptionPane.showOptionDialog(rechercheAvanceePanel,rechercheAvanceePanel,"Recherche avancée",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,null);
                if (res == JOptionPane.YES_OPTION) {
                    
                }
            }
        }
        
    }
}
