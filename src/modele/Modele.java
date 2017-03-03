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
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import observer.Observable;
import observer.Observer;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Modele implements Observable {
    
    //ATTRIBUTS
    private final Connexion connexion;
    private Observer observer;
    private Database database;
    
    
    //CONSTRUCTEUR
    public Modele() {
        connexion = new Connexion();
    }
    
    
    //ACCESSEURS
    public void actualiserAffichage() {
        if (observer != null ){
            notifyObserverChapitres();
            notifyObserverExamens();
        }
    }
    
    
    //MUTATEURS
    public void ouvrirBDD(String chemin) {
        //database = new Database(connexion,chemin);
        if(Database.getINSTANCE() != null)
            Database.resetINSTANCE();
        Database.setINSTANCE(connexion, chemin);
        database = Database.getINSTANCE();
    }
    
    public boolean creerBDD(String chemin) {
        connexion.connecter(chemin);
        try {
            Database.create(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if(Database.getINSTANCE() != null)
            Database.resetINSTANCE();
        Database.setINSTANCE(connexion, chemin);
        database = Database.getINSTANCE();
        //database = new Database(connexion,chemin);

        return true;
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
    public void rechercherExercice(String ... tags) {
        String requete = "SELECT * FROM EXERCICE "
                       + "WHERE tagsExercice LIKE ";
        for (int i = 0; i < tags.length; i++) {
            if (i == 0) {
                requete += "'%" + tags[i] + "%'";
            }
            if (i > 0 && i < tags.length) {
                requete += " AND tagsExercice LIKE '%" + tags[i] + "%'";
            }
        }
        
        ResultSet res = connexion.executerRequete(requete);
        observer.clearRecherche();
        try {
            while (res.next()) {
                int idExercice = res.getInt("idExercice");
                int numeroExercice = res.getInt("numeroExercice");
                Time dureeExercice = Time.valueOf(res.getString("dureeExercice"));
                int pointsExercice = res.getInt("pointsExercice");
                String libelleExercice = res.getString("libelleExercice");
                String fichierExercice = res.getString("fichierExercice");
                String tagsExercice = res.getString("tagsExercice");
                int idChapitre = res.getInt("idChapitre");
                //int idCours = res.getInt("idCours");
                Chapitre chapitre = database.getChapitres().get(idChapitre);
                //Cours cours = database.getCoursMap().get(idCours);

                Exercice exercice = new Exercice(idExercice,numeroExercice,dureeExercice,pointsExercice,libelleExercice,fichierExercice,tagsExercice,chapitre);
                observer.addExerciceRecherche(exercice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rechercherExercice(Date dateDebut, Date dateFin, String ... tags) {
        String requete = "SELECT * FROM EXERCICE INNER JOIN EXERCICECOURS ON EXERCICE.idExercice = EXERCICECOURS.idExercice "
                       + "WHERE tagsExercice LIKE ";
        for (int i = 0; i < tags.length; i++) {
            if (i == 0) {
                requete += "'%" + tags[i] + "%'";
            }
            if (i > 0 && i < tags.length) {
                requete += " OR tagsExercice LIKE '%" + tags[i] + "%'";
            }
        }
        if (dateDebut != null) {
            requete += " AND dateUtilisation > '" + dateDebut.toString() + "'";
        }
        if (dateFin != null) {
            requete += " AND dateUtilisation < '" + dateFin.toString() + "'";
        }
        
        System.out.println(requete);
        ResultSet res = connexion.executerRequete(requete);
        observer.clearRecherche();
        try {
            while (res.next()) {
                int idExercice = res.getInt("idExercice");
                int numeroExercice = res.getInt("numeroExercice");
                Time dureeExercice = Time.valueOf(res.getString("dureeExercice"));
                int pointsExercice = res.getInt("pointsExercice");
                String libelleExercice = res.getString("libelleExercice");
                String fichierExercice = res.getString("fichierExercice");
                String tagsExercice = res.getString("tagsExercice");
                int idChapitre = res.getInt("idChapitre");
                Chapitre chapitre = database.getChapitres().get(idChapitre);
                Exercice exercice = new Exercice(idExercice,numeroExercice,dureeExercice,pointsExercice,
                        libelleExercice,fichierExercice,tagsExercice,chapitre);
                observer.addExerciceRecherche(exercice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    //OBSERVABLE
    @Override
    public void setObserver(Observer obs) {
        observer = obs;
    }
    
    @Override
    public void notifyObserverChapitres() {
        Map<Integer,Chapitre> chapitres = database.getChapitres();
        Set<Entry<Integer,Chapitre>> chapitresSet = chapitres.entrySet();
        
        for (Entry<Integer,Chapitre> chapitreEntry : chapitresSet) {
            Chapitre chapitre = chapitreEntry.getValue();
            observer.addChapitre(chapitre);
            boolean presentiel = (chapitre.getModeChapitre() < 2);
            int idChapitre = chapitre.getIdChapitre();
            
            Set<Cours> cours = chapitre.getCours();
            for (Cours cour : cours) {
                notifyObserverCours(cour);
                Set<Exercice> exercicesCours = cour.getExercices();
                for (Exercice exerciceCours : exercicesCours) {
                    observer.addExerciceOfCours(presentiel, idChapitre, cour, exerciceCours);
                }
            }
            
            Set<Exercice> exercices = chapitre.getExercices();
            for (Exercice exercice : exercices) {
                notifyObserverExercice(presentiel, idChapitre, exercice);
            }
            
        }
    }
    
    @Override
    public void notifyObserverExercice(boolean presentiel, int idChapitre, Exercice exercice) {
        observer.addExercice(presentiel, idChapitre, exercice);
    }
    
    public void notifyObserverCours(Cours cours) {
        observer.addCours(cours);
    }
    
    public void notifyObserverExamens() {
        Map<Integer,Examen> examensMap = database.getExamens();
        Set<Entry<Integer,Examen>> examensSet = examensMap.entrySet();
        
        for (Entry<Integer,Examen> examenEntry : examensSet) {
            Examen examen = examenEntry.getValue();
            observer.addExamen(examen);
        }
    }
    
}
