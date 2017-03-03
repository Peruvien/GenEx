/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Map;
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
    private Database(Connexion connexion, String path) {
        this.connexion = connexion;
        chapitresMap = new TreeMap<>();
        exercicesMap = new TreeMap<>();
        coursMap = new TreeMap<>();
        examensMap = new TreeMap<>();
        exercicesDExamenMap = new TreeMap<>();
        exercicesDeCoursMap = new TreeMap<>();
        
        connexion.connecter(path);
        try {
            getDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Database() {
        this.connexion = null;
        this.chapitresMap = null;
        this.exercicesMap = null;
        this.coursMap = null;
        this.examensMap = null;
        this.exercicesDExamenMap = null;
        this.exercicesDeCoursMap = null;
    }

    private static Database INSTANCE = null;

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    public static Database getINSTANCE(){
        return INSTANCE;
    }

    public static void setINSTANCE(Connexion c, String p){
        if (INSTANCE != null){
            System.err.println("You can't set a Database already setted, try reseting it first !");
        }else{
            INSTANCE = new Database(c, p);
        }
    }
    
    //ACCESSEURS
    public Map<Integer,Chapitre> getChapitres() {
        return chapitresMap;
    }
    
    public Map<Integer,Examen> getExamens() {
        return examensMap;
    }

    public Map<Integer, Cours> getCoursMap(){ return coursMap;}
    //TODO Refaire les selects pour virer les classes ExercicesDe...
    private void getDatabase() throws SQLException {
        int idChapitre, numeroChapitre, modeChapitre;
        String libelleChapitre;
        
        String requete1 = "SELECT * FROM CHAPITRE";
        ResultSet res1 = connexion.executerRequete(requete1);
        while (res1.next()) {
            idChapitre = res1.getInt("idChapitre");
            numeroChapitre = res1.getInt("numeroChapitre");
            modeChapitre = res1.getInt("modeChapitre");
            libelleChapitre = res1.getString("libelleChapitre");
            Chapitre chapitre = new Chapitre(idChapitre,numeroChapitre,modeChapitre,libelleChapitre);
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
            dateExamen = Date.valueOf(res2.getString("dateExamen"));
            dureeExamen = Time.valueOf(res2.getString("dureeExamen"));
            libelleExamen = res2.getString("libelleExamen");
            fichierExamenPath = res2.getString("fichierExamen");
            Examen examen = new Examen(idExamen,isExamen,dateExamen,dureeExamen,libelleExamen,fichierExamenPath);
            examensMap.put(idExamen, examen);
        }
        
        int idExercice, numeroExercice, pointsExercice, idCours;
        Time dureeExercice;
        String libelleExercice, fichierExercicePath, tagsExercice;
        
        String requete3 = "SELECT * FROM EXERCICE";
        ResultSet res3 = connexion.executerRequete(requete3);
        while (res3.next()) {
            idExercice = res3.getInt("idExercice");
            numeroExercice = res3.getInt("numeroExercice");
            dureeExercice = Time.valueOf(res3.getString("dureeExercice"));
            pointsExercice = res3.getInt("pointsExercice");
            libelleExercice = res3.getString("libelleExercice");
            fichierExercicePath = res3.getString("fichierExercice");
            tagsExercice = res3.getString("tagsExercice");
            idChapitre = res3.getInt("idChapitre");
            //TODO FAIRE FONCTIONNER CES LIGNES
            //Chapitre chapitreExercice = chapitresMap.get(idChapitre);
            //Exercice exercice = new Exercice(idExercice,numeroExercice,dureeExercice,pointsExercice,libelleExercice,
                    //fichierExercicePath,tagsExercice,chapitreExercice);
            //exercicesMap.put(idExercice, exercice);
            //chapitresMap.get(idChapitre).addExercice(exercice);
        }
        
        int numeroCours;
        String libelleCours, fichierCoursPath;
        
        String requete4 = "SELECT * FROM COURS";
        ResultSet res4 = connexion.executerRequete(requete4);
        while (res4.next()) {
            idCours = res4.getInt("idCours");
            numeroCours = res4.getInt("numeroCours");
            libelleCours = res4.getString("libelleCours");
            fichierCoursPath = res4.getString("fichierCours");
            idChapitre = res4.getInt("idChapitre");
            Chapitre chapitreCours = chapitresMap.get(idChapitre);
            Cours cours = new Cours(idCours,numeroCours,libelleCours,fichierCoursPath,chapitreCours);
            coursMap.put(idCours, cours);
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
            idCours = res6.getInt("idCours");
            idExercice = res6.getInt("idExercice");
            Date dateUtilisation = Date.valueOf(res6.getString("dateUtilisation"));
            Cours cours = coursMap.get(idCours);
            Exercice exercice = exercicesMap.get(idExercice);
            ExercicesDeCours exercicesDeCours = exercicesDeCoursMap.get(idCours);
            if (exercicesDeCours == null) {
                exercicesDeCours = new ExercicesDeCours(cours, dateUtilisation);
                exercicesDeCoursMap.put(idCours, exercicesDeCours);
            }
            exercicesDeCours.addExercice(exercice);
            cours.addExercice(exercice);
        }
    }
    
    
    //MUTATEURS
    
    //Toutes les fonctions ci dessous peuvent renvoyer un entier pour vérifier si l'ajout à bien été fait
    public void addChapitre(int idChapitre, int numeroChapitre, int modeChapitre, String libelle){
        this.chapitresMap.put(idChapitre, new Chapitre(idChapitre, numeroChapitre, modeChapitre, libelle));
    }

    public void addExercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice,
                            String libelleExercice, String fichierExercicePath, String tags, Chapitre chapitre){
        this.exercicesMap.put(idExercice, new Exercice(idExercice, numeroExercice, dureeExercice, pointsExercice,
                libelleExercice, fichierExercicePath, tags, chapitre));
    }

    public void addExamen(int idExamen, boolean isExamen, Date date, Time duree, String libelle, String fichier){
       this.examensMap.put(idExamen, new Examen(idExamen, isExamen, date, duree, libelle, fichier));
    }

    public void addCours(int idCours, int numeroCours, String libelleCours, String fichierCoursPath, Chapitre chapitre){
        //this.chapitresMap.put(chapitre.getIdChapitre(), new Cours(idCours, numeroCours, libelleCours, fichierCoursPath));
        this.chapitresMap.get(chapitre).addCours(new Cours(idCours, numeroCours, libelleCours, fichierCoursPath));
    }

   public void linkExeToCours(Exercice exercice, Cours cours){
        //TODO Vérifier si le cours est bien lié au chapitre auquel l'exercice est lié
        Chapitre temp = Database.getINSTANCE().chapitresMap.get(exercice.getChapitreExercice().getIdChapitre());
        //TODO Vérifier si le test fonctionne bien
        if(temp.getCours().contains(cours)) {
            Database.getINSTANCE().coursMap.get(cours.getIDCours()).addExercice(exercice);
        }else{
            System.err.println("Ce cours n'est pas dans le chapitre de cet exercice.");
        }
    }

    public void linkExeToChapitre(Exercice exercice, Chapitre chapitre){
        //TODO Verifier si cette commande sera utile si un exercice sera forcément lié à un chapitre
        Database.getINSTANCE().chapitresMap.get(chapitre.getIdChapitre()).addExercice(exercice);
    }

    public void linkExeToExamen(Exercice exercice, Examen examen){
        Database.getINSTANCE().examensMap.get(examen.getID()).addExercice(exercice);
    }

    //TODO Verifier si cette commande sera utile si un cours sera forcément lié à un chapitre
    public void linkCoursToChapitre(Cours cours, Chapitre chapitre){
        Database.getINSTANCE().chapitresMap.get(chapitre.getIdChapitre()).addCours(cours);
    }

    //STATIC
    //Fonction a utiliser lors de la création d'une nouvelle Database
    public static void create(Connexion connexion) throws SQLException{
        String requete1 = "CREATE TABLE CHAPITRE (" +
                "idChapitre INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numeroChapitre INTEGER NOT NULL," +
                "modeChapitre INTEGER NOT NULL," +
                "libelleChapitre TEXT," +
                "CONSTRAINT uniqueChapitre UNIQUE (modeChapitre, numeroChapitre)" +
                ");";
        connexion.executerUpdate(requete1);
        
        String requete2 = "CREATE TABLE EXERCICE (" +
                "idExercice INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numeroExercice INTEGER," +
                "dureeExercice TEXT," +
                "pointsExercice INTEGER," +
                "libelleExercice TEXT," +
                "fichierExercice TEXT," +
                "tagsExercice TEXT," +
                "idChapitre INTEGER," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)" +
                ");";
        connexion.executerUpdate(requete2);
        
        
        String requete3 = "CREATE TABLE EXERCICECHAPITRE (" +
                "idChapitre INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idChapitre, idExercice)," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete3);
        
        
        String requete4 = "CREATE TABLE COURS (" +
                "idCours INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numeroCours INTEGER," +
                "modeCours INTEGER," +
                "libelleCours TEXT," +
                "fichierCours TEXT," +
                "idChapitre INTEGER," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)" +
                ");";
        connexion.executerUpdate(requete4);
        
        
        String requete5 = "CREATE TABLE EXERCICECOURS (" +
                "dateUtilisation TEXT," +
                "idCours INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idCours, idExercice)," +
                "FOREIGN KEY(idCours) REFERENCES COURS(idCours)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete5);
        
        
        //J'ai appeler le boolean "boolExamen", ça ne prend comme valeur 0 ou 1 et est de type BOOLEAN
        String requete6 = "CREATE TABLE EXAMEN (" +
                "idExamen INTEGER PRIMARY KEY AUTOINCREMENT," +
                "boolExamen BOOLEAN NOT NULL," +
                "dateExamen DATE," +
                "dureeExamen TEXT," +
                "libelleExamen TEXT," +
                "fichierExamen TEXT" +
                ");";
        connexion.executerUpdate(requete6);
        
        
        String requete7 = "CREATE TABLE EXERCICEEXAMEN (" +
                "idExamen INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idExamen, idExercice)," +
                "FOREIGN KEY(idExamen) REFERENCES EXAMEN(idExamen)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete7);
        
        //Faire un petit message comme quoi la création de table s'est bien passée :)
        System.out.println("La table a bien été créée !");
        //Je pense que faire une fenetre info pour le dire peut être cool aussi ^^
        //Techniquement, je peux te renvoyer un boolean pour dire si ça été créer ou non
    }
    
    
    
}
