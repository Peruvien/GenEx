/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import modele.Chapitre;
import modele.PlancheTd;
import modele.Examen;
import modele.Exercice;

/**
 *
 * @author robin
 */
public interface Observer {
    
    public void addChapitre(Chapitre exercice);
    public void addCours(PlancheTd plancheTd);
    public void addExamen(Examen examen);
    public void addExercice(boolean presentiel, int idChapitre, Exercice exercice);
    public void addExerciceOfCours(boolean presentiel, int idChapitre, PlancheTd plancheTd, Exercice exercice);
    public void clearRecherche();
    public void addExerciceRecherche(Exercice exercice);
    
}