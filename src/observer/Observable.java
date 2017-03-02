/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import bdd.Cours;
import bdd.Exercice;
import java.sql.Time;

/**
 *
 * @author robin
 */
public interface Observable {
    
    public void setObserver(Observer obs);
    public void notifyObserverChapitres();
    public void notifyObserverExercice(int idChapitre,Exercice exercice);
    public void notifyObserverCours(int idChapitre, Cours cours);
    
}
