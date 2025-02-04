package fr.eni.eni_cda_enchere.bo;

import java.io.Serial;
import java.io.Serializable;

public class Adresse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Adresse() {
    }

    private long no_adresse;
    private String rue;
    private String code_postal;
    private String ville;
    private boolean adresse_eni;

    public long getNo_adresse() {
        return no_adresse;
    }

    public void setNo_adresse(long no_adresse) {
        this.no_adresse = no_adresse;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public boolean isAdresse_eni() {
        return adresse_eni;
    }

    public void setAdresse_eni(boolean adresse_eni) {
        this.adresse_eni = adresse_eni;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + no_adresse +
                ", rue='" + rue + '\'' +
                ", codePostal='" + code_postal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
