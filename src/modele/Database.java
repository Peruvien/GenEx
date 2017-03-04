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
import java.util.ArrayList;
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
    private final Map<Integer, Planche> coursMap;
    private final Map<Integer,Examen> examensMap;
    private final Map<Integer,ExercicesDExamen> exercicesDExamenMap;
    private final Map<Integer,ExercicesDeCours> exercicesDeCoursMap;
    private final Map<Exercice, ArrayList<Examen>> usageExercice;
    
    //CONSTRUCTEUR
    private Database(Connexion connexion, String path) {
        this.connexion = connexion;
        chapitresMap = new TreeMap<>();
        exercicesMap = new TreeMap<>();
        coursMap = new TreeMap<>();
        examensMap = new TreeMap<>();
        exercicesDExamenMap = new TreeMap<>();
        exercicesDeCoursMap = new TreeMap<>();
        usageExercice = new TreeMap<>();
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
        this.usageExercice = null;
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

    public Map<Integer, Planche> getCoursMap(){ return coursMap;}

    public Map<Exercice, ArrayList<Examen>> getUsageExercice(){return usageExercice;}

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
        boolean isExamen, isPresentiel;
        Date dateExamen;
        Time dureeExamen;
        String libelleExamen, fichierExamenPath;
        
        String requete2 = "SELECT * FROM EXAMEN";
        ResultSet res2 = connexion.executerRequete(requete2);
        while (res2.next()) {
            idExamen = res2.getInt("idExamen");
            isExamen = res2.getBoolean("boolExamen");
            isPresentiel = res2.getBoolean("boolPresentiel");
            dateExamen = Date.valueOf(res2.getString("dateExamen"));
            dureeExamen = Time.valueOf(res2.getString("dureeExamen"));
            libelleExamen = res2.getString("libelleExamen");
            fichierExamenPath = res2.getString("fichierExamen");
            Examen examen = new Examen(idExamen,isExamen,isPresentiel,dateExamen,dureeExamen,libelleExamen,fichierExamenPath);
            examensMap.put(idExamen, examen);
        }

        int numeroCours, idPlanche;
        String libelleCours, fichierCoursPath;

        String requete4 = "SELECT * FROM PLANCHE";
        ResultSet res4 = connexion.executerRequete(requete4);
        while (res4.next()) {
            idPlanche = res4.getInt("idPlanche");
            numeroCours = res4.getInt("numeroPlanche");
            libelleCours = res4.getString("libellePlanche");
            fichierCoursPath = res4.getString("fichierPlanche");
            idChapitre = res4.getInt("idChapitre");
            Chapitre chapitreCours = chapitresMap.get(idChapitre);
            Planche planche = new Planche(idPlanche,numeroCours,libelleCours,fichierCoursPath,chapitreCours);
            coursMap.put(idPlanche, planche);
            chapitresMap.get(idChapitre).addCours(planche);
        }
        
        int idExercice, numeroExercice, pointsExercice;
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
            Chapitre chapitreExercice = chapitresMap.get(idChapitre);
            Exercice exercice = new Exercice(idExercice,numeroExercice,dureeExercice,pointsExercice,libelleExercice,
                    fichierExercicePath,tagsExercice,chapitreExercice);
            exercicesMap.put(idExercice, exercice);

            chapitreExercice.addExercice(exercice);
            usageExercice.put(exercice, new ArrayList<Examen>());
        }
        

        
        String requete5 = "SELECT * FROM EXERCICEEXAMEN GROUP BY idExamen";
        ResultSet res5 = connexion.executerRequete(requete5);
        while (res5.next()) {
            idExamen = res5.getInt("idExamen");
            idExercice = res5.getInt("idExercice");
            Exercice exercice = exercicesMap.get(idExercice);
            Examen examen = examensMap.get(idExamen);
            /*
            ExercicesDExamen exosDexamen = exercicesDExamenMap.get(idExamen);
            if (exosDexamen == null) {
                exosDexamen = new ExercicesDExamen(examen);
                exercicesDExamenMap.put(idExamen,exosDexamen);
            }
            examen.addExercice(exercice);
            exosDexamen.addExercice(exercice);
            */
            exercice.addUsageExamen();
            usageExercice.get(exercice).add(examen);
        }
        
        String requete6 = "SELECT * FROM EXERCICEPLANCHE GROUP BY idPlanche";
        ResultSet res6 = connexion.executerRequete(requete6);
        while (res6.next()) {
            idPlanche = res6.getInt("idCours");
            idExercice = res6.getInt("idExercice");
            Date dateUtilisation = Date.valueOf(res6.getString("dateUtilisation"));
            Planche planche = coursMap.get(idPlanche);
            Exercice exercice = exercicesMap.get(idExercice);
            /*
            ExercicesDeCours exercicesDeCours = exercicesDeCoursMap.get(idPlanche);
            if (exercicesDeCours == null) {
                exercicesDeCours = new ExercicesDeCours(planche, dateUtilisation);
                exercicesDeCoursMap.put(idPlanche, exercicesDeCours);
            }
            exercicesDeCours.addExercice(exercice);
            planche.addExercice(exercice);
            */
            planche.addExercice(exercice);
            exercice.addUsagePlanche();
        }

        String requete7 = "SELECT * FROM EXERCICECHAPITRE GROUP BY idChapitre";
        ResultSet res7 = connexion.executerRequete(requete7);
        while (res7.next()){
            idChapitre = res7.getInt("idChapitre");
            idExercice = res7.getInt("idExercice");
            Chapitre chapitre = chapitresMap.get(idChapitre);
            Exercice exercice = exercicesMap.get(idExercice);

            exercice.setIllustration(true);
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

    public void addExamen(int idExamen, boolean isExamen, boolean isPresentiel, Date date, Time duree, String libelle, String fichier){
       this.examensMap.put(idExamen, new Examen(idExamen, isExamen, isPresentiel, date, duree, libelle, fichier));
    }

    public void addCours(int idCours, int numeroCours, String libelleCours, String fichierCoursPath, Chapitre chapitre){
        //this.chapitresMap.put(chapitre.getIdChapitre(), new Planche(idCours, numeroCours, libelleCours, fichierCoursPath));
        this.chapitresMap.get(chapitre).addCours(new Planche(idCours, numeroCours, libelleCours, fichierCoursPath));
    }

   public void linkExeToCours(Exercice exercice, Planche planche){
        //TODO Vérifier si le planche est bien lié au chapitre auquel l'exercice est lié
        Chapitre temp = Database.getINSTANCE().chapitresMap.get(exercice.getChapitreExercice().getIdChapitre());
        //TODO Vérifier si le test fonctionne bien
        if(temp.getCours().contains(planche)) {
            Database.getINSTANCE().coursMap.get(planche.getIDCours()).addExercice(exercice);
        }else{
            System.err.println("Ce planche n'est pas dans le chapitre de cet exercice.");
        }
    }

    public void linkExeToChapitre(Exercice exercice, Chapitre chapitre){
        //TODO Verifier si cette commande sera utile si un exercice sera forcément lié à un chapitre
        Database.getINSTANCE().chapitresMap.get(chapitre.getIdChapitre()).addExercice(exercice);
    }

    public void linkExeToExamen(Exercice exercice, Examen examen){
        Database.getINSTANCE().examensMap.get(examen.getID()).addExercice(exercice);
    }

    //TODO Verifier si cette commande sera utile si un planche sera forcément lié à un chapitre
    public void linkCoursToChapitre(Planche planche, Chapitre chapitre){
        Database.getINSTANCE().chapitresMap.get(chapitre.getIdChapitre()).addCours(planche);
    }


}
