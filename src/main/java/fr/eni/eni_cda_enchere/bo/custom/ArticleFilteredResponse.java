package fr.eni.eni_cda_enchere.bo.custom;

import java.time.LocalDate;

public class ArticleFilteredResponse {
    int no_article;
    String nom_article;
    int prix_initial;
    int meilleure_offre;
    LocalDate date_fin_encheres;
    String pseudo;
    long no_categorie;
    String chemin_vendeur;

    public ArticleFilteredResponse(int no_article ,String nom_article,
                                   int prix_initial,
                                   int meilleure_offre,
                                   LocalDate date_fin_encheres,
                                   String pseudo,
                                   long no_categorie,
                                   boolean estAuthentifie) {
        this.no_article = no_article;
        this.nom_article = nom_article;
        this.prix_initial = prix_initial;
        this.meilleure_offre = meilleure_offre;
        this.date_fin_encheres = date_fin_encheres;
        this.pseudo = pseudo;
        this.no_categorie = no_categorie;
        if(estAuthentifie){
            this.chemin_vendeur = estAuthentifie ? "/utilisateurs/public/profil/" + pseudo : null;
        }
    }

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }

    public String getNom_article() {
        return nom_article;
    }

    public void setNom_article(String nom_article) {
        this.nom_article = nom_article;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public int getMeilleure_offre() {
        return meilleure_offre;
    }

    public void setMeilleure_offre(int meilleure_offre) {
        this.meilleure_offre = meilleure_offre;
    }

    public LocalDate getDate_fin_encheres() {
        return date_fin_encheres;
    }

    public void setDate_fin_encheres(LocalDate date_fin_encheres) {
        this.date_fin_encheres = date_fin_encheres;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public long getNo_categorie() {
        return no_categorie;
    }

    public void setNo_categorie(long no_categorie) {
        this.no_categorie = no_categorie;
    }

    public String getChemin_vendeur() {
        return chemin_vendeur;
    }

    public void setChemin_vendeur(String chemin_vendeur) {
        this.chemin_vendeur = chemin_vendeur;
    }
}
