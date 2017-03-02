/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer;

import bdd.Chapitre;
import bdd.Cours;
import bdd.Examen;
import bdd.Exercice;

/**
 *
 * @author robin
 */
public interface Observer {
    
    public void addChapitre(Chapitre exercice);
    public void addExercice(Exercice exercice);
    public void clearRecherche();
    public void addExerciceRecherche(Exercice exercice);
    public void addCours(Cours cours);
    public void addExamen(Examen examen);
    
}