package fr.eni.eni_cda_enchere.bo.custom;

public class ArticleRequest {
    private int noCategorie;
    private String searchText;
    private boolean estAchat;
    private boolean estVente;
    private int critere;

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        if (searchText != null && searchText.trim().isEmpty()) {
            this.searchText = null;
        } else {
            this.searchText = searchText;
        }
    }

    public boolean isEstAchat() {
        return estAchat;
    }

    public void setEstAchat(boolean estAchat) {
        this.estAchat = estAchat;
    }

    public boolean isEstVente() {
        return estVente;
    }

    public void setEstVente(boolean estVente) {
        this.estVente = estVente;
    }

    public int getCritere() {
        return critere;
    }

    public void setCritere(int critere) {
        this.critere = critere;
    }
}