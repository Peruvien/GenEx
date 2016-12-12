/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author robin
 */
public class CheckBoxComponent extends JPanel{
    
    //ATTRIBUTS
    private final JCheckBox checkBox;
    private final Component component;
    
    
    //CONSTRUCTEUR
    public CheckBoxComponent(String titre, Component component) {
        super(new BorderLayout());
        checkBox = new JCheckBox(titre);
        this.component = component;
        
        add(checkBox,BorderLayout.WEST);
        add(this.component,BorderLayout.CENTER);
    }
    
    //ACCESSEURS
    public boolean isSelected() {
        return checkBox.isSelected();
    }
    
    public Component getComponent() {
        return component;
    }
    
    //MUTATEURS
    public void setSelected(boolean b) {
        checkBox.setSelected(b);
    }
    
}
