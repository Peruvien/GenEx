/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.LayoutManager;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *
 * @author robin
 */
public class RechercheResultatsPanel extends JPanel {
    
    //ATTRIBUTS
    private JTextPane infosTextPane;
    private DefaultListModel<ExerciceNodeList> exosRechercheModelList;
    private JList <ExerciceNodeList>exosRechercheList;
    
    
    //CONSTRUCTEUR
    public RechercheResultatsPanel(LayoutManager layout) {
        super(layout);
        
        initAll();
        setComponents();
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initList();
        initTextPane();
    }
    private void initList() {
        exosRechercheModelList = new DefaultListModel<>();
        exosRechercheList = new JList<>(exosRechercheModelList);
    }
    private void initTextPane() {
        infosTextPane = new JTextPane();
        infosTextPane.setEditable(false);
    }
    
    private void setComponents() {
        add(exosRechercheList);
        add(infosTextPane);
    }
    
    
    public void clear() {
        exosRechercheModelList.clear();
    }
    
    public void addExerciceNode(ExerciceNodeList exerciceNode) {
        exosRechercheModelList.addElement(exerciceNode);
    }
    
    public List<ExerciceNodeList> getSelectedValuesList() {
        return exosRechercheList.getSelectedValuesList();
    }
    
}
