package fr.eni.eni_cda_enchere.bo;

import fr.eni.eni_cda_enchere.validations.DateComparer;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@DateComparer
public class ArticleAVendre implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private int no_article;

    @NotBlank(message = "Veuillez entrer un nom d'article.")
    @Size(min = 5, max = 30, message = "Le nom doit être compris entre 5 et 30 caractères.")
    private String nom_article;

    @NotBlank(message = "Veuillez entrer une description pour votre vente.")
    @Size(min = 5, max = 300, message = "Vous devez entrer une description comportant plus de 5 caractères et ne devant pas excéder 300 caractères.")
    private String description;

    private String photo;

    @NotNull(message = "Veuillez indiquer une date de début d'enchère.")
    @FutureOrPresent
    private LocalDate date_debut_encheres;

    @Future
    @NotNull(message = "Veuillez indiquer une date de fin d'enchère.")
    private LocalDate date_fin_encheres;

    private int statut_enchere;

    @NotNull(message = "Veuillez indiquer un prix de vente.")
    private int prix_initial;

    private Integer prix_vente;

    private Integer meilleure_offre;

    private Adresse retrait;

    private Utilisateur vendeur;

    private Categorie categorie;

    public ArticleAVendre() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getDate_debut_encheres() {
        return date_debut_encheres;
    }

    public void setDate_debut_encheres(LocalDate date_debut_encheres) {
        this.date_debut_encheres = date_debut_encheres;
    }

    public LocalDate getDate_fin_encheres() {
        return date_fin_encheres;
    }

    public void setDate_fin_encheres(LocalDate date_fin_encheres) {
        this.date_fin_encheres = date_fin_encheres;
    }

    public int getStatut_enchere() {
        return statut_enchere;
    }

    public void setStatut_enchere(int statut_enchere) {
        this.statut_enchere = statut_enchere;
    }

    public int getPrix_initial() {
        return prix_initial;
    }

    public void setPrix_initial(int prix_initial) {
        this.prix_initial = prix_initial;
    }

    public Integer getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(Integer prix_vente) {
        this.prix_vente = prix_vente;
    }

    public Integer getMeilleure_offre() {
        return meilleure_offre;
    }

    public void setMeilleure_offre(Integer meilleure_offre) {
        this.meilleure_offre = meilleure_offre;
    }

    public Adresse getRetrait() {
        return retrait;
    }

    public void setRetrait(Adresse retrait) {
        this.retrait = retrait;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "ArticleAVendre{" +
                "id=" + no_article +
                ", nom='" + nom_article + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + date_debut_encheres +
                ", dateFinEncheres=" + date_fin_encheres +
                ", statut=" + statut_enchere +
                ", prixInitial=" + prix_initial +
                ", prixVente=" + prix_vente +
                ", meilleureOffre=" + meilleure_offre +
                ", retrait=" + retrait +
                ", vendeur=" + vendeur.getPseudo() +
                ", categorie=" + categorie.getLibelle() +
                '}';
    }
}
