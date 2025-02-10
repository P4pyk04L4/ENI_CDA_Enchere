package fr.eni.eni_cda_enchere.bo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

public class Enchere implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime date_enchere;
    private int montant;
    private Optional<Utilisateur> acquereur;
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
}
