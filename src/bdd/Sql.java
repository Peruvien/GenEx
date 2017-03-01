package bdd;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
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

    public static boolean addExercice(int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice, String fichierExercicePath, String tags) {
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
                    fichierExercicePath, tags);
            
            //numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean addChapitre(int numeroChapitre, boolean presentiel, String libelle){
        return true;
    }

    public static boolean addExamen(){
        return true;
    }

    public static boolean addCours(Cours cours){
        return true;
    }

}
