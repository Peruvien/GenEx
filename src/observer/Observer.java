/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import java.sql.Time;

/**
 *
 * @author robin
 */
public interface Observer {
    
    public void addChapitre(int idChapitre, int numeroChapitre, boolean presentiel, String libelle);
    public void addExercice(int idChapitre, boolean presentiel, int idExercice, int numeroExercice, Time duree, int points, String libelle);
    
}
