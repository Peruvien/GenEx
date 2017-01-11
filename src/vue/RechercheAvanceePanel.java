/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.GridLayout;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class RechercheAvanceePanel extends JPanel {
    
    //ATTRIBUTS
    private JTextField textField;
    private CheckBoxComponent texteCheckBox;
    private CheckBoxComponent dateDebutCheckBox;
    private CheckBoxComponent dateFinCheckBox;
    
    
    //CONSTRUCTEUR
    public RechercheAvanceePanel() {
        super(new GridLayout(3,0,5,5));
        initAll();
        
        add(texteCheckBox);
        add(dateDebutCheckBox);
        add(dateFinCheckBox);
    }
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initAll() {
        initCheckBoxes();
    }
    private void initCheckBoxes() {
        textField = new JTextField();
        texteCheckBox = new CheckBoxComponent("Texte",textField);
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");
        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(model,p),new DateLabelFormatter());
        dateDebutCheckBox = new CheckBoxComponent("Date début",datePicker);
        model = new UtilDateModel();
        p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");
        datePicker = new JDatePickerImpl(new JDatePanelImpl(model,p),new DateLabelFormatter());
        dateFinCheckBox = new CheckBoxComponent("Date fin",datePicker);
    }
    
    private void setComponenets() {
        
    }
    
}
