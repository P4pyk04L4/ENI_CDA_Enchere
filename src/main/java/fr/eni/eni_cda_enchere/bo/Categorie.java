package fr.eni.eni_cda_enchere.bo;

import java.io.Serial;
import java.io.Serializable;

public class Categorie implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long no_categorie;
    private String libelle;

    public Categorie() {}

    public long getId() {
        return no_categorie;
    }

    public void setId(long no_categorie) {
        this.no_categorie = no_categorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "no_categorie =" + no_categorie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
