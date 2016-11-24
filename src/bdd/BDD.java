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
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author robin
 * @author Vincent
 */
public class BDD {
    
    //ATTRIBUTS
    private final ArrayList<Chapitre> chapitres;
    private final Map<Integer,Chapitre> chapitresMap;
    private final ArrayList<Exercice> exercices;
    private final Map<Integer,Exercice> exercicesMap;
    private final ArrayList<TD> tds;
    private final Map<Integer,TD> tdsMap;
    private final ArrayList<Examen> examens;
    private final Map<Integer,Examen> examensMap;
    private final Map<Integer,ExercicesDExamen> exercicesDExamenMap;
    private final Map<Integer,ExercicesDeTD> exercicesDeTDMap;
    
    //CONSTRUCTEUR
    public BDD(Connexion connexion) {
        chapitres = new ArrayList();
        chapitresMap = new TreeMap();
        exercices = new ArrayList();
        exercicesMap = new TreeMap();
        tds = new ArrayList();
        tdsMap = new TreeMap();
        examens = new ArrayList();
        examensMap = new TreeMap();
        exercicesDExamenMap = new TreeMap();
        exercicesDeTDMap = new TreeMap();
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
            presentiel = res1.getBoolean("presentielChapitre");
            libelleChapitre = res1.getString("libelleChapitre");
            Chapitre chapitre = new Chapitre(idChapitre,numeroChapitre,presentiel,libelleChapitre);
            chapitres.add(chapitre);
            chapitresMap.put(idChapitre, chapitre);
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
            heureExamen = res2.getTime("heureExamen");
            dureeExamen = res2.getTime("dureeExamen");
            libelleExamen = res2.getString("libelleExamen");
            fichierExamenPath = res2.getString("fichierExamen");
            Examen examen = new Examen(idExamen,dateExamen,heureExamen,dureeExamen,libelleExamen,fichierExamenPath);
            examens.add(examen);
            examensMap.put(idExamen, examen);
        }
        
        int idExercice, numeroExercice, pointsExercice;
        Time dureeExercice;
        String libelleExercice, fichierExercicePath;
        
        String requete3 = "SELECT * FROM Exercices";
        ResultSet res3 = connexion.executerRequete(requete3);
        while (res3.next()) {
            idExercice = res3.getInt("idExercice");
            numeroExercice = res3.getInt("numeroExercice");
            dureeExercice = res3.getTime("dureeExercice");
            pointsExercice = res3.getInt("pointsExercice");
            libelleExercice = res3.getString("libelleExercice");
            fichierExercicePath = res3.getString("fichierExercice");
            idChapitre = res3.getInt("idChapitre");
            //int indexChapitre = chapitres.indexOf(new Chapitre(idChapitre,0,false,null));
            //Chapitre chapitreExercice = chapitres.get(indexChapitre);
            Chapitre chapitreExercice = chapitresMap.get(idChapitre);
            Exercice exercice = new Exercice(idExercice,numeroExercice,dureeExercice,pointsExercice,libelleExercice,fichierExercicePath,chapitreExercice);
        }
        
        int idTD, numeroTD;
        String fichierTDPath;
        
        String requete4 = "SELECT * FROM TDs";
        ResultSet res4 = connexion.executerRequete(requete4);
        while (res4.next()) {
            idTD = res4.getInt("idTD");
            numeroTD = res4.getInt("numeroTD");
            fichierTDPath = res4.getString("fichierTD");
            idChapitre = res4.getInt("idChapitre");
            //int indexChapitre = chapitres.indexOf(new Chapitre(idChapitre,0,false,null));
            //Chapitre chapitreTD = chapitres.get(indexChapitre);
            Chapitre chapitreTD = chapitresMap.get(idChapitre);
            TD td = new TD(idTD,numeroTD,fichierTDPath,chapitreTD);
        }
        
        String requete5 = "SELECT * FROM ExercicesDExamen GROUP BY idExamen";
        ResultSet res5 = connexion.executerRequete(requete5);
        while (res5.next()) {
            idExamen = res5.getInt("idExamen");
            idExercice = res5.getInt("idExercice");
            Exercice exercice = exercicesMap.get(idExercice);
            Examen examen = examensMap.get(idExamen);
            ExercicesDExamen exosDexamen = exercicesDExamenMap.get(idExamen);
            if (exosDexamen == null) {
                exosDexamen = new ExercicesDExamen(examen);
                exercicesDExamenMap.put(idExamen,exosDexamen);
            }
            exosDexamen.addExercice(exercice);
        }
        
        String requete6 = "SELECT * FROM ExercicesDeTD GROUP BY idTD";
        ResultSet res6 = connexion.executerRequete(requete6);
        while (res6.next()) {
            idTD = res6.getInt("idTD");
            idExercice = res6.getInt("idExercice");
            Date dateUtilisation = res6.getDate("dateUtilisation");
            TD td = tdsMap.get(idTD);
            Exercice exercice = exercicesMap.get(idExercice);
            ExercicesDeTD exercicesDeTD = exercicesDeTDMap.get(idTD);
            if (exercicesDeTD == null) {
                exercicesDeTD = new ExercicesDeTD(td, dateUtilisation);
                exercicesDeTDMap.put(idTD, exercicesDeTD);
            }
            exercicesDeTD.addExercice(exercice);
            
        }
        
        
        
    }
}
