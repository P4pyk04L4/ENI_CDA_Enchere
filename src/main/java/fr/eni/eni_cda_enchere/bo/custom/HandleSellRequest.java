package fr.eni.eni_cda_enchere.bo.custom;

public class HandleSellRequest {
    private String acquereur;
    private String vendeur;
    private int prix;
    private int no_article;

    public String getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(String acquereur) {
        this.acquereur = acquereur;
    }

    public String getVendeur() {
        return vendeur;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getNo_article() {
        return no_article;
    }

    public void setNo_article(int no_article) {
        this.no_article = no_article;
    }
}


