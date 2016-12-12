/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.GridLayout;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author robin
 */
public class RechercheAvanceePanel extends JPanel {
    
    //ATTRIBUTS
    private JTextField textField;
    private CheckBoxComponent texteCheckBox;
    private CheckBoxComponent datesCheckBox;
    //private JButton rechercherButton;
    
    
    //CONSTRUCTEUR
    public RechercheAvanceePanel() {
        super(new GridLayout(2,0));
        initAll();
        
        add(texteCheckBox);
        add(datesCheckBox);
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initCheckBoxes();
    }
    private void initCheckBoxes() {
        textField = new JTextField();
        texteCheckBox = new CheckBoxComponent("Titre",textField);
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");
        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(model,p),new DateLabelFormatter());
        datesCheckBox = new CheckBoxComponent("Date",datePicker);
    }
    
    private void setComponenets() {
        
    }
    
}
