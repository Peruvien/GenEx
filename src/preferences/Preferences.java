/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preferences;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;


/**
 *
 * @author Robin
 * @author Vincent
 */
public class Preferences {
    
    //ATTRIBUTS
    private Ini ini;
    private File filePreferences;
    
    //CONSTRUCTEURS
    public Preferences(File filePreferences) {
        this.filePreferences = filePreferences;
        boolean created = false;
        try {
            created = this.filePreferences.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                ini = new Wini(this.filePreferences);
            }
            else {
                ini = new Ini(this.filePreferences);
            }
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        ini.getConfig().setFileEncoding(Charset.forName("utf8"));
        if (created) {
            createPreferencesFile();
        }
    }
    public Preferences(String pathPreferences) {
        this(new File(pathPreferences));
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        String res = "Dossier bases de données : " + getDossierBDD() + "\n";
        return res;
    }
    
    public String getDossierBDD() {
        return ini.fetch("Bases de données", "bases_de_données");
    }
    
    
    //MUTATEURS
    public void load() {
        try {
            ini.load();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDossierBDD(String path) {
        ini.put("Bases de données", "bases_de_données", path);
    }
    
    
    public void savePreferences() {
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void createPreferencesFile() {
        ini.clear();
        ini.setFile(filePreferences);
        
        Section bdd = ini.add("Bases de données");
        bdd.add("bases_de_données", "");
        
        PreferencesDialog prefsDialog = new PreferencesDialog(null, "Initialisation des Préferences", true, null, this);
        prefsDialog.showDialog();
        
        try {
            ini.store();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void destroyPreferencesFile() {
        filePreferences.delete();
    }
    
}
