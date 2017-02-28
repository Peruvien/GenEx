/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Exercice;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import lu.tudor.santec.jtimechooser.JTimeChooser;

/**
 *
 * @author robin
 */
public class ExercicePanels {
    
    //ATTRIBUTS
    //Chapitre
    private final ChapitrePanels chapitrePanels;
    private JPanel chapitrePanel;
    //Exercice
    private JPanel exercicePanel;
    private JPanel numeroPanel;
    private JPanel dureePanel;
    private JPanel pointsPanel;
    private JPanel libellePanel;
    private JPanel fichierPanel;
    private JPanel tagsPanel;
    
    private SpinnerNumberModel numeroModelSpinner;
    private JSpinner numeroSpinner;
    private JComboBox<Integer> numeroBox;
    private JTimeChooser dureeChooser;
    private SpinnerNumberModel pointsModelSpinner;
    private JSpinner pointsSpinner;
    private JTextField libelleField;
    private FileChooser fichierChooser;
    private JTextField tagsField;
    
    private boolean ajout;
    
    private Map<Pair,Set<Exercice>> exercices;
    
    
    //CONSTRUCTEUR
    public ExercicePanels(ChapitrePanels chapitrePanels) {
        this.chapitrePanels = chapitrePanels;
        this.chapitrePanels.addChangeListener(new ChapitreListener());
        initAll();
        setComponents();
        ajout = true;
    }
    
    
    //ACCESSEURS
    public JPanel getChapitrePanel() {
        return chapitrePanel;
    }
    
    public JPanel getExercicePanel() {
        return exercicePanel;
    }
    
    public JPanel getNumeroPanel() {
        return numeroPanel;
    }
    
    public JSpinner getNumeroSpinner() {
        return numeroSpinner;
    }
    
    public JComboBox getNumeroBox() {
        return numeroBox;
    }
    
    public JPanel getDureePanel() {
        return dureePanel;
    }
    
    public JTimeChooser getDureeChooser() {
        return dureeChooser;
    }
    
    public JPanel getPointsPanel() {
        return pointsPanel;
    }
    
    public JSpinner getPointsSpinner() {
        return pointsSpinner;
    }
    
    public JPanel getLibellePanel() {
        return libellePanel;
    }
    
    public JTextField getLibelleField() {
        return libelleField;
    }
    
    public JPanel getFichierPanel() {
        return fichierPanel;
    }
    
    public FileChooser getFichierChooser() {
        return fichierChooser;
    }
    
    public JPanel getTagsPanel() {
        return tagsPanel;
    }
    
    public JTextField getTagsField() {
        return tagsField;
    }
    
    //MUTATEURS
    private void initAll() {
        initPanels();
        initFields();
        initSpinners();
        initMap();
        initComboBox();
        initTimeChooser();
        initFileChooser();
    }
    private void initPanels() {
        chapitrePanel = new JPanel(new GridLayout(2,0));
        chapitrePanel.setBorder(BorderFactory.createTitledBorder("Chapitre"));
        exercicePanel = new JPanel(new GridLayout(5,0));
        exercicePanel.setBorder(BorderFactory.createTitledBorder("Exercice"));
        numeroPanel = new JPanel(new BorderLayout());
        numeroPanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Numéro"));
        dureePanel = new JPanel(new BorderLayout());
        dureePanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Durée"));
        pointsPanel = new JPanel(new BorderLayout());
        pointsPanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Points"));
        libellePanel = new JPanel(new BorderLayout());
        libellePanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Libellé"));
        fichierPanel = new JPanel(new BorderLayout());
        tagsPanel = new JPanel(new BorderLayout());
        tagsPanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Tags"));
    }
    private void initFields() {
        libelleField = new JTextField();
        tagsField = new JTextField();
    }
    private void initSpinners() {
        numeroModelSpinner = new SpinnerNumberModel(1, 1, 999, 1);
        numeroSpinner = new JSpinner(numeroModelSpinner);
        pointsModelSpinner = new SpinnerNumberModel(1,1,99,1);
        pointsSpinner = new JSpinner(pointsModelSpinner);
    }
    private void initMap() {
        exercices = new TreeMap<>();
    }
    private void initComboBox() {
        numeroBox = new JComboBox<>();
        numeroBox.addActionListener(new ExerciceBoxListener());
    }
    private void initTimeChooser() {
        dureeChooser = new JTimeChooser();
    }
    private void initFileChooser() {
        fichierChooser = new FileChooser("Fichier d'exercice","",JFileChooser.FILES_ONLY,JFileChooser.SAVE_DIALOG);
    }
    
    private void setComponents() {
        chapitrePanel.add(chapitrePanels.getPresentielPanel());
        chapitrePanel.add(chapitrePanels.getNumeroPanel());
        dureePanel.add(dureeChooser);
        pointsPanel.add(pointsSpinner);
        libellePanel.add(libelleField);
        fichierPanel.add(fichierChooser);
        tagsPanel.add(tagsField);
        exercicePanel.add(numeroPanel);
        exercicePanel.add(dureePanel);
        exercicePanel.add(libellePanel);
        exercicePanel.add(fichierPanel);
        exercicePanel.add(tagsPanel);
    }
    
    public void setChapitreNumeroBox() {
        chapitrePanels.setNumeroBox();
    }
    
    public void setNumeroSpinner() {
        numeroPanel.remove(numeroBox);
        numeroPanel.add(numeroSpinner);
    }
    
    public void setNumeroBox() {
        numeroPanel.remove(numeroSpinner);
        numeroPanel.add(numeroBox);
    }
    
    public void addExercice(Pair chapitre, Exercice exercice) {
        Set<Exercice> exos = exercices.get(chapitre);
        if (exos == null) {
            exos = new TreeSet<>();
            exercices.put(chapitre, exos);
        }
        exos.add(exercice);
    }
    
    public void clearFields() {
        if (numeroBox.getItemCount() > 0) {
            numeroBox.setSelectedIndex(0);
        }
        dureeChooser.setTime(new Time(10));
        pointsSpinner.setValue(0);
        libelleField.setText("");
        tagsField.setText("");
    }
    
    public void setAjout(boolean ajout) {
        this.ajout = ajout;
    }
    
    public void setFields() {
        if (chapitrePanels.getNumeroBox().getItemCount() > 0) {
            chapitrePanels.getNumeroBox().setSelectedIndex(0);
        }
        if (numeroBox.getItemCount() > 0) {
            numeroBox.setSelectedIndex(0);
        }
    }
    
    private void setFields(Exercice exercice) {
        numeroBox.setSelectedItem(exercice.getNumero());
        dureeChooser.setTime(exercice.getDuree());
        pointsSpinner.setValue(exercice.getPoints());
        libelleField.setText(exercice.getLibelle());
        tagsField.setText(exercice.getTags());
    }
    
    private Pair getPairChapitre() {
        Pair res = null;
        boolean presentiel = chapitrePanels.getPresentielCheckBox().isSelected();
        Object numeroChapitreObj = chapitrePanels.getNumeroBox().getSelectedItem();
        if (numeroChapitreObj != null) {
            int numeroChapitre = (int)numeroChapitreObj;
            res = new Pair(presentiel,numeroChapitre);
        }
        return res;
    }
    
    //CLASSES INTERNES
    class ChapitreListener implements ChangeListener {
        
        @Override
        public void stateChanged(ChangeEvent e) {
            if (!ajout) {
                boolean presentiel = chapitrePanels.getPresentielCheckBox().isSelected();
                Object numeroChapitreObj = chapitrePanels.getNumeroBox().getSelectedItem();
                if (numeroChapitreObj != null) {
                    int numeroChapitre = (int)numeroChapitreObj;
                    Set<Exercice> chapitre = exercices.get(new Pair(presentiel,numeroChapitre));
                    if (chapitre != null) {
                        numeroBox.removeAllItems();
                        for (Exercice exo : chapitre) {
                            numeroBox.addItem(exo.getNumero());
                        }
                        numeroBox.setSelectedIndex(0);
                    }
                    else {
                        numeroBox.removeAllItems();
                        clearFields();
                    }
                }
                else {
                    numeroBox.removeAllItems();
                    clearFields();
                }
            }
        }
        
    }
    
    class ExerciceBoxListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!ajout) {
                Object src = e.getSource();
                if (src.equals(numeroBox)) {
                    Object exerciceObj = numeroBox.getSelectedItem();
                    if (exerciceObj != null) {
                        int numeroExercice = (int)exerciceObj;
                        Pair chapitrePair = getPairChapitre();
                        Set<Exercice> chapitre = exercices.get(chapitrePair);
                        for (Exercice exercice : chapitre) {
                            if (exercice.getNumero() == numeroExercice) {
                                setFields(exercice);
                            }
                        }
                    }
                }
            }
        }
        
    }
    
}