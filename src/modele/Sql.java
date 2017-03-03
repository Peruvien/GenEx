package modele;

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

    public static boolean addExamen(boolean isExamen, Date date, Time duree, String libelle, String fichier){
        try {
            String insertExercice = "INSERT INTO EXAMEN"
                    + "(boolExamen, dateExamen, dureeExamen, libelleExamen, fichierExamen) VALUES"
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercice);
            preparedStatement.setBoolean(1, isExamen);
            preparedStatement.setDate(2, date);
            preparedStatement.setString(3, duree.toString());
            preparedStatement.setString(4, libelle);
            preparedStatement.setString(5, fichier);
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().addExamen(id, isExamen, date, duree, libelle, fichier);

            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addCours(int numeroCours, int modeCours, String libelleCours, String fichierCoursPath, Chapitre chapitre){
        try {
            String insertExercice = "INSERT INTO COURS"
                    + "(numeroCours, modeCours, libelleCours, fichiercours, idChapitre) VALUES"
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnexion.prepareStatement(insertExercice);
            preparedStatement.setInt(1, numeroCours);
            preparedStatement.setInt(2, modeCours);
            preparedStatement.setString(3, libelleCours);
            preparedStatement.setString(4, fichierCoursPath);
            preparedStatement.setInt(5, chapitre.getIdChapitre());
            if (preparedStatement.executeUpdate() == 0){
                return false;
            }
            int id = ((Number) preparedStatement.executeQuery("Select last_inster_rowid();")).intValue();
            System.out.println(id);
            Database.getINSTANCE().addCours(id, numeroCours, libelleCours, fichierCoursPath, chapitre);

            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
