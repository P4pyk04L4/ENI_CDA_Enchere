package fr.eni.eni_cda_enchere.bo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Enchere implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDateTime date_enchere;
    @NotNull
    @Min( value = 1, message = "Vous devez entrer une valeur supérieure à 0 !")
    private int montant;
    @NotNull
    private Optional<Utilisateur> acquereur;
    @NotNull
    private ArticleAVendre articleAVendre;


    public Enchere() {
    }

    public LocalDateTime getDate() {
        return date_enchere;
    }

    public void setDate(LocalDateTime date) {
        this.date_enchere = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Optional<Utilisateur> getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Optional<Utilisateur> acquereur) {
        this.acquereur = acquereur;
    }

    public ArticleAVendre getArticleAVendre() {
        return articleAVendre;
    }

    public void setArticleAVendre(ArticleAVendre articleAVendre) {
        this.articleAVendre = articleAVendre;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "date=" + date_enchere +
                ", montant=" + montant +
                ", acquereur=" + acquereur.toString() +
                ", articleAVendre=" + articleAVendre.toString() +
                '}';
    }

    public LocalDateTime getDate_enchere() {
        return date_enchere;
    }

    public void setDate_enchere(LocalDateTime date_enchere) {
        this.date_enchere = date_enchere;
    }

}
