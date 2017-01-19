/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import bdd.Database;
import bdd.BDD;
import bdd.Chapitre;
import bdd.Connexion;

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
        notifyObserverChapitres();
    }
    
    //MUTATEURS
    public void ouvrirBDD(String chemin) {
        database = new Database(connexion);
    }
    
    public void creerBDD(String chemin) {
        connexion.connecter(chemin);
        try {
            Database.create(connexion);
        } catch (SQLException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deconnecter() {
        connexion.deconnecter();
    }
    
    
    //OBSERVABLE
    @Override
    public void setObserver(Observer obs) {
        observer = obs;
    }
    
    @Override
    public void notifyObserverChapitres() {
        Map<Integer,Chapitre> chapitres = bdd.getChapitres();
        Set<Entry<Integer,Chapitre>> chapitresSet = chapitres.entrySet();
        
        for (Entry<Integer,Chapitre> chapitre : chapitresSet) {
            Chapitre chapitreValue = chapitre.getValue();
            int idChapitre = chapitreValue.getID();
            int numeroChapitre = chapitreValue.getNumero();
            boolean presentiel = chapitreValue.isPresentiel();
            
            observer.addChapitre(idChapitre, numeroChapitre, presentiel, "Chapitre ");
            
            Set<Exercice> exercices = chapitreValue.getExercices();
            for (Exercice exercice : exercices) {
                int idExercice = exercice.getID();
                int numeroExercice = exercice.getNumero();
                Time dureeExercice = exercice.getDuree();
                int pointsExercice = exercice.getPoints();
                String libelleExercice = exercice.getLibelle();
                notifyObserverExercice(idChapitre, presentiel, idExercice, numeroExercice, dureeExercice, pointsExercice, libelleExercice);
            }
        }
    }
    
    @Override
    public void notifyObserverExercice(int idChapitre, boolean presentiel, int idExercice, int numeroExercice, Time duree, int points, String libelle) {
        observer.addExercice(idChapitre, presentiel, idExercice, numeroExercice, duree, points, libelle);
    }
    
}
