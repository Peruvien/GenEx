/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.File;
import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author Robin
 * @author Vincent
 */
public class Exercice implements Comparable<Exercice> {
    
    //ATTRIBUTS
    private Chapitre chapitreExercice;
    private final int idExercice;
    private boolean isIllustration;
    private int usageNumber;
    private int usedPlanche;
    private int usedExamen;
    private int numeroExercice;
    private Date latestUsage;
    private final Time duree;
    private int points;
    private final String libelle;
    private final File fichier;
    private String tags;



    //CONSTRUCTEUR
    private Exercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice,
                     String libelleExercice, String fichierExercicePath, String tags) {
        this.idExercice = idExercice;
        this.numeroExercice = numeroExercice;
        this.duree = dureeExercice;
        this.points = pointsExercice;
        this.libelle = libelleExercice;
        this.fichier = new File(fichierExercicePath);
        this.tags = tags;
        isIllustration = false;
        usageNumber = 0;
        usedPlanche = 0;
        usedExamen = 0;
        latestUsage = null;

    }
    public Exercice(int idExercice, int numeroExercice, Time dureeExercice, int pointsExercice, String libelleExercice,
                    String fichierExercicePath, String tags, Chapitre chapitre) {
        this(idExercice, numeroExercice, dureeExercice, pointsExercice, libelleExercice, fichierExercicePath, tags);
        this.chapitreExercice = chapitre;
    }
    
    
    //ACCESSEURS
    public Chapitre getChapitreExercice() {
        return chapitreExercice;
    }

    public int getNumero() {
        return numeroExercice;
    }
    
    public Time getDuree() {
        return duree;
    }
    
    public int getPoints() {
        return points;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public String getTags() {
        return tags;
    }
    
    public String getFichier() {
        return fichier.getAbsolutePath();
    }

    public int getUsedPlanche() {
        return usedPlanche;
    }

    public int getUsedExamen() {
        return usedExamen;
    }

    public Date getLatestUsage() {
        return latestUsage;
    }

    @Override
    public String toString() {
        String res = "";
        //TODO Gerer les 3 cas
        res += "Présentiel : ";
        if (chapitreExercice.getModeChapitre() < 2){
            res += "oui";
        }else{
            res += "non";
        }
        res += "\n";   
        //res += "Numéro de cours : " + chapitreExercice.getModeChapitre() + "\n";
        res += "Numéro d'exercice : " + numeroExercice + "\n";
        res += "Temps : " + duree + "\n";
        res += "Points : " + points + "\n";
        res += "Libellé : " + libelle + "\n";
        res += "Fichier : " + fichier.getAbsolutePath();
        return res;
    }

    public int getIdExercice() {
        return idExercice;
    }

    public boolean isIllustration() {
        return isIllustration;
    }

    public int getUsageNumber() {
        return usageNumber;
    }

    public int getNumeroExercice() {
        return numeroExercice;
    }

    //MUTATEURS
    public void setLatestUsage(Date date){
        //TODO Verifier le sens de la comparaison
        if (this.latestUsage.compareTo(date) < 0){
            this.latestUsage = date;
        }
    }
    //TODO Faire en arraylist pour ajouter ou supprimer des tags
    //les tags sont déjà une liste de mots
    public void setTags(String tags){
        this.tags = tags;
    }

    public void setIllustration(boolean illustration) {
        //A priori, ce test ne devrait jamais fonctionner, mais on n'est jamais trop prudent...
        if(isIllustration == illustration){
            return;
        }
        this.usageNumber += illustration? 1:-1;
        isIllustration = illustration;
    }

    public void addUsageExamen(){
        this.usedExamen++;
        this.usageNumber++;
    }

    public void substractUsageExamen(){
        this.usedExamen--;
        this.usedExamen--;
    }
    public void addUsagePlanche(){
        this.usedPlanche++;
        this.usageNumber++;
    }

    public void substractUsagePlanche(){
        this.usedPlanche--;
        this.usageNumber--;
    }
    
    //COMPARABLE
    @Override
    public boolean equals(Object o) {
        if (o instanceof Exercice) {
            return ((Exercice)o).compareTo(this) == 0;
        }
        return false;
    }
    
    @Override
    public int compareTo(Exercice o) {
        return idExercice - o.idExercice;
    }
}
