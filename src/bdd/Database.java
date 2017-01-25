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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Database {
    
    //ATTRIBUTS
    private final Connexion connexion;
    private final Map<Integer,Chapitre> chapitresMap;
    private final Map<Integer,Exercice> exercicesMap;
    private final Map<Integer,Cours> coursMap;
    private final Map<Integer,Examen> examensMap;
    private final Map<Integer,ExercicesDExamen> exercicesDExamenMap;
    private final Map<Integer,ExercicesDeCours> exercicesDeCoursMap;
    
    //CONSTRUCTEUR
    public Database(Connexion connexion, String path) {
        this.connexion = connexion;
        chapitresMap = new TreeMap<Integer,Chapitre>();
        exercicesMap = new TreeMap<Integer,Exercice>();
        coursMap = new TreeMap<Integer,Cours>();
        examensMap = new TreeMap<Integer,Examen>();
        exercicesDExamenMap = new TreeMap<Integer,ExercicesDExamen>();
        exercicesDeCoursMap = new TreeMap<Integer,ExercicesDeCours>();
        
        connexion.connecter(path);
        try {
            getDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //ACCESSEURS
    public Map<Integer,Chapitre> getChapitres() {
        return chapitresMap;
    }
    
    private void getDatabase() throws SQLException {
        int idChapitre, numeroChapitre;
        boolean presentiel;
        String libelleChapitre;
        
        String requete1 = "SELECT * FROM CHAPITRE";
        ResultSet res1 = connexion.executerRequete(requete1);
        while (res1.next()) {
            idChapitre = res1.getInt("idChapitre");
            numeroChapitre = res1.getInt("numeroChapitre");
            presentiel = res1.getBoolean("presentielChapitre");
            libelleChapitre = res1.getString("libelleChapitre");
            Chapitre chapitre = new Chapitre(idChapitre,numeroChapitre,presentiel,libelleChapitre);
            chapitresMap.put(idChapitre, chapitre);
        }
        
        
        int idExamen;
        boolean isExamen;
        Date dateExamen;
        Time dureeExamen;
        String libelleExamen, fichierExamenPath;
        
        String requete2 = "SELECT * FROM EXAMEN";
        ResultSet res2 = connexion.executerRequete(requete2);
        while (res2.next()) {
            idExamen = res2.getInt("idExamen");
            isExamen = res2.getBoolean("boolExamen");
            dateExamen = res2.getDate("dateExamen");
            dureeExamen = Time.valueOf(res2.getString("dureeExamen"));
            libelleExamen = res2.getString("libelleExamen");
            fichierExamenPath = res2.getString("fichierExamen");
            Examen examen = new Examen(idExamen,isExamen,dateExamen,dureeExamen,libelleExamen,fichierExamenPath);
            examensMap.put(idExamen, examen);
        }
        
        int idExercice, numeroExercice, pointsExercice;
        Time dureeExercice;
        String libelleExercice, fichierExercicePath;
        
        String requete3 = "SELECT * FROM EXERCICE";
        ResultSet res3 = connexion.executerRequete(requete3);
        while (res3.next()) {
            idExercice = res3.getInt("idExercice");
            numeroExercice = res3.getInt("numeroExercice");
            dureeExercice = Time.valueOf(res3.getString("dureeExercice"));
            pointsExercice = res3.getInt("pointsExercice");
            libelleExercice = res3.getString("libelleExercice");
            fichierExercicePath = res3.getString("fichierExercice");
            idChapitre = res3.getInt("idChapitre");
            Chapitre chapitreExercice = chapitresMap.get(idChapitre);
            Exercice exercice = new Exercice(idExercice,numeroExercice,dureeExercice,pointsExercice,libelleExercice,
                    fichierExercicePath,chapitreExercice);
            exercicesMap.put(idExercice, exercice);
            chapitresMap.get(idChapitre).addExercice(exercice);
        }
        
        int icCours, numeroCours;
        String fichierCoursPath;
        
        String requete4 = "SELECT * FROM COURS";
        ResultSet res4 = connexion.executerRequete(requete4);
        while (res4.next()) {
            icCours = res4.getInt("idCours");
            numeroCours = res4.getInt("numeroCours");
            fichierCoursPath = res4.getString("fichierCours");
            idChapitre = res4.getInt("idChapitre");
            Chapitre chapitreCours = chapitresMap.get(idChapitre);
            Cours cours = new Cours(icCours,numeroCours,fichierCoursPath,chapitreCours);
            coursMap.put(icCours, cours);
            chapitresMap.get(idChapitre).addCours(cours);
        }
        
        String requete5 = "SELECT * FROM EXERCICEEXAMEN GROUP BY idExamen";
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
            examen.addExercice(exercice);
            exosDexamen.addExercice(exercice);
        }
        
        String requete6 = "SELECT * FROM EXERCICECOURS GROUP BY idCours";
        ResultSet res6 = connexion.executerRequete(requete6);
        while (res6.next()) {
            icCours = res6.getInt("idCours");
            idExercice = res6.getInt("idExercice");
            Date dateUtilisation = Date.valueOf(res6.getString("dateUtilisation"));
            Cours cours = coursMap.get(icCours);
            Exercice exercice = exercicesMap.get(idExercice);
            ExercicesDeCours exercicesDeCours = exercicesDeCoursMap.get(icCours);
            if (exercicesDeCours == null) {
                exercicesDeCours = new ExercicesDeCours(cours, dateUtilisation);
                exercicesDeCoursMap.put(icCours, exercicesDeCours);
            }
            exercicesDeCours.addExercice(exercice);
            cours.addExercice(exercice);
        }
    }
    
    
    //MUTATEURS
    
    //Toutes les fonctions ci dessous peuvent renvoyer un entier pour vérifier si l'ajout à bien été fait
    public void addChapitre(int numero, boolean presentiel, String libelle, Set<Exercice> exercices, Set<Cours> tds){
        //String requete = "INSERT INTO CHAPITRE (numeroChapitre, presentielChapitre, libelleChapitre) VALUES (?,?,?)";
        String request = "INSERT INTO CHAPITRE (numeroChapitre, presentielChapitre, libelleChapitre) ( VALUES (";
        request += "'" + numero + "', ";
        if (presentiel)
            request += "1";
        else
            request += "0";
        request += ", ";
        request += "'" + libelle + "', ";
        request += ");";
        /*
        try {
            connexion.executerPreparedRequete(requete,"(int)" + numero,"(bool)" + presentiel, libelle);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        connexion.executerUpdate(request);
    }

//    public void add(Cours td){
//        String request = "INSERT INTO COURS (numeroCours, FichierCours, idChapitre) ( VALUES (";
//    }
//
    //STATIC
    //Fonction a utiliser lors de la création d'une nouvelle Database
    public static void create(Connexion connexion) throws SQLException{
        
        
        String requete2 = "CREATE TABLE EXERCICE (" +
                "idExercice INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "numeroExercice INTEGER," +
                "dureeExercice TEXT," +
                "pointsExercice INTEGER," +
                "libelleExercice TEXT," +
                "fichierExercice TEXT," +
                "idChapitre INTEGER," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)" +
                ");";
        connexion.executerUpdate(requete2);
        
        
        String requete1 = "CREATE TABLE CHAPITRE (" +
                "idChapitre INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "numeroChapitre INTEGER," +
                "presentielChapitre BOOLEAN," +
                "libelleChapitre TEXT" +
                ");";
        connexion.executerUpdate(requete1);
        
        
        String requete7 = "CREATE TABLE EXERCICECHAPITRE (" +
                "idChapitre INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idChapitre, idExercice)," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete7);
        
        
        String requete3 = "CREATE TABLE COURS (" +
                "idCours INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "numeroCours INTEGER," +
                "fichierCours TEXT," +
                "idChapitre INTEGER," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)" +
                ");";
        connexion.executerUpdate(requete3);
        
        
        String requete4 = "CREATE TABLE EXERCICECOURS (" +
                "dateUtilisation DATE," +
                "idCours INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idCours, idExercice)," +
                "FOREIGN KEY(idCours) REFERENCES COURS(idCours)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete4);
        
        
        //J'ai appeler le boolean "boolExamen", ça ne prend comme valeur 0 ou 1 et est de type BOOLEAN
        String requete5 = "CREATE TABLE EXAMEN (" +
                "idExamen INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "boolExamen BOOLEAN NOT NULL," +
                "dateExamen DATE," +
                "dureeExamen TEXT," +
                "libelleExamen TEXT," +
                "fichierExamen TEXT" +
                ");";
        connexion.executerUpdate(requete5);
        
        
        String requete6 = "CREATE TABLE EXERCICEEXAMEN (" +
                "idExamen INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idExamen, idExercice)," +
                "FOREIGN KEY(idExamen) REFERENCES EXAMEN(idExamen)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete6);
        
        //Faire un petit message comme quoi la création de table s'est bien passée :)
        System.out.println("La table a bien été créée !");
        //Je pense que faire une fenetre info pour le dire peut être cool aussi ^^
        //Techniquement, je peux te renvoyer un boolean pour dire si ça été créer ou non
    }
    
    
    
}
