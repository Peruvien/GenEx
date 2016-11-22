/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author robin
 * @author Vincent
 */
public class BDD {
    
    //ATTRIBUTS
    private final ArrayList<Chapitre> chapitres;
    private final ArrayList<Exercice> exercices;
    private final ArrayList<TD> tds;
    private final ArrayList<Examen> examens;
    
    
    //CONSTRUCTEUR
    public BDD(Connexion connexion) {
        chapitres = new ArrayList();
        exercices = new ArrayList();
        tds = new ArrayList();
        examens = new ArrayList();
        
        
    }
    
    
    //ACCESSEURS
    
    
    //MUTATEURS
    private void initBDD(Connexion connexion) throws SQLException {
        int idChapitre, numeroChapitre;
        boolean presentiel;
        String libelleChapitre;
        
        String requete1 = "SELECT * FROM Chapitres";
        ResultSet res1 = connexion.executerRequete(requete1);
        while (res1.next()) {
            idChapitre = res1.getInt("idChapitre");
            numeroChapitre = res1.getInt("numeroChapitre");
            presentiel = res1.getBoolean("PresentielChapitre");
            libelleChapitre = res1.getString("LibelleChapitre");
            chapitres.add(new Chapitre(idChapitre,numeroChapitre,presentiel,libelleChapitre));
        }
        
        
        int idExamen;
        Date dateExamen;
        Time heureExamen, dureeExamen;
        String libelleExamen, fichierExamenPath;
        
        String requete2= "SELECT * FROM Examens";
        ResultSet res2 = connexion.executerRequete(requete2);
        while (res2.next()) {
            idExamen = res2.getInt("idExamen");
            dateExamen = res2.getDate("dateExamen");
            heureExamen = res2.getTime("HeureExamen");
            dureeExamen = res2.getTime("dureeExamen");
            libelleExamen = res2.getString("LibelleExamen");
            fichierExamenPath = res2.getString("fichierExamen");
            examens.add(new Examen(idExamen,dateExamen,heureExamen,dureeExamen,libelleExamen,fichierExamenPath));
        }
        
        
        
        
    }
}
