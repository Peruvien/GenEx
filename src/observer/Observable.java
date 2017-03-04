/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import modele.Exercice;
import modele.Planche;

/**
 *
 * @author robin
 */
public interface Observable {
    
    public void setObserver(Observer obs);
    public void notifyObserverChapitres();
    public void notifyObserverCours(boolean presentiel, int idChapitre, Planche planche);
    public void notifyObserverExercice(boolean presentiel, int idChapitre, Exercice exercice);
    
}
