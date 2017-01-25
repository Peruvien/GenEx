/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Chapitre;
import bdd.Cours;
import bdd.Examen;
import bdd.Exercice;
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
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
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
    //PANELS
    private JPanel recherchePanel;
    private JPanel dropPanel;
    private JPanel infosExoPanel;
    private JPanel boutonsExoPanel;
    private RechercheResultatsPanel rechercheResPanel;
    //LISTS
    private DefaultListModel<Cours> coursModelList;
    private JList <Cours>coursList;
    private DefaultListModel<Examen> examModelList;
    private JList <Examen>examList;
    private DefaultListModel<ExerciceNodeList> exosModelList;
    private JList <ExerciceNodeList>exosList;
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
    private JTree treeChapCoursPresentiels;
    private JTree treeChapCoursDistants;
    private DefaultMutableTreeNode rootPresentiels;
    private DefaultMutableTreeNode rootDistants;
    private DefaultMutableTreeNode rootCoursPresentiels;
    private Map<Integer,ChapitreNode> chapitresPresentiels;
    private Map<Integer,ChapitreNode> chapitresDistants;
    private Map<Integer,ChapitreNode> chapitresCoursPresentiels;
    private Map<Integer,ChapitreNode> chapitresCoursDistants;
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
    public Fenetre(final Controleur controleur, Preferences preferences) {
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
        
        setLayout(new BorderLayout());
        
        setJMenuBar(menuBar);
        add(splitPaneCentral);
        
        setVisible(true);
        ouvrirDossierBDD(JOptionPane.YES_NO_OPTION,preferences.getDossierBDD());
        
        addExamen(new Examen(1,true,new Date(175975),new Time(1000),"Test Examen","fichier.pdf"));
        
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
        rechercheResPanel = new RechercheResultatsPanel(new GridLayout(1,2));
        rechercheAvanceePanel = new RechercheAvanceePanel();
    }
    /**
     * Initialise les listes et leurs modèles.
     */
    private void initLists() {
        coursModelList = new DefaultListModel<>();
        coursList = new JList<>(coursModelList);
        
        examModelList = new DefaultListModel<>();
        examList = new JList<>(examModelList);
        
        exosModelList = new DefaultListModel<>();
        exosList = new JList<>(exosModelList);
        exosList.setDropTarget(new DropTarget(exosList,new DropListTarget()));
        exosList.addListSelectionListener(new InfoListListener());
        
        bddModelList = new DefaultListModel<>();
        bddList = new JList<>(bddModelList);
    }
    /**
     * Initialise les splitPanes.
     */
    private void initSplitPane() {
        splitPaneCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneDroit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
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
        creerCoursButton = new JButton("Créer Cours");
        creerExamButton = new JButton("Créer Examen");
    }
    /**
     * Initialise les textPanes et textFields;
     */
    private void initTextFields() {
        infosTextPane = new JTextPane();
        infosTextPane.setText("Informations sur l'exerice ou chapitre");
        rechercheField = new JTextField();
        rechercheField.setColumns(15);
    }
    /**
     * Initialise les arbres.
     */
    private void initTrees() {
        rootPresentiels = new DefaultMutableTreeNode("Chapitres");
        rootDistants = new DefaultMutableTreeNode("Chapitres");
        rootCoursPresentiels = new DefaultMutableTreeNode("Chapitres");
        
        InfoTreeListener listener = new InfoTreeListener();
        
        treeChapPresentiels = new JTree(rootPresentiels);
        treeChapPresentiels.setDragEnabled(true);
        treeChapPresentiels.setTransferHandler(new TransferNodeHandler());
        treeChapPresentiels.addTreeSelectionListener(listener);
        
        treeChapDistants = new JTree(rootDistants);
        treeChapDistants.setDragEnabled(true);
        treeChapDistants.setTransferHandler(new TransferNodeHandler());
        treeChapDistants.addTreeSelectionListener(listener);
        
        treeChapCoursPresentiels = new JTree(rootCoursPresentiels);
        treeChapCoursPresentiels.addTreeSelectionListener(listener);
        
        chapitresPresentiels = new TreeMap<>();
        chapitresDistants = new TreeMap<>();
        chapitresCoursPresentiels = new TreeMap<>();
    }
    /**
     * Initialise tous les menus et menuItems.
     */
    private void initMenus() {
        MenuListener menuListener = new MenuListener();
        
        menuBar = new JMenuBar();
        fichier = new JMenu("Fichier");
        
        nouveau = new JMenuItem("Nouveau");
        nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_MASK));
        nouveau.addActionListener(menuListener);
        
        ouvrirBDD = new JMenuItem("Ouvrir");
        ouvrirBDD.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
        ouvrirBDD.addActionListener(menuListener);
        
        ouvrirDossier = new JMenuItem("Ouvrir dossier");
        ouvrirDossier.addActionListener(menuListener);
        
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
    /**
     * Met les composants les uns dans les autres pour mettre en place l'interface.
     */
    private void setComponents() {
        splitPaneCentral.add(ongletsTabbedPane);
        splitPaneCentral.add(infosExoPanel);
        splitPaneCentral.setDividerLocation(400);
        
        ongletsTabbedPane.addTab("Chapitres présentiels", treeChapPresentiels);
        ongletsTabbedPane.addTab("Chapitre distants", treeChapDistants);
        ongletsTabbedPane.addTab("Cours présentiels",treeChapCoursPresentiels);
        ongletsTabbedPane.addTab("Cours", coursList);
        ongletsTabbedPane.addTab("Examens",examList);
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        boutonsExoPanel.add(creerCoursButton);
        boutonsExoPanel.add(creerExamButton);
        
        dropPanel.add(exosList,BorderLayout.CENTER);
        dropPanel.add(boutonsExoPanel,BorderLayout.SOUTH);
        
        splitPaneDroit.add(dropPanel);
        splitPaneDroit.add(infosTextPane);
        splitPaneDroit.setDividerLocation(250);
        
        infosExoPanel.add(recherchePanel,BorderLayout.NORTH);
        infosExoPanel.add(splitPaneDroit,BorderLayout.CENTER);
    }
    /**
     * Met les menuItems dans les menus correspondants.
     */
    private void setMenus() {
        fichier.add(nouveau);
        fichier.add(ouvrirBDD);
        fichier.add(ouvrirDossier);
        fichier.add(ajouterChapitre);
        fichier.add(ajouterExercice);
        fichier.add(quitter);
        
        outils.add(prefsMenuItem);
        
        menuBar.add(fichier);
        menuBar.add(outils);
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
        int res2 = JOptionPane.CANCEL_OPTION;
        Object[] options;
        if (optionType == JOptionPane.YES_NO_OPTION) {
            options = new Object[2];
            options[0] = "Ouvrir";
            options[1] = "Créer";
        }
        else {
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
        } while ((res == JOptionPane.NO_OPTION || res == JOptionPane.CLOSED_OPTION) && (res2 == JOptionPane.NO_OPTION || res2 == JOptionPane.CANCEL_OPTION || res2 == JOptionPane.CLOSED_OPTION));
    }
    
    /**
     * Ouvre un dialogue pour demander à l'utilisateur le chemin pour créer une nouvelle base de données. Si le fichier existe : demande confirmation d'écrasement.
     * @return Un entier qui représente l'option choisie, peut être JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION, JOptionPane.NO_OPTION ou JOptionPane.CLOSED_OPTION
     */
    private int creerBDD(String chemin) {
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
        FileChooser fileBDD = new FileChooser("Base de données",chemin,JFileChooser.FILES_ONLY,JFileChooser.SAVE_DIALOG);
        fileBDD.setFilter(fileFilter);
        
        int res = JOptionPane.showConfirmDialog(this,fileBDD,"Créer la base de données",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            File file = new File(fileBDD.getPath());
            if (file.exists()) {
                int res2 = JOptionPane.showConfirmDialog(this, "Voulez-vous écraser le fichier ?", "Fichier déjà existant", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res2 == JOptionPane.YES_OPTION) {
                    file.delete();
                    controleur.creerBDD(fileBDD.getPath());
                }
                else {
                    return res2;
                }
            }
            else {
                controleur.creerBDD(fileBDD.getPath());
            }
        }
        return res;
    }
    
    /**
     * Ajoute un ExerciceNodeList à exosModelList s'il est pas présent.
     * @param exercice l'ExerciceNodeList à ajouter
     */
    private void addExoNodeList(ExerciceNodeList exercice) {
        if (!exosModelList.contains(exercice)) {
            exosModelList.addElement(exercice);
        }
    }
    
    
    //OBSERVER
    @Override
    public void addChapitre(Chapitre chapitre) {
        ChapitreNode chapitreAdd = new ChapitreNode(chapitre, "Chapitre " + chapitre.getNumeroChapitre());
        boolean presentiel = chapitre.isPresentiel();
        int idChapitre = chapitre.getIdChapitre();
        if (presentiel) {
            chapitresPresentiels.put(idChapitre, chapitreAdd);
            rootPresentiels.add(chapitreAdd);
            ChapitreNode chapitreAddCours = new ChapitreNode(chapitre, "Chapitre " + chapitre.getNumeroChapitre());
            rootCoursPresentiels.add(chapitreAddCours);
        }
        else {
            chapitresDistants.put(idChapitre, chapitreAdd);
            rootDistants.add(chapitreAdd);
        }
        this.repaint();
    }
    
    @Override
    public void addExercice(Exercice exercice) {
        ExerciceNode exerciceAdd = new ExerciceNode(exercice, "Exercice " + exercice.getNumero());
        boolean presentiel = exercice.getChapitre().isPresentiel();
        int idChapitre = exercice.getChapitre().getIdChapitre();
        if (presentiel) {
            chapitresPresentiels.get(idChapitre).add(exerciceAdd);
        }
        else {
            chapitresDistants.get(idChapitre).add(exerciceAdd);
        }
    }
    
    @Override
    public void clearRecherche() {
        rechercheResPanel.clear();
    }
    
    @Override 
    public void addExerciceRecherche(Exercice exercice) {
        ExerciceNodeList exoNodeList = new ExerciceNodeList(exercice,"");
        rechercheResPanel.addExerciceNode(exoNodeList);
    }
    
    @Override
    public void addCours(Cours cours) {
        boolean presentiel = cours.getChapitre().isPresentiel();
        int idChapitre = cours.getChapitre().getIdChapitre();
        CoursNode coursAdd = new CoursNode(cours, "Cours " + cours.getNumeroCours());
        if (presentiel) {
            chapitresPresentiels.get(idChapitre).add(coursAdd);
        }
        else {
            chapitresDistants.get(idChapitre).add(coursAdd);
        }
        
    }

    @Override
    public void addExamen(Examen examen) {
        examModelList.addElement(examen);
    }
    
    //CLASSES INTERNES
    class DropListTarget extends DropTargetAdapter {
        
        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                ExerciceNodeList node = (ExerciceNodeList)dtde.getTransferable().getTransferData(nodeFlavor);
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
                ouvrirDossierBDD(JOptionPane.YES_NO_CANCEL_OPTION,preferences.getDossierBDD());
            }
            if (src.equals(ouvrirDossier)) {
                FileChooser fileDossier = new FileChooser("Ouvrir dosiser",preferences.getDossierBDD(),JFileChooser.DIRECTORIES_ONLY,JFileChooser.OPEN_DIALOG);
                int res = JOptionPane.showConfirmDialog(null, fileDossier, "Ouvrir dossier de base de données", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    ouvrirDossierBDD(JOptionPane.YES_NO_CANCEL_OPTION,fileDossier.getPath());
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
            clearRecherche();
            int recherche = 1;
            if (src.equals(rechercheButton)) {
                if (!rechercheField.getText().isEmpty()) {
                    recherche = 0;
                    controleur.rechercherExercice(rechercheField.getText());
                }
            }
            if (src.equals(rechercheAvanceeButton)) {
                Object[] options = {"Rechercher","Annuler"};
                int res = JOptionPane.showOptionDialog(rechercheAvanceePanel,rechercheAvanceePanel,"Recherche avancée",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,null);
                if (res == JOptionPane.YES_OPTION) {
                    String text = rechercheAvanceePanel.getText();
                    System.out.println(text);
                    java.util.Date dateUtilDebut = rechercheAvanceePanel.getDateDebut();
                    Date dateDebut = null;
                    if (dateUtilDebut != null ) {
                        dateDebut = new Date(dateUtilDebut.getTime());
                        System.out.println(dateDebut.toString());
                    }
                    java.util.Date dateUtilFin = rechercheAvanceePanel.getDateFin();
                    Date dateFin = null;
                    if (dateUtilFin != null ) {
                        dateFin = new Date(dateUtilFin.getTime());
                        System.out.println(dateFin.toString());
                    }
                    controleur.rechercherExercice(text, dateUtilDebut, dateUtilFin);
                }
                recherche = res;
            }
            if (recherche == 0) {
                String[] options = { "Ajouter la sélection", "Annuler" };
                int res = JOptionPane.showOptionDialog(null, rechercheResPanel,"Résultat de recherche", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
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
            if (!e.getValueIsAdjusting()) {
                List<ExerciceNodeList> exosSelected = exosList.getSelectedValuesList();
                String infos = "";
                for (NodeInformations exo : exosSelected) {
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
            }
            else {
                paths = treeChapDistants.getSelectionPaths();
            }
            if (paths != null) {
                String informations = "";
                for (TreePath path : paths) {
                    Object node = path.getLastPathComponent();
                    if (node instanceof NodeInformations) {
                        informations += ((NodeInformations)node).getInformations() + "\n";
                    }
                }
                infosTextPane.setText(informations);
            }
        }
        
    }
    
}
