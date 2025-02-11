package fr.eni.eni_cda_enchere.bo.custom;

public class ArticleRequest {
    private int noCategorie;
    private String searchText;

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
}