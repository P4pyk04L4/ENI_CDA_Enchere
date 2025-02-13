package fr.eni.eni_cda_enchere.validations;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateComparerValidator implements ConstraintValidator<DateComparer, ArticleAVendre> {

    @Override
    public void initialize(DateComparer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ArticleAVendre articleAVendre, ConstraintValidatorContext constraintValidatorContext) {
        if (articleAVendre == null) {
            return true;
        }

        LocalDate dateDebut = articleAVendre.getDate_debut_encheres();
        LocalDate dateFin = articleAVendre.getDate_fin_encheres();

        if (dateDebut != null && dateFin != null) {
            return dateFin.isAfter(dateDebut);
        }

        return true;
    }
}
