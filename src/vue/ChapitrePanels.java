/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author robin
 */
public class ChapitrePanels extends JComponent {
    
    //ATTRIBUTS
    private JPanel presentielPanel;
    private JPanel numeroPanel;
    private JPanel libellePanel;
    
    private JCheckBox presentielCheckBox;
    private SpinnerNumberModel numeroModelSpinner;
    private JSpinner numeroSpinner;
    private JComboBox<Integer> numeroBox;
    private JTextField libelleField;
    
    private Set<Integer> presentiels;
    private Set<Integer> distants;
    private Map<Pair,String> libelles;
    
    
    //CONSTRUCTEUR
    public ChapitrePanels() {
        initAll();
        setComponents();
    }
    
    //ACCESSEURS
    public JPanel getPresentielPanel() {
        return presentielPanel;
    }
    
    public JCheckBox getPresentielCheckBox() {
        return presentielCheckBox;
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
    
    public JPanel getLibellePanel() {
        return libellePanel;
    }
    
    public JTextField getLibelleField() {
        return libelleField;
    }
    
    
    //MUTATEUS
    private void initAll() {
        initPanels();
        initSets();
        initMap();
        initCheckBox();
        initSpinner();
        initComboBox();
        initField();
    }
    private void initPanels() {
        presentielPanel = new JPanel(new BorderLayout());
        presentielPanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Présentiel"));
        numeroPanel = new JPanel(new BorderLayout());
        numeroPanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Numéro"));
        libellePanel = new JPanel(new BorderLayout());
        libellePanel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),"Libellé"));
    }
    
    private void initSets() {
        presentiels = new TreeSet<>();
        distants = new TreeSet<>();
    }
    private void initMap() {
        libelles = new TreeMap<>();
    }
    private void initCheckBox() {
        presentielCheckBox = new JCheckBox("présentiel");
        presentielCheckBox.addActionListener(new CheckBoxListener());
    }
    private void initSpinner() {
        numeroModelSpinner = new SpinnerNumberModel(1, 1, 999, 1);
        numeroSpinner = new JSpinner(numeroModelSpinner);
    }
    private void initComboBox() {
        numeroBox = new JComboBox<>();
        numeroBox.addActionListener(new ComboBoxListener());
    }
    private void initField() {
        libelleField = new JTextField();
    }
    
    private void setComponents() {
        presentielPanel.add(presentielCheckBox);
        libellePanel.add(libelleField);
    }
    
    public void setNumeroSpinner() {
        numeroPanel.remove(numeroBox);
        numeroPanel.add(numeroSpinner);
    }
    
    public void setNumeroBox() {
        numeroPanel.remove(numeroSpinner);
        numeroPanel.add(numeroBox);
    }
    
    private void setItemsPresentiels() {
        numeroBox.removeAllItems();
        for (Integer i : presentiels) {
            numeroBox.addItem(i);
        }
    }
    
    private void setItemsDistants() {
        numeroBox.removeAllItems();
        for (Integer i : distants) {
            numeroBox.addItem(i);
        }
    }
    
    public void clear() {
        presentiels.clear();
        distants.clear();
        libelles.clear();
        numeroBox.removeAllItems();
    }
    
    public void addItemPresentiel(int numero, String libelle) {
        presentiels.add(numero);
        if (presentielCheckBox.isSelected()) {
            numeroBox.addItem(numero);
            libelleField.setText(libelle);
        }
        libelles.put(new Pair(Boolean.TRUE,numero), libelle);
        ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(new ChangeEvent(numeroBox));
        }
    }
    
    public void addItemDistant(int numero, String libelle) {
        distants.add(numero);
        if (!presentielCheckBox.isSelected()) {
            numeroBox.addItem(numero);
            libelleField.setText(libelle);
        }
        libelles.put(new Pair(Boolean.FALSE,numero), libelle);
        ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(new ChangeEvent(numeroBox));
        }
    }
    
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }
    
    
    //CLASSES INTERNES
    class CheckBoxListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);
            
            if (src.equals(presentielCheckBox)) {
                if (presentielCheckBox.isSelected()) {
                    setItemsPresentiels();
                }
                else {
                    setItemsDistants();
                }
            }
            for (ChangeListener listener : listeners) {
                listener.stateChanged(new ChangeEvent(src));
            }
        }
        
    }
    
    class ComboBoxListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);
            
            if (src.equals(numeroBox)) {
                boolean presentiel = presentielCheckBox.isSelected();
                if (numeroBox.getItemCount() != 0) {
                    int numero = (int)numeroBox.getSelectedItem();
                    Pair pair = new Pair(presentiel, numero);
                    String libelle = libelles.get(pair);
                    libelleField.setText(libelle);
                }
            }
            for (ChangeListener listener : listeners) {
                listener.stateChanged(new ChangeEvent(src));
            }
        }
        
    }
    
}