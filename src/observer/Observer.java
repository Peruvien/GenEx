/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import bdd.Chapitre;
import bdd.Exercice;

/**
 *
 * @author robin
 */
public interface Observer {
    
    public void addChapitre(Chapitre exercice);
    public void addExercice(Exercice exercice);
    
}
