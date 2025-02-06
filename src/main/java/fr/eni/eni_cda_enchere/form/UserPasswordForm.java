package fr.eni.eni_cda_enchere.form;

import jakarta.validation.constraints.NotBlank;

public class UserPasswordForm {
    @NotBlank
    private String motDePasseActuel;

    @NotBlank
    private String motDePasseNouveau;

    @NotBlank
    private String motDePasseConfirmation;

    public String getMotDePasseActuel() {
        return motDePasseActuel;
    }

    public void setMotDePasseActuel(String motDePasseActuel) {
        this.motDePasseActuel = motDePasseActuel;
    }

    public String getMotDePasseNouveau() {
        return motDePasseNouveau;
    }

    public void setMotDePasseNouveau(String motDePasseNouveau) {
        this.motDePasseNouveau = motDePasseNouveau;
    }

    public String getMotDePasseConfirmation() {
        return motDePasseConfirmation;
    }

    public void setMotDePasseConfirmation(String motDePasseConfirmation) {
        this.motDePasseConfirmation = motDePasseConfirmation;
    }
}
