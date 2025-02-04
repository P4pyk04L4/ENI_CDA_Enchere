package fr.eni.eni_cda_enchere.bo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Enchere implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime date_enchere;
    private int montant;
    private String id_utilisateur;
    private long no_article;

    private Utilisateur acquereur;
    private ArticleAVendre articleAVendre;

    public String getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(String id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public long getNo_article() {
        return no_article;
    }

    public void setNo_article(long no_article) {
        this.no_article = no_article;
    }

    public Enchere() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Utilisateur getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Utilisateur acquereur) {
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
                ", id_utilisateur='" + id_utilisateur + '\'' +
                ", no_article=" + no_article +
                ", acquereur=" + acquereur +
                ", articleAVendre=" + articleAVendre +
                '}';
    }
}
