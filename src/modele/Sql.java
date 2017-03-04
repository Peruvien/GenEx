package modele;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Hamor.
 */
public abstract class Sql{
    private static Connection dbConnexion;

    protected static void setConnexion(Connection c){
        dbConnexion = c;
    }

    public static boolean addExercice(int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice, String fichierExercicePath, String tags, Chapitre chapitre) {
        try {
            String insertExercice = "INSERT INTO EXERCICE"
                    + "(numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercice) VALUES"
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercice);
            preparedStatement.setInt(1, numeroExercice);
            preparedStatement.setTime(2, dureeExercice);
            preparedStatement.setInt(3, pointsExercice);
            preparedStatement.setString(4, libelleExercice);
            preparedStatement.setString(5, tags);
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().addExercice(id, numeroExercice, dureeExercice, pointsExercice, libelleExercice,
                    fichierExercicePath, tags, chapitre);
            
            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addChapitre(int numeroChapitre, int modeChapitre, String libelle){
        try {
            String insertExercice = "INSERT INTO CHAPITRE"
                    + "(numeroChapitre, modeChapitre, libelleChapitre) VALUES"
                    + "(?,?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercice);
            preparedStatement.setInt(1, numeroChapitre);
            preparedStatement.setInt(2, modeChapitre);
            preparedStatement.setString(3, libelle);
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().addChapitre(id, numeroChapitre, modeChapitre, libelle);

            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addExamen(boolean isExamen, boolean isPresentiel, Date date, Time duree, String libelle, String fichier){
        try {
            String insertExercice = "INSERT INTO EXAMEN"
                    + "(boolExamen, boolPresentiel, dateExamen, dureeExamen, libelleExamen, fichierExamen) VALUES"
                    + "(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercice);
            preparedStatement.setBoolean(1, isExamen);
            preparedStatement.setBoolean(2, isPresentiel);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, duree.toString());
            preparedStatement.setString(5, libelle);
            preparedStatement.setString(6, fichier);
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().addExamen(id, isExamen, isPresentiel, date, duree, libelle, fichier);

            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addPlanche(int numeroPlanche, int modePlanche, Date datePlanche, String libellePlanche, String fichierPlanchePath, Chapitre chapitre){
        try {
            String insertExercice = "INSERT INTO PLANCHE"
                    + "(numeroPlanche, modePlanche, datePlanche, libellePlanche, fichierPlanche, idChapitre) VALUES"
                    + "(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercice);
            preparedStatement.setInt(1, numeroPlanche);
            preparedStatement.setInt(2, modePlanche);
            preparedStatement.setDate(3, datePlanche);
            preparedStatement.setString(4, libellePlanche);
            preparedStatement.setString(5, fichierPlanchePath);
            preparedStatement.setInt(6, chapitre.getIdChapitre());
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().addPlanche(id, numeroPlanche, modePlanche, datePlanche, libellePlanche, fichierPlanchePath, chapitre);
            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addExercicePlanche(Exercice exercice, Planche planche){
        //Verifier si l'exercice et la planche appartiennent au même chapitre
        Chapitre chapitre = exercice.getChapitreExercice();
        if(!Database.getINSTANCE().getChapitres().get(chapitre.getIdChapitre()).getCours().contains(planche)){
            return false;
        }

        try {
            String insertExercicePlanche = "INSERT INTO EXERCICEPLANCHE"
                    + "(idPlanche, idExercice) VALUES"
                    + "(?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercicePlanche);
            preparedStatement.setInt(1, exercice.getIdExercice());
            preparedStatement.setInt(2, planche.getIDPlanche());
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().linkExeToPlanche(exercice, planche);

            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addExerciceChapitre(Exercice exercice, Chapitre chapitre){
        //Verifier si l'exercice et la planche appartiennent au même chapitre
        if(chapitre != exercice.getChapitreExercice()){
            return false;
        }

        try {
            String insertExercicePlanche = "INSERT INTO EXERCICECHAPITRE"
                    + "(idChapitre, idExercice) VALUES"
                    + "(?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercicePlanche);
            preparedStatement.setInt(1, chapitre.getIdChapitre());
            preparedStatement.setInt(2, exercice.getIdExercice());
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().linkExeToChapitre(exercice);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addExerciceExamen(Exercice exercice, Examen examen){
        try {
            String insertExercicePlanche = "INSERT INTO EXERCICEEXAMEN"
                    + "(idExamen, idExercice) VALUES"
                    + "(?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercicePlanche);
            preparedStatement.setInt(1, examen.getID());
            preparedStatement.setInt(2, exercice.getIdExercice());
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().linkExeToExamen(exercice, examen);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //STATIC
    //Fonction a utiliser lors de la création d'une nouvelle BDD sqlite
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

        String requete4 = "CREATE TABLE PLANCHE (" +
                "idPlanche INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numeroPlanche INTEGER," +
                "modePlanche INTEGER," +
                "datePlanche DATE," +
                "libellePlanche TEXT," +
                "fichierPlanche TEXT," +
                "idChapitre INTEGER," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)" +
                ");";
        connexion.executerUpdate(requete4);

        String requete3 = "CREATE TABLE EXERCICECHAPITRE (" +
                "idChapitre INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idChapitre, idExercice)," +
                "FOREIGN KEY(idChapitre) REFERENCES CHAPITRE(idChapitre)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete3);


        String requete5 = "CREATE TABLE EXERCICEPLANCHE (" +
                "idPlanche INTEGER NOT NULL," +
                "idExercice INTEGER NOT NULL," +
                "PRIMARY KEY(idPlanche, idExercice)," +
                "FOREIGN KEY(idPlanche) REFERENCES COURS(idPlanche)," +
                "FOREIGN KEY(idExercice) REFERENCES EXERCICE(idExercice)" +
                ");";
        connexion.executerUpdate(requete5);


        //J'ai appeler le boolean "boolExamen", ça ne prend comme valeur 0 ou 1 et est de type BOOLEAN
        String requete6 = "CREATE TABLE EXAMEN (" +
                "idExamen INTEGER PRIMARY KEY AUTOINCREMENT," +
                "boolExamen BOOLEAN NOT NULL," +
                "boolPresentiel BOOLEAN NOT NULL," +
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
