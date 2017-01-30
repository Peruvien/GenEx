/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import bdd.Examen;

/**
 *
 * @author robin
 */
public class ExamenList implements Informations {
    
    //ATTRIBUTS
    private final Examen examen;
    
    
    //CONSTRUCTEUR
    public ExamenList(Examen examen) {
        this.examen = examen;
    }
    
    
    //ACCESSEURS
    @Override
    public String toString() {
        return "Examen n°" + examen.getID();
    }
    
    
    //MUTAEURS
    
    
    //INFORMATIONS
    @Override
    public String getInformations() {
        return examen.toString();
    }
    
}
