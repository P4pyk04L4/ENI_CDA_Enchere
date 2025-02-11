package fr.eni.eni_cda_enchere.form;

import fr.eni.eni_cda_enchere.bll.CategorieService;
import fr.eni.eni_cda_enchere.bo.Categorie;

public class ArticlesFilterForm {

    private String nomArticle;

    private Categorie categorie;

    public ArticlesFilterForm() {
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setNoCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
