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
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
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
    private JPanel listeExosPanel;
    private JPanel listeExosListePanel;
    private JPanel recherchePanel;
    private JPanel dropPanel;
    private DefaultListModel modelList;
    private JList dropList;
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
    private JTabbedPane ongletsTabbedPane;
    private JSplitPane splitPaneCentral;
    private JSplitPane splitPaneDroit;
    
    private DefaultMutableTreeNode rootPresentiels;
    private DefaultMutableTreeNode rootDistants;
    
    private Map<Integer,DefaultMutableTreeNode> chapitresPresentiels;
    private Map<Integer,DefaultMutableTreeNode> chapitresDistants;
    
    private JTree treeChapPresentiels;
    private JTree treeChapDistants;
    
    private DataFlavor nodeFlavor;
    
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
    private final RechercheAvanceePanel rechercheAvanceeDialog;
    
    
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
        preferencesDialog = new PreferencesDialog(null, "Préferences", true, this.controleur, this.preferences);
        rechercheAvanceeDialog = new RechercheAvanceePanel();
        
        initAll();
        setComponents();
        setMenus();
        
        setLayout(new GridLayout(1,1));
        
        setJMenuBar(menuBar);
        add(splitPaneCentral);
        
        setVisible(true);
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initPanels();
        initList();
        initSplitPane();
        initTabbedPane();
        initButtons();
        initTextFields();
        initTrees();
        initMenus();
    }
    private void initPanels() {
        listeExosPanel = new JPanel(new BorderLayout());
        listeExosListePanel = new JPanel(new ListeLayout());
        
        dropPanel = new JPanel(new BorderLayout());
        
        infosExoPanel = new JPanel(new BorderLayout());
        recherchePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        boutonsExoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        listeExosBoutonsPanel = new JPanel(new FlowLayout());
    }
    private void initList() {
        modelList = new DefaultListModel();
        dropList = new JList(modelList);
        dropList.setDropMode(DropMode.INSERT);
        dropList.setDropTarget(new DropTarget(dropList,new DropListTarget()));
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
        rootPresentiels = new DefaultMutableTreeNode("Chapitres");
        rootDistants = new DefaultMutableTreeNode("Chapitres");
        
        ChapitreNode chapitre1 = new ChapitreNode(true,1,1,"Chapitre 1");
        chapitre1.add(new ExerciceNode(true,1,1,1,"Exercice 1"));
        chapitre1.add(new ExerciceNode(true,2,1,2,"Exercice 2"));
        
        ChapitreNode chapitre2 = new ChapitreNode(true,2,2,"Chapitre 2");
        chapitre2.add(new ExerciceNode(true,3,2,1,"Exercice 1"));
        chapitre2.add(new ExerciceNode(true,4,2,2,"Exercice 2"));
        
        rootPresentiels.add(chapitre1);
        rootPresentiels.add(chapitre2);
        treeChapPresentiels = new JTree(rootPresentiels);
        treeChapPresentiels.setDragEnabled(true);
        treeChapPresentiels.setTransferHandler(new MyTransferHandler());
        treeChapDistants = new JTree(rootDistants);
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
        
        ongletsTabbedPane.addTab("Chapitres présentiels", treeChapPresentiels);
        ongletsTabbedPane.addTab("Chapitre distants", treeChapDistants);
        ongletsTabbedPane.addTab("TDs",new JPanel());
        ongletsTabbedPane.addTab("Examens",new JPanel());
        ongletsTabbedPane.addTab("Liste exercices",listeExosPanel);
        
        recherchePanel.add(rechercheField);
        recherchePanel.add(rechercheButton);
        recherchePanel.add(rechercheAvanceeButton);
        
        splitPaneDroit.add(dropList);
        splitPaneDroit.add(infosExoField);
        splitPaneDroit.setDividerLocation(250);
        
        infosExoPanel.add(recherchePanel,BorderLayout.NORTH);
        infosExoPanel.add(splitPaneDroit,BorderLayout.CENTER);
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
    
    
    //OBSERVER
    public void addChapitre(boolean distant) {
        if (distant) {
            DefaultMutableTreeNode chapitreAdd = new DefaultMutableTreeNode();
            chapitresDistants.put(WIDTH, chapitreAdd);
            rootDistants.add(chapitreAdd);
        }
        else {
            DefaultMutableTreeNode chapitreAdd = new DefaultMutableTreeNode();
            chapitresPresentiels.put(WIDTH, chapitreAdd);
            rootPresentiels.add(chapitreAdd);
        }
    }
    
    public void addExercice(int chapitre, boolean distant) {
        if (distant) {
            DefaultMutableTreeNode exerciceAdd = new DefaultMutableTreeNode();
            exerciceAdd.setAllowsChildren(false);
            chapitresDistants.get(chapitre).add(exerciceAdd);
        }
        else {
            DefaultMutableTreeNode exerciceAdd = new DefaultMutableTreeNode();
            exerciceAdd.setAllowsChildren(false);
            chapitresPresentiels.get(chapitre).add(exerciceAdd);
        }
    }
    
    
    
    //CLASSES INTERNES
    class DropListTarget extends DropTargetAdapter {

        @Override
        public void drop(DropTargetDropEvent dtde) {
            
            try {
                ExerciceNode node = (ExerciceNode)dtde.getTransferable().getTransferData(nodeFlavor);
                if (!modelList.contains(node)) {
                    modelList.addElement(node);
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
            if (src.equals(nouveau) || src.equals(ouvrir)) {
                int dialogType;
                String message;
                if (src.equals(nouveau)) {
                    dialogType = JFileChooser.SAVE_DIALOG;
                    message = "Créer";
                }
                else {
                    dialogType = JFileChooser.OPEN_DIALOG;
                    message = "Ouvrir";
                }
                FileChooser fileBDD = new FileChooser("Base de données","",JFileChooser.FILES_ONLY,dialogType);
                FileNameExtensionFilter fileFilter;
                if (src.equals(nouveau)) {
                    fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
                }
                else {
                    fileFilter = new FileNameExtensionFilter("Base de données (.accdb,.mdb,.kexi,.db,.sdb,.sqlite,.db2,.s2db,.sqlite2.sl2,.db3,.s3db,.sqlite3,.sl3)","accdb","mdb","kexi","db","sdb","sqlite","db2","s2db","sqlite2","sl2","db3","s3db","sqlite3","sl3");
                }
                
                fileBDD.setFilter(fileFilter);
                int res = JOptionPane.showConfirmDialog(null,fileBDD,message + " la base de données",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    if (src.equals(nouveau)) {
                        controleur.creerBDD(fileBDD.getPath());
                    }
                    else {
                        controleur.ouvrirBDD(fileBDD.getPath());
                    }
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
                RechercheAvanceePanel panelInputs = new RechercheAvanceePanel();
                Object[] options = {"Rechercher","Annuler"};
                int res = JOptionPane.showOptionDialog(panelInputs,panelInputs,"Recherche avancée",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,null);
            }
        }
        
    }
}
