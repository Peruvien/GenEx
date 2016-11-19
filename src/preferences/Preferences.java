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
            if (System.getProperty("os.name").startsWith("Windows")) {
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
        String res = "Base de données : " + getBDD() + "\n";
        res += "Nommage fichier : " + getNommage() + "\n";
        res += "Résolution par défaut : " + getDefaultResolution() + "\n";
        res += "Dossier d'enregistrement : " + getDossierRecord() + "\n";
        res += "Dossier temporaire : " + getDossierTemp() + "\n";
        res += "Dossier final : " + getDossierFinal() + "\n";
        
        return res;
    }
    
    public String getBDD() {
        return ini.fetch("Base de données", "base_de_données");
    }
    public String getNommage() {
        return ini.fetch("Base de données", "nommage");
    }
    public String getDefaultResolution() {
        return ini.fetch("Base de données","résolution_défaut");
    }
    public String getDossierRecord() {
        return ini.fetch("Dossiers", "enregistrement");
    }
    public String getDossierTemp() {
        return ini.fetch("Dossiers", "temporaire");
    }
    public String getDossierFinal() {
        return ini.fetch("Dossiers", "final");
    }
    public String getScript() {
        return ini.fetch("Script", "fichier");
    }
    public boolean getScriptPriorite() {
        return Boolean.parseBoolean(ini.fetch("Script", "prioriteTerminees"));
    }
    public int getScriptMax() {
        return Integer.parseUnsignedInt(ini.fetch("Script", "nbMax"));
    }
    public String getScriptDebut() {
        return ini.fetch("Script", "debut");
    }
    public String getScriptEpisode() {
        return ini.fetch("Script", "episode");
    }
    public String getScriptFin() {
        return ini.fetch("Script", "fin");
    }
    
    //MUTATEURS
    public void load() {
        try {
            ini.load();
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setBDD(String path) {
        ini.put("Base de données", "base_de_données", path);
    }
    public void setNommage(String nom) {
        ini.put("Base de données", "nommage", nom);
    }
    public void setDefaultResolution(String resolution) {
        ini.put("Base de données", "résolution_défaut", resolution);
    }
    public void setDossierRecord(String path) {
        ini.put("Dossiers", "enregistrement", path);
    }
    public void setDossierTemp(String path) {
        ini.put("Dossiers", "temporaire", path);
    }
    public void setDossierFinal(String path) {
        ini.put("Dossiers", "final", path);
    }
    public void setScriptFichier(String path) {
        ini.put("Script", "fichier", path);
    }
    public void setScriptPriorite(boolean priorite) {
        ini.put("Script", "prioriteTerminees", priorite);
    }
    public void setScriptMax(int max) {
        ini.put("Script", "nbMax", max);
    }
    public void setScriptDebut(String str) {
        ini.put("Script", "debut", str);
    }
    public void setScriptEpisode(String str) {
        ini.put("Script", "episode", str);
    }
    public void setScriptfin(String str) {
        ini.put("Script", "fin", str);
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
        
        Section bdd = ini.add("Base de données");
        bdd.add("base_de_données", "");
        bdd.add("nommage", "[Titre] - [C]");
        bdd.add("résolution_défaut", "1080p");
        
        Section dossiers = ini.add("Dossiers");
        dossiers.add("enregistrement", "");
        dossiers.add("temporaire", "");
        dossiers.add("final", "");
        
        Section script = ini.add("Script");
        script.add("fichier", "");
        script.add("prioriteTerminees",true);
        script.add("nbMax", 1);
        script.add("debut","");
        script.add("episode","");
        script.add("fin","");
        
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
