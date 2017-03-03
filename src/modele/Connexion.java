/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Robin
 * @author Vincent
 */

import java.sql.*;
import java.util.logging.*;

public class Connexion {
    
    //ATTRIBUTS
    private String database;
    private Connection connexion;
    private Statement statement;
    
    
    //CONSTRUCTEUR
    public Connexion() {
        
    }
    
    
    //ACCESSSEURS
    public static boolean isAccess(String path) {
        String lowerCase = path.toLowerCase();
        return lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb");
    }
    
    public static boolean isSQLite(String path) {
        String lowerCase = path.toLowerCase();
        return lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
            || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db3")
            || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
            || lowerCase.endsWith(".sl3") || lowerCase.endsWith(".db2")
            || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
            || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".kexi");
    }
    
    //MUTATEURS
    private void setDatabase(String db) {
        database = db;
    }

    private void setConnection(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connexion = DriverManager.getConnection(database);
            Sql.setConnexion(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setStatement() {
        try {
            statement = connexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connecter(String path) {
        String lowerCase = path.toLowerCase();
        boolean isAccess = lowerCase.endsWith(".accdb") || lowerCase.endsWith(".mdb");
        boolean isSQLite = lowerCase.endsWith(".db") || lowerCase.endsWith(".sdb")
                        || lowerCase.endsWith(".sqlite") || lowerCase.endsWith(".db3")
                        || lowerCase.endsWith(".s3db") || lowerCase.endsWith(".sqlite3")
                        || lowerCase.endsWith(".sl3") || lowerCase.endsWith(".db2")
                        || lowerCase.endsWith(".s2db") || lowerCase.endsWith(".sqlite2")
                        || lowerCase.endsWith(".sl2") || lowerCase.endsWith(".kexi");
        
        if (isAccess) {
            setDatabase("jdbc:ucanaccess://" + path + ";newdatabaseversion=V2007");
        }
        if (isSQLite) {
            setDatabase("jdbc:sqlite:" + path);
        }
        
        if (isAccess) {
            setConnection("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        if (isSQLite) {
            setConnection("org.sqlite.JDBC");
        }
        
        setStatement();
        
        try {
            statement.setQueryTimeout(30);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet executerPreparedRequete(String requete, String  ... args) throws SQLException {
        ResultSet res = null;
        try (PreparedStatement prepared = connexion.prepareStatement(requete, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
            //mise en place des paramètres pour le PreparedStatement
            for (int i = 0; i < args.length; i++) {
                if (args[i].startsWith("(int)")) {
                    prepared.setInt(i+1, Integer.parseInt(args[i].replace("(int)","")));
                }
                else {
                    if (args[i].startsWith("(bool)")) {
                        prepared.setBoolean(i+1, Boolean.parseBoolean(args[i].replace("(bool)","")));
                    }
                    else {
                        prepared.setString(i+1, args[i]);
                    }
                }
            }
            
            if (requete.startsWith("SELECT")) {
                res = prepared.executeQuery();
            }
            //si la requete est une UPDATE ou une INSERT
            else {
                prepared.executeUpdate();
            }
            prepared.close();
        }
        return res;
    }
    
    public ResultSet executerRequete(String requete) {
        ResultSet res = null;
        try {
            res = statement.executeQuery(requete);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public void executerUpdate(String requete) {
        try {
            statement.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deconnecter() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



}
