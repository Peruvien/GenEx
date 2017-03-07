/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import modele.*;
import modele.Planche;
import controleur.Controleur;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import lu.tudor.santec.jtimechooser.JTimeChooser;
import observer.Observer;
import preferences.Preferences;
import preferences.PreferencesDialog;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Fenetre extends JFrame implements Observer {
    
    //ATTRIBUTS
    //INTERFACE PRINCIPALE
    //JFRAME
    private final LecteurPDFrame lecteurFrame;
    //PANELS
    private JPanel recherchePanel;
    private JPanel dropPanel;
    private JPanel infosExoPanel;
    private JPanel boutonsExoPanel;
    private RechercheResultatsPanel rechercheResPanel;
    private ChapitrePanels chapitrePanels;
    private ExercicePanels exercicePanels;
    //LISTS
    private DefaultListModel<ExamenList> examModelList;
    private JList<ExamenList> examList;
    private DefaultListModel<ExerciceNodeList> exosModelList;
    private JList<ExerciceNodeList> exosList;
    //TEXTPANE
    private JTextPane infosTextPane;
    //TEXTFIELD
    private JTextField rechercheField;
    //BUTTONS
    private JButton rechercheButton;
    private JButton rechercheAvanceeButton;
    private JButton creerCoursButton;
    private JButton creerExamButton;
    //TABBEDPANE
    private JTabbedPane ongletsTabbedPane;
    //SPLITPANES
    private JSplitPane splitPaneCentral;
    private JSplitPane splitPaneDroit;
    //ARBRES
    private JTree treeChapPresentiels;
    private JTree treeChapDistants;
    private DefaultMutableTreeNode rootPresentiels;
    private DefaultMutableTreeNode rootDistants;
    private Map<Integer, ChapitreNode> chapitresPresentiels;
    private Map<Integer, ChapitreNode> chapitresDistants;
    private DataFlavor nodeFlavor;
    //PREFS Database
    private DefaultListModel<String> bddModelList;
    private JList bddList;
    private String dossierBDD;
    
    //MENU
    private JMenuBar menuBar;
    private JMenu fichier;
    private JMenuItem nouveau;
    private JMenuItem ouvrirBDD;
    private JMenuItem ouvrirDossier;
    private JMenuItem ajouterChapitre;
    private JMenuItem modifierChapitre;
    private JMenuItem supprimerChapitre;
    private JMenuItem ajouterExercice;
    private JMenuItem modifierExercice;
    private JMenuItem supprimerExercice;
    private JMenuItem ajouterExamen;
    private JMenuItem modifierExamen;
    private JMenuItem supprimerExamen;
    private JMenuItem quitter;
    private JMenu outils;
    private JMenuItem prefsMenuItem;
    
    //POPUP MENU ARBRES
    private JPopupMenu popupMenuTree;
    private JMenuItem ajouterChapitrePopup;
    private JMenuItem modifierChapitrePopup;
    private JMenuItem supprimerChapitrePopup;
    private JMenuItem ajouterExercicePopup;
    private JMenuItem modifierExercicePopup;
    private JMenuItem supprimerExercicePopup;
    //POPUP MENU EXAMENS
    private JPopupMenu popupMenuExamens;
    private JMenuItem afficherExamen;
    
    //CLASSES PERSOS
    private final Controleur controleur;
    private final Preferences preferences;
    private PreferencesDialog preferencesDialog;
    private RechercheAvanceePanel rechercheAvanceePanel;
    
    
    //CONSTRUCTEUR
    public Fenetre(final Controleur controleur, Preferences preferences) {
        super("Logiciel de gestion d'exercices");
        try {
            this.nodeFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + DefaultMutableTreeNode.class.getName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
        setSize(new Dimension(950, 600));
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
        this.lecteurFrame = new LecteurPDFrame();
        
        initAll();
        setComponents();
        setMenus();

        setLayout(new BorderLayout());

        setJMenuBar(menuBar);
        add(splitPaneCentral);

        setVisible(true);
        ouvrirDossierBDD(JOptionPane.YES_NO_OPTION, preferences.getDossierBDD());
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    /**
     * Initialise tous les éléments graphiques.
     */
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
    /**
     * Initialise le dialogue des préférences.
     */
    private void initPreferences() {
        preferencesDialog = new PreferencesDialog(this, "Préferences", true, this.controleur, this.preferences);
    }
    /**
     * Initialise les panels.
     */
    private void initPanels() {
        dropPanel = new JPanel(new BorderLayout());
        infosExoPanel = new JPanel(new BorderLayout());
        recherchePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonsExoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rechercheResPanel = new RechercheResultatsPanel(new GridLayout(1, 2));
        rechercheAvanceePanel = new RechercheAvanceePanel();
        chapitrePanels = new ChapitrePanels();
        exercicePanels = new ExercicePanels(chapitrePanels);
    }
    /**
     * Initialise les listes et leurs modèles.
     */
    private void initLists() {
        InfoListListener listener = new InfoListListener();

        examModelList = new DefaultListModel<>();
        examList = new JList<>(examModelList);
        examList.addListSelectionListener(listener);

        exosModelList = new DefaultListModel<>();
        exosList = new JList<>(exosModelList);
        exosList.setDropTarget(new DropTarget(exosList, new DropListTarget()));
        exosList.addListSelectionListener(listener);
        
        
        bddModelList = new DefaultListModel<>();
        bddList = new JList<>(bddModelList);
    }
    /**
     * Initialise les splitPanes.
     */
    private void initSplitPane() {
        splitPaneCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneCentral.setContinuousLayout(true);
        splitPaneDroit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneDroit.setContinuousLayout(true);
        splitPaneDroit.setOneTouchExpandable(true);
    }
    /**
     * Initialise le tabbedPane.
     */
    private void initTabbedPane() {
        ongletsTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    }
    /**
     * Initialise les boutons.
     */
    private void initButtons() {
        RechercheListener listener = new RechercheListener();
        rechercheButton = new JButton("Rechercher");
        rechercheButton.addActionListener(listener);
        rechercheAvanceeButton = new JButton("Recherche avancée");
        rechercheAvanceeButton.addActionListener(listener);
        creerCoursButton = new JButton("Créer Planche");
        creerExamButton = new JButton("Créer Examen");
    }
    /**
     * Initialise les textPanes et textFields;
     */
    private void initTextFields() {
        infosTextPane = new JTextPane();
        infosTextPane.setEditable(false);
        rechercheField = new JTextField();
        rechercheField.setColumns(15);
    }
    /**
     * Initialise les arbres.
     */
    private void initTrees() {
        
        
        rootPresentiels = new DefaultMutableTreeNode("Chapitres");
        rootDistants = new DefaultMutableTreeNode("Chapitres");
        
        InfoTreeListener listener = new InfoTreeListener();
        
        treeChapPresentiels = new JTree(rootPresentiels);
        treeChapPresentiels.setDragEnabled(true);
        treeChapPresentiels.setTransferHandler(new TransferNodeHandler());
        treeChapPresentiels.addTreeSelectionListener(listener);
        
        treeChapDistants = new JTree(rootDistants);
        treeChapDistants.setDragEnabled(true);
        treeChapDistants.setTransferHandler(new TransferNodeHandler());
        treeChapDistants.addTreeSelectionListener(listener);
        
        chapitresPresentiels = new TreeMap<>();
        chapitresDistants = new TreeMap<>();
    }
    /**
     * Initialise tous les menus et menuItems.
     */
    private void initMenus() {
        //LISTENERS
        MenuListener menuListener = new MenuListener();
        AfficherListener afficherListener = new AfficherListener();
        
        //MENU
        menuBar = new JMenuBar();
        fichier = new JMenu("Fichier");

        nouveau = new JMenuItem("Nouveau");
        nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        nouveau.addActionListener(menuListener);

        ouvrirBDD = new JMenuItem("Ouvrir");
        ouvrirBDD.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        ouvrirBDD.addActionListener(menuListener);

        ouvrirDossier = new JMenuItem("Ouvrir dossier");
        ouvrirDossier.addActionListener(menuListener);

        ajouterChapitre = new JMenuItem("Ajouter chapitre");
        ajouterChapitre.addActionListener(menuListener);

        modifierChapitre = new JMenuItem("Modifier chapitre");
        modifierChapitre.addActionListener(menuListener);

        supprimerChapitre = new JMenuItem("Supprimer chapitre");
        supprimerChapitre.addActionListener(menuListener);

        ajouterExercice = new JMenuItem("Ajouter exercice");
        ajouterExercice.addActionListener(menuListener);

        modifierExercice = new JMenuItem("Modifier exercice");
        modifierExercice.addActionListener(menuListener);

        supprimerExercice = new JMenuItem("Supprimer exercice");
        supprimerExercice.addActionListener(menuListener);
        
        ajouterExamen = new JMenuItem("Ajouter examen");
        ajouterExamen.addActionListener(menuListener);
        
        modifierExamen = new JMenuItem("Modifier examen");
        modifierExamen.addActionListener(menuListener);
        
        supprimerExamen = new JMenuItem("Supprimer examen");
        supprimerExamen.addActionListener(menuListener);
        
        quitter = new JMenuItem("Quitter");
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        quitter.addActionListener(menuListener);

        outils = new JMenu("Outils");
        prefsMenuItem = new JMenuItem("Préférences");
        prefsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        prefsMenuItem.addActionListener(menuListener);

        //POPUP MENU ARBRES
        popupMenuTree = new JPopupMenu();
        
        ajouterChapitrePopup = new JMenuItem("Ajouter chapitre");
        ajouterChapitrePopup.addActionListener(menuListener);
        
        modifierChapitrePopup = new JMenuItem("Modifier chapitre");
        modifierChapitrePopup.addActionListener(menuListener);
        
        supprimerChapitrePopup = new JMenuItem("Supprimer chapitre");
        supprimerChapitrePopup.addActionListener(menuListener);
        
        ajouterExercicePopup = new JMenuItem("Ajouter exercice");
        ajouterExercicePopup.addActionListener(menuListener);
        
        modifierExercicePopup = new JMenuItem("Modifier exercice");
        modifierExercicePopup.addActionListener(menuListener);
        
        supprimerExercicePopup = new JMenuItem("Supprimer exercice");
        supprimerExercicePopup.addActionListener(menuListener);
        //POPUP MENU EXAMENS
        popupMenuExamens = new JPopupMenu();
        
        afficherExamen = new JMenuItem("Afficher");
        afficherExamen.addActionListener(afficherListener);
    }
    
    /**
     * Met les composants les uns dans les autres pour mettre en place
     * l'interface.
     */
    private void setComponents() {
        splitPaneCentral.add(ongletsTabbedPane);
        splitPaneCentral.add(infosExoPanel);
        splitPaneCentral.setDividerLocation(350);
        
        ongletsTabbedPane.addTab("Chapitres présentiels", treeChapPresentiels);
        ongletsTabbedPane.addTab("Chapitre distants", treeChapDistants);
        ongletsTabbedPane.addTab("Examens", examList);
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        boutonsExoPanel.add(creerCoursButton);
        boutonsExoPanel.add(creerExamButton);
        
        dropPanel.add(exosList, BorderLayout.CENTER);
        dropPanel.add(boutonsExoPanel, BorderLayout.SOUTH);
        
        splitPaneDroit.add(dropPanel);
        splitPaneDroit.add(new JScrollPane(infosTextPane));
        splitPaneDroit.setDividerLocation(250);
        
        infosExoPanel.add(recherchePanel, BorderLayout.NORTH);
        infosExoPanel.add(splitPaneDroit, BorderLayout.CENTER);
    }
    /**
     * Met les menuItems dans les menus correspondants.
     */
    private void setMenus() {
        fichier.add(nouveau);
        fichier.add(ouvrirBDD);
        fichier.add(ouvrirDossier);
        fichier.add(new JSeparator());
        fichier.add(ajouterChapitre);
        fichier.add(modifierChapitre);
        fichier.add(supprimerChapitre);
        fichier.add(new JSeparator());
        fichier.add(ajouterExercice);
        fichier.add(modifierExercice);
        fichier.add(supprimerExercice);
        fichier.add(new JSeparator());
        fichier.add(quitter);
        
        outils.add(prefsMenuItem);
        
        menuBar.add(fichier);
        menuBar.add(outils);
        
        popupMenuTree.add(ajouterChapitrePopup);
        popupMenuTree.add(modifierChapitrePopup);
        popupMenuTree.add(supprimerChapitrePopup);
        popupMenuTree.add(new JSeparator());
        popupMenuTree.add(ajouterExercicePopup);
        popupMenuTree.add(modifierExercicePopup);
        popupMenuTree.add(supprimerExercicePopup);
        
        popupMenuExamens.add(afficherExamen);
        
        treeChapPresentiels.setComponentPopupMenu(popupMenuTree);
        treeChapDistants.setComponentPopupMenu(popupMenuTree);
        
        examList.setComponentPopupMenu(popupMenuExamens);
        
    }
    
    /**
     * Remplit la liste de bases de données avec les fichiers compatibles dans le répertoire 'chemin'.
     * @param chemin Le chemin du répertoire dans lequel chercher les bases de données.
     */
    public void remplirBDDList(String chemin) {
        dossierBDD = chemin;
        File fileBDD = new File(dossierBDD);
        java.io.FilenameFilter fileFilter = new java.io.FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String lowerCase = name.toLowerCase();
                return lowerCase.endsWith(".sdb") || lowerCase.endsWith(".sqlite")
                    || lowerCase.endsWith(".db2") || lowerCase.endsWith(".s2db")
                    || lowerCase.endsWith(".sqlite2") || lowerCase.endsWith(".sl2")
                    || lowerCase.endsWith(".db3") || lowerCase.endsWith(".s3db")
                    || lowerCase.endsWith(".sqlite3") || lowerCase.endsWith(".sl3");
            }
        };
        File[] filesBDD = fileBDD.listFiles(fileFilter);
        bddModelList.clear();
        for (File file : filesBDD) {
            bddModelList.addElement(file.getName());
        }
    }
    
    /**
     * Demande l'ouverture d'une base de données dans une liste de fichiers dans le dossier dont le chemin complet est 'chemin'.
     * @param optionType Le type de dialogue, doit YES_NO_OPTION ou YES_NO_CANCEL_OPTION
     * @param chemin Le chemin du dossier dans lequels sont les bases de données
     */
    private void ouvrirDossierBDD(int optionType, String chemin) {
        int res;
        boolean res2 = false;
        Object[] options;
        if (optionType == JOptionPane.YES_NO_OPTION) {
            options = new Object[2];
            options[0] = "Ouvrir";
            options[1] = "Créer";
        } else {
            options = new Object[3];
            options[0] = "Ouvrir";
            options[1] = "Créer";
            options[2] = "Annuler";
        }
        remplirBDDList(chemin);
        do {
            res = JOptionPane.showOptionDialog(this, bddList, "Choisir une base de données", optionType, JOptionPane.PLAIN_MESSAGE, null, options, null);
            if (res == JOptionPane.YES_OPTION) {
                controleur.ouvrirBDD(dossierBDD + File.separator + bddList.getSelectedValue());
            }
            if (res == JOptionPane.NO_OPTION) {
                res2 = this.creerBDD(chemin);
            }
        } while ((res == JOptionPane.NO_OPTION || res == JOptionPane.CLOSED_OPTION) && !res2);
        
        controleur.actualiserAffichage();
    }
    
    /**
     * Ouvre un dialogue pour demander à l'utilisateur le chemin pour créer une nouvelle base de données.
     * Si le fichier existe : demande confirmation d'écrasement.
     *
     * @return Un entier qui représente l'option choisie, peut être
     * JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION, JOptionPane.NO_OPTION
     * ou JOptionPane.CLOSED_OPTION
     */
    private boolean creerBDD(String chemin) {
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Base de données (.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)", "sdb", "sqlite", "db2", "s2db", "sqlite2", "sl2", "db3", "s3db", "sqlite3", "sl3");
        FileChooser fileBDD = new FileChooser("Base de données", chemin, JFileChooser.FILES_ONLY, JFileChooser.SAVE_DIALOG);
        fileBDD.setFilter(fileFilter);
        boolean created = false;
        int res = JOptionPane.showConfirmDialog(this, fileBDD, "Créer la base de données", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            File file = new File(fileBDD.getPath());
            if (file.exists()) {
                int res2 = JOptionPane.showConfirmDialog(this, "Voulez-vous écraser le fichier ?", "Fichier déjà existant", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res2 == JOptionPane.YES_OPTION) {
                    file.delete();
                    created = controleur.creerBDD(fileBDD.getPath());
                }
            } else {
                if (!Connexion.isSQLite(fileBDD.getPath())) {
                    created = controleur.creerBDD(fileBDD.getPath() + ".sqlite3");
                }
                else {
                    created = controleur.creerBDD(fileBDD.getPath());
                }
            }
        }
        return created;
    }
    
    /**
     * Ajoute un ExerciceNodeList à exosModelList s'il n'est pas présent.
     * @param exercice l'ExerciceNodeList à ajouter
     */
    private void addExoNodeList(ExerciceNodeList exercice) {
        if (!exosModelList.contains(exercice)) {
            exosModelList.addElement(exercice);
        }
    }
    
    
    //METHODES GRAPHIQUES
    private boolean setSelectedPresentiel(JCheckBox presentielCheckBox) {
        boolean res;
        Component comp = ongletsTabbedPane.getSelectedComponent();
        if (comp.equals(treeChapPresentiels)) {
            presentielCheckBox.setSelected(false);
            presentielCheckBox.doClick(0);
            res = true;
        }
        else {
            presentielCheckBox.setSelected(true);
            presentielCheckBox.doClick(0);
            res = false;
        }
        return res;
    }
    
    private TreePath getSelectedPath() {
        TreePath res;
        Component comp = ongletsTabbedPane.getSelectedComponent();
        if (comp.equals(treeChapPresentiels)) {
            res = treeChapPresentiels.getSelectionPath();
        }
        else {
            res = treeChapDistants.getSelectionPath();
        }
        return res;
    }
    
    private void setSelectedChapitrePath(JComboBox numeroBox) {
        TreePath path = getSelectedPath();
        if (path != null && path.getPathCount() > 1 && numeroBox.getItemCount() > 0) {
            numeroBox.setSelectedItem(((ChapitreNode)path.getPathComponent(1)).getChapitre().getNumeroChapitre());
        }
        else {
            numeroBox.setSelectedIndex(0);
        }
    }
    
    private void setSelectedExercicePath(JComboBox numeroChapitreBox, JComboBox numeroBox) {
        setSelectedChapitrePath(numeroChapitreBox);
        TreePath path = getSelectedPath();
        Object lastPathComponent = path.getLastPathComponent();
        if (lastPathComponent instanceof ExerciceNode) {
            ExerciceNode exerciceNode = (ExerciceNode)lastPathComponent;
            numeroBox.setSelectedItem(exerciceNode.getExercice().getNumero());
        }
    }
    
    private void ajouterChapitre(Object src) {
        chapitrePanels.setAjout(true);
        JPanel inputs = exercicePanels.getChapitrePanel();
        chapitrePanels.setNumeroSpinner();
        
        JCheckBox presentielCheckBox = chapitrePanels.getPresentielCheckBox();
        JSpinner numeroSpinner = chapitrePanels.getNumeroSpinner();
        JTextField libelleField = chapitrePanels.getLibelleField();
        
        String[] options = {"Ajouter", "Annuler"};
        if (src.equals(ajouterChapitrePopup)) {
            setSelectedPresentiel(presentielCheckBox);
        }
        libelleField.setText("");
        int res = JOptionPane.showOptionDialog(this, inputs, "Ajouter chapitre", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (res == JOptionPane.YES_OPTION) {
            int modeChapitre = presentielCheckBox.isSelected() ? 1 : 2;
            controleur.ajouterChapitre(modeChapitre, (int)numeroSpinner.getValue(), libelleField.getText());
        }
    }
    
    private void modifierChapitre(Object src) {
        chapitrePanels.setAjout(false);
        JPanel inputs = exercicePanels.getChapitrePanel();
        chapitrePanels.setNumeroBox();
        exercicePanels.setFields();
        JCheckBox presentielCheckBox = chapitrePanels.getPresentielCheckBox();
        JComboBox numeroBox = chapitrePanels.getNumeroBox();
        JTextField libelleField = chapitrePanels.getLibelleField();
        
        String[] options = {"Modifier", "Annuler"};
        if (numeroBox.getItemCount() > 0) {
            if (src.equals(modifierChapitrePopup)) {
                setSelectedPresentiel(presentielCheckBox);
                setSelectedChapitrePath(numeroBox);
            }
            int res = JOptionPane.showOptionDialog(this, inputs, "Modifier chapitre", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (res == JOptionPane.YES_OPTION) {
                int modeChapitre = presentielCheckBox.isSelected() ? 1 : 2;
                controleur.modifierChapitre(modeChapitre, (int)numeroBox.getSelectedItem(), libelleField.getText());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Il n'y a pas de chapitre à modifier");
        }
    }
    
    private void supprimerChapitre(Object src) {
        chapitrePanels.setAjout(false);
        JPanel inputs = exercicePanels.getChapitrePanel();
        chapitrePanels.setNumeroBox();
        exercicePanels.setFields();
        
        JCheckBox presentielCheckBox = chapitrePanels.getPresentielCheckBox();
        JComboBox numeroBox = chapitrePanels.getNumeroBox();
        JTextField libelleField = chapitrePanels.getLibelleField();
        libelleField.setEditable(false);
        
        String[] options = {"Supprimer", "Annuler"};
        if (numeroBox.getItemCount() > 0) {
            if (src.equals(supprimerChapitrePopup)) {
                setSelectedPresentiel(presentielCheckBox);
                setSelectedChapitrePath(numeroBox);
            }
            
            int res = JOptionPane.showOptionDialog(this, inputs, "Modifier chapitre", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (res == JOptionPane.YES_OPTION) {
                int res2 = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr ?", "Demande de confirmation", JOptionPane.YES_NO_OPTION);
                if (res2 == JOptionPane.YES_OPTION) {
                    int modeChapitre = presentielCheckBox.isSelected() ? 1 : 2;
                    controleur.supprimerChapitre(modeChapitre,(int)numeroBox.getSelectedItem());
                }
            }
            libelleField.setEditable(true);
        } else {
            JOptionPane.showMessageDialog(this, "Il n'y a pas de chapitre à supprimer");
        }
    }
    
    
    private void ajouterExercice(Object src) {
        chapitrePanels.setAjout(false);
        exercicePanels.setAjout(true);
        JPanel chapitre = exercicePanels.getChapitrePanel();
        JPanel exercice = exercicePanels.getExercicePanel();
        exercicePanels.setChapitreNumeroBox();
        exercicePanels.setNumeroSpinner();
        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        inputs.add(chapitre,gbc);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        inputs.add(exercice,gbc);
        
        JCheckBox presentielCheckBox = chapitrePanels.getPresentielCheckBox();
        JComboBox numeroChapitreBox = chapitrePanels.getNumeroBox();
        JSpinner numeroSpinner = exercicePanels.getNumeroSpinner();
        JTimeChooser dureeChoser = exercicePanels.getDureeChooser();
        JSpinner pointsSpin = exercicePanels.getPointsSpinner();
        JTextField libelleField = exercicePanels.getLibelleField();
        FileChooser fichierChooser = exercicePanels.getFichierChooser();
        JTextField tagsField = exercicePanels.getTagsField();
        
        JTextField libelleChapitreField = chapitrePanels.getLibelleField();
        libelleChapitreField.setEditable(false);
        if (src.equals(ajouterExercicePopup)) {
            setSelectedPresentiel(presentielCheckBox);
            setSelectedChapitrePath(numeroChapitreBox);
        }
        
        exercicePanels.clearFields();
        String[] options = { "Ajouter", "Annuler" };
        
        int res = JOptionPane.showOptionDialog(this, inputs,"Ajouter exercice",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        if (res == JOptionPane.YES_OPTION) {
            int modeChapitre = presentielCheckBox.isSelected() ? 1 : 2;
            controleur.ajouterExercice(modeChapitre, (int)numeroChapitreBox.getSelectedItem(), (int)numeroSpinner.getValue(), dureeChoser.getFormatedTime(), (int)pointsSpin.getValue(), libelleField.getText(), fichierChooser.getPath(), tagsField.getText());
        }
        libelleChapitreField.setEditable(true);
    }
    
    private void modifierExercice(Object src) {
        chapitrePanels.setAjout(false);
        exercicePanels.setAjout(false);
        JPanel chapitre = exercicePanels.getChapitrePanel();
        JPanel exercice = exercicePanels.getExercicePanel();
        exercicePanels.setChapitreNumeroBox();
        exercicePanels.setNumeroBox();
        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        inputs.add(chapitre,gbc);
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.gridx = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        inputs.add(exercice,gbc);
        
        JCheckBox presentielCheckBox = chapitrePanels.getPresentielCheckBox();
        JComboBox numeroChapitreBox = chapitrePanels.getNumeroBox();
        JComboBox numeroBox = exercicePanels.getNumeroBox();
        JTimeChooser dureeChoser = exercicePanels.getDureeChooser();
        JSpinner pointsSpin = exercicePanels.getPointsSpinner();
        JTextField libelleField = exercicePanels.getLibelleField();
        FileChooser fichierChooser = exercicePanels.getFichierChooser();
        JTextField tagsField = exercicePanels.getTagsField();
        
        exercicePanels.setFields();
        if (numeroChapitreBox.getItemCount() > 0) {
            JTextField libelleChapitreField = chapitrePanels.getLibelleField();
            libelleChapitreField.setEditable(false);
            
            if (src.equals(modifierExercicePopup)) {
                setSelectedPresentiel(presentielCheckBox);
                setSelectedChapitrePath(numeroChapitreBox);
                TreePath path = getSelectedPath();
                Object lastPathComponent = path.getLastPathComponent();
                if (lastPathComponent instanceof ExerciceNode) {
                    ExerciceNode exerciceNode = (ExerciceNode)lastPathComponent;
                    numeroBox.setSelectedItem(exerciceNode.getExercice().getNumero());
                }
            }
            
            String[] options = { "Modifier", "Annuler" };
            int res = JOptionPane.showOptionDialog(this, inputs,"Modifier exercice",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if (res == JOptionPane.YES_OPTION) {
                int modeChapitre = presentielCheckBox.isSelected() ? 1 : 2;
                controleur.modifierExercice(modeChapitre, (int)numeroChapitreBox.getSelectedItem(), (int)numeroBox.getSelectedItem(), dureeChoser.getFormatedTime(), (int)pointsSpin.getValue(), libelleField.getText(), fichierChooser.getPath(), tagsField.getText());
            }
            libelleChapitreField.setEditable(true);
        }
        else {
            JOptionPane.showMessageDialog(this, "Il n'y a pas d'exercice à modifier");
        }
    }
    
    private void supprimerExercice(Object src) {
        chapitrePanels.setAjout(false);
        exercicePanels.setAjout(false);
        JPanel chapitrePan = exercicePanels.getChapitrePanel();
        JPanel exercicePan = new JPanel(new BorderLayout());
        exercicePan.setBorder(BorderFactory.createTitledBorder("Exercice"));
        exercicePanels.setChapitreNumeroBox();
        exercicePanels.setNumeroBox();
        exercicePanels.setNumeroBox();
        
        JPanel numeroPan = exercicePanels.getNumeroPanel();
        JPanel inputs = new JPanel(new BorderLayout());
        JCheckBox presentielCheckBox = chapitrePanels.getPresentielCheckBox();
        JComboBox numeroChapitreBox = chapitrePanels.getNumeroBox();
        JComboBox numeroBox = exercicePanels.getNumeroBox();
        
        inputs.add(chapitrePan,BorderLayout.CENTER);
        inputs.add(numeroPan,BorderLayout.SOUTH);
        
        exercicePanels.setFields();
        if (numeroChapitreBox.getItemCount() > 0) {
            JTextField libelleChapitreField = chapitrePanels.getLibelleField();
            libelleChapitreField.setEditable(false);
            
            if (src.equals(supprimerExercicePopup)) {
                setSelectedPresentiel(presentielCheckBox);
                setSelectedChapitrePath(numeroChapitreBox);
                TreePath path = getSelectedPath();
                Object lastPathComponent = path.getLastPathComponent();
                if (lastPathComponent instanceof ExerciceNode) {
                    ExerciceNode exerciceNode = (ExerciceNode)lastPathComponent;
                    numeroBox.setSelectedItem(exerciceNode.getExercice().getNumero());
                }
            }

            String[] options = { "Supprimer", "Annuler" };

            int res = JOptionPane.showOptionDialog(this, inputs,"Supprimer exercice",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if (res == JOptionPane.YES_OPTION) {
                int modeChapitre = presentielCheckBox.isSelected() ? 1 : 2;
                controleur.supprimerExercice(modeChapitre, (int)numeroChapitreBox.getSelectedItem(), (int)numeroBox.getSelectedItem());
            }
            libelleChapitreField.setEditable(true);
        }
        else {
            JOptionPane.showMessageDialog(this, "Il n'y a pas d'exercice à supprimer");
        }
    }
    
    //OBSERVER
    @Override
    public void clear() {
        chapitrePanels.clear();
        exercicePanels.clear();
        chapitresPresentiels.clear();
        rootPresentiels.removeAllChildren();
        ((DefaultTreeModel)treeChapPresentiels.getModel()).reload(rootPresentiels);
        treeChapPresentiels.removeAll();
        chapitresDistants.clear();
        rootDistants.removeAllChildren();
        ((DefaultTreeModel)treeChapDistants.getModel()).reload(rootDistants);
        treeChapDistants.removeAll();
        examModelList.removeAllElements();
        exosModelList.removeAllElements();
        this.repaint();
    }
    
    @Override
    public void addChapitre(Chapitre chapitre) {
        ChapitreNode chapitreAdd = new ChapitreNode(chapitre);
        //TODO GERER LES 3 CAS POSSIBLES
        boolean presentiel = chapitre.getModeChapitre() < 2;
        int idChapitre = chapitre.getIdChapitre();
        if (presentiel) {
            chapitresPresentiels.put(idChapitre, chapitreAdd);
            rootPresentiels.add(chapitreAdd);
            chapitrePanels.addItemPresentiel(chapitre.getNumeroChapitre(), chapitre.getLibelle());
        } else {
            chapitresDistants.put(idChapitre, chapitreAdd);
            rootDistants.add(chapitreAdd);
            chapitrePanels.addItemDistant(chapitre.getNumeroChapitre(), chapitre.getLibelle());
        }
        this.repaint();
    }
    
    @Override
    public void addExercice(boolean presentiel, int idChapitre, Exercice exercice) {
        ExerciceNode exerciceAdd = new ExerciceNode(exercice);
        //TODO Regarder si ce changement est interessant, gerer les 3 cas
        //boolean presentiel = (exercice.getChapitreExercice().getModeChapitre() < 2);
        //TODO Remplacer idChapitre par idCours
        //TODO EN FAIT NON
        //int idCours = exercice.getCoursExercice().getIDCours();
        //int idChapitre = exercice.getChapitreExercice().getIdChapitre();
        if (presentiel) {
            //chapitresPresentiels.get(idCours).add(exerciceAdd);
            chapitresPresentiels.get(idChapitre).add(exerciceAdd);
        } else {
            //chapitresDistants.get(idCours).add(exerciceAdd);
            chapitresDistants.get(idChapitre).add(exerciceAdd);
        }
        //TODO Remplacer getChapitre par getCoursExercice()
        exercicePanels.addExercice(new Pair(presentiel, idChapitre), exercice);
        //exercicePanels.addExercice(new Pair(exercice.getChapitre().isPresentiel(),exercice.getChapitre().getNumeroChapitre()), exercice);
    }
    
    @Override
    public void addPlanche(boolean presentiel, int idChapitre, Planche planche) {
        CoursNode coursAdd = new CoursNode(planche, "Planche " + planche.getNumeroCours());
        if (presentiel) {
            chapitresPresentiels.get(idChapitre).add(coursAdd);
        } else {
            chapitresDistants.get(idChapitre).add(coursAdd);
        }
        
    }
    
    @Override
    public void addExamen(Examen examen) {
        examModelList.addElement(new ExamenList(examen));
    }
    
    @Override
    public void addExerciceOfCours(boolean presentiel, int idChapitre, Planche planche, Exercice exercice) {
        ExerciceNode exerciceNode = new ExerciceNode(exercice);
        if (presentiel) {
            chapitresPresentiels.get(idChapitre).getLastLeaf().add(exerciceNode);
        }
        else {
            chapitresDistants.get(idChapitre).getLastLeaf().add(exerciceNode);
        }
    }
    
    @Override
    public void clearRecherche() {
        rechercheResPanel.clear();
    }
    
    @Override
    public void addExerciceRecherche(Exercice exercice) {
        ExerciceNodeList exoNodeList = new ExerciceNodeList(exercice);
        rechercheResPanel.addExerciceNode(exoNodeList);
    }
    
    
    //CLASSES INTERNES
    class DropListTarget extends DropTargetAdapter {
        
        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                ExerciceNodeList node = (ExerciceNodeList) dtde.getTransferable().getTransferData(nodeFlavor);
                addExoNodeList(node);
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
                creerBDD(preferences.getDossierBDD());
            }
            if (src.equals(ouvrirBDD)) {
                ouvrirDossierBDD(JOptionPane.YES_NO_CANCEL_OPTION, preferences.getDossierBDD());
            }
            if (src.equals(ouvrirDossier)) {
                FileChooser fileDossier = new FileChooser("Ouvrir dosiser", preferences.getDossierBDD(), JFileChooser.DIRECTORIES_ONLY, JFileChooser.OPEN_DIALOG);
                int res = JOptionPane.showConfirmDialog(null, fileDossier, "Ouvrir dossier de base de données", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    ouvrirDossierBDD(JOptionPane.YES_NO_CANCEL_OPTION, fileDossier.getPath());
                }
            }
            if (src.equals(ajouterChapitre) || src.equals(ajouterChapitrePopup)) {
                ajouterChapitre(src);
            }
            if (src.equals(modifierChapitre) || src.equals(modifierChapitrePopup)) {
                modifierChapitre(src);
            }
            if (src.equals(supprimerChapitre) || src.equals(supprimerChapitrePopup)) {
                supprimerChapitre(src);
            }
            if (src.equals(ajouterExercice) || src.equals(ajouterExercicePopup)) {
                ajouterExercice(src);
            }
            if (src.equals(modifierExercice) || src.equals(modifierExercicePopup)) {
                modifierExercice(src);
            }
            if (src.equals(supprimerExercice) || src.equals(supprimerExercicePopup)) {
                supprimerExercice(src);
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
            clearRecherche();
            int recherche = 1;
            if (src.equals(rechercheButton)) {
                if (!rechercheField.getText().isEmpty()) {
                    recherche = 0;
                    controleur.rechercherExercice(rechercheField.getText());
                }
            }
            if (src.equals(rechercheAvanceeButton)) {
                Object[] options = {"Rechercher", "Annuler"};
                int res = JOptionPane.showOptionDialog(rechercheAvanceePanel, rechercheAvanceePanel, "Recherche avancée", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                if (res == JOptionPane.YES_OPTION) {
                    String text = rechercheAvanceePanel.getText();
                    Date dateUtilDebut = rechercheAvanceePanel.getDateDebut();
                    Date dateUtilFin = rechercheAvanceePanel.getDateFin();
                    controleur.rechercherExercice(text, dateUtilDebut, dateUtilFin);
                }
                recherche = res;
            }
            if (recherche == 0) {
                String[] options = {"Ajouter la sélection", "Annuler"};
                int res = JOptionPane.showOptionDialog(null, rechercheResPanel, "Résultat de recherche", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (res == JOptionPane.YES_OPTION) {
                    for (ExerciceNodeList exercice : rechercheResPanel.getSelectedValuesList()) {
                        addExoNodeList(exercice);
                    }
                }
            }
            
        }
        
    }
    
    class InfoListListener implements ListSelectionListener {
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Object src = e.getSource();
            if (src.equals(exosList) && !e.getValueIsAdjusting()) {
                List<ExerciceNodeList> exosSelected = exosList.getSelectedValuesList();
                String infos = "";
                for (Informations exo : exosSelected) {
                    infos += exo.getInformations() + "\n";
                }
                infosTextPane.setText(infos);
            }
            if (src.equals(examList)) {
                List<ExamenList> exosSelected = examList.getSelectedValuesList();
                String infos = "";
                for (Informations exo : exosSelected) {
                    infos += exo.getInformations() + "\n";
                }
                infosTextPane.setText(infos);
            }
        }
        
    }

    class InfoTreeListener implements TreeSelectionListener {
        
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            Object src = e.getSource();
            TreePath[] paths;
            if (src.equals(treeChapPresentiels)) {
                paths = treeChapPresentiels.getSelectionPaths();
            } else {
                paths = treeChapDistants.getSelectionPaths();
            }
            if (paths != null) {
                String informations = "";
                for (TreePath path : paths) {
                    Object node = path.getLastPathComponent();
                    if (node instanceof Informations) {
                        informations += ((Informations) node).getInformations() + "\n";
                    }
                }
                infosTextPane.setText(informations);
            }
        }
        
    }
    
    class AfficherListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            lecteurFrame.openFichier(examList.getSelectedValue().getFichier());
            lecteurFrame.setVisible(true);
        }
        
    }
}
