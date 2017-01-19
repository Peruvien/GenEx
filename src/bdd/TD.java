/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.File;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class TD implements Comparable<TD> {
    
    //ATTRIBUTS
    private final int idTD;
    private Chapitre chapitre;
    private final int numeroTD;
    private final File fichier;
    
    //CONSTRUCTEUR
    public TD(int idTD, int numeroTD, String fichierTDPath) {
        this.idTD = idTD;
        this.numeroTD = numeroTD;
        this.fichier = new File(fichierTDPath);
    }
    public TD(int idTD, int numeroTD, String fichierTDPath, Chapitre chapitreTD) {
        this(idTD,numeroTD,fichierTDPath);
        this.chapitre = chapitreTD;
    }
    
    
    //ACCESSEURS

    public int getIdTD() {
        return idTD;
    }

    public Chapitre getChapitre() {
        return chapitre;
    }

    public int getNumeroTD() {
        return numeroTD;
    }

    public File getFichier() {
        return fichier;
    }


    //MUTATEURS
    
    
    //COMPARABLE
    @Override
    public int compareTo(TD o) {
        int res = chapitre.compareTo(o.chapitre);
        if (res == 0) {
            res = numeroTD - o.numeroTD;
        }
        return res;
    }
}
