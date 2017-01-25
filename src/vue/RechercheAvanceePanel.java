/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.GridLayout;
import java.util.Date;
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
    private CheckBoxComponent textCheckBox;
    private CheckBoxComponent dateDebutCheckBox;
    private CheckBoxComponent dateFinCheckBox;
    
    
    //CONSTRUCTEUR
    public RechercheAvanceePanel() {
        super(new GridLayout(3,0,5,5));
        
        initAll();
        setComponents();
    }
    
    //ACCESSEURS
    public String getText() {
        if (textCheckBox.isSelected()) {
            return textField.getText();
        }
        return "";
    }
    
    public Date getDateDebut() {
        if (dateDebutCheckBox.isSelected()) {
            JDatePickerImpl datePicker = (JDatePickerImpl)dateDebutCheckBox.getComponent();
            return (Date)datePicker.getModel().getValue();
        }
        return null;
    }
    
    public Date getDateFin() {
        if (dateFinCheckBox.isSelected()) {
            JDatePickerImpl datePicker = (JDatePickerImpl)dateFinCheckBox.getComponent();
            return (Date)datePicker.getModel().getValue();
        }
        return null;
    }
    
    //MUTATEURS
    private void initAll() {
        initTextField();
        initCheckBoxes();
    }
    private void initTextField() {
        textField = new JTextField();
    }
    private void initCheckBoxes() {
        textCheckBox = new CheckBoxComponent("Texte",textField);
        
        UtilDateModel modelDebut = new UtilDateModel();
        Properties p1 = new Properties();
        p1.put("text.today", "Aujourd'hui");
        p1.put("text.month", "Mois");
        p1.put("text.year", "Année");
        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(modelDebut,p1),new DateLabelFormatter());
        dateDebutCheckBox = new CheckBoxComponent("Date début",datePicker);
        
        UtilDateModel modelFin = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Aujourd'hui");
        p2.put("text.month", "Mois");
        p2.put("text.year", "Année");
        datePicker = new JDatePickerImpl(new JDatePanelImpl(modelFin,p2),new DateLabelFormatter());
        dateFinCheckBox = new CheckBoxComponent("Date fin",datePicker);
    }
    
    private void setComponents() {
        add(textCheckBox);
        add(dateDebutCheckBox);
        add(dateFinCheckBox);
    }
    
}
