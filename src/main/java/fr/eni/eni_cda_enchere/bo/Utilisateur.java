package fr.eni.eni_cda_enchere.bo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Utilisateur implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String motDePasse;
    private int credit;
    private boolean admin;

    private Adresse adresse;

    private List<ArticleAVendre> ArticlesAVendre;
    private List<Enchere> listeEncheres;

    public Utilisateur() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<ArticleAVendre> getArticlesAVendre() {
        return ArticlesAVendre;
    }

    public void setArticlesAVendre(List<ArticleAVendre> articlesAVendre) {
        ArticlesAVendre = articlesAVendre;
    }

    public List<Enchere> getListeEncheres() {
        return listeEncheres;
    }

    public void setListeEncheres(List<Enchere> listeEncheres) {
        this.listeEncheres = listeEncheres;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "adresse=" + adresse +
                ", admin=" + admin +
                ", credit=" + credit +
                ", motDePasse='" + motDePasse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
