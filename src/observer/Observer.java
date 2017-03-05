/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import modele.Chapitre;
import modele.Planche;
import modele.Examen;
import modele.Exercice;

/**
 *
 * @author robin
 */
public interface Observer {
    
    public void clear();
    public void addChapitre(Chapitre exercice);
    public void addPlanche(boolean presentiel, int idChapitre, Planche planche);
    public void addExamen(Examen examen);
    public void addExercice(boolean presentiel, int idChapitre, Exercice exercice);
    public void addExerciceOfCours(boolean presentiel, int idChapitre, Planche planche, Exercice exercice);
    public void clearRecherche();
    public void addExerciceRecherche(Exercice exercice);
    
}