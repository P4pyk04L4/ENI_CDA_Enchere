package fr.eni.eni_cda_enchere.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Utilisateur implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Le champ ne doit pas être null.")
    @Size(min = 1, max = 30, message = "Le pseudo doit faire entre 1 et 30 caractères.")
    private String pseudo;
    @NotBlank(message = "Le champ ne doit pas être null.")
    @Size(min = 1, max = 40, message = "Le nom doit faire entre 1 et 40 caractères.")
    private String nom;
    @NotBlank(message = "Le champ ne doit pas être null.")
    @Size(min = 1, max = 50, message = "Le prénom doit faire entre 1 et 50 caractères.")
    private String prenom;
    @NotBlank(message = "Le champ ne doit pas être null.")
    private String email;
    @NotBlank(message = "Le champ ne doit pas être null.")
    private String telephone;
    @NotBlank(message = "Le champ ne doit pas être null.")
    private String mot_de_passe;
    private int credit;
    private boolean admin;
    @NotNull
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
        return mot_de_passe;
    }

    public void setMotDePasse(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
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
                ", motDePasse='" + mot_de_passe + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
