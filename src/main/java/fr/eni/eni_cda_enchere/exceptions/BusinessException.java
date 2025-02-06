package fr.eni.eni_cda_enchere.exceptions;

/*
Le but est de pouvoir avoir des erreurs customisées pour l'application selon le métier. Et avoir des messages dédiés avec
des couples clé-valeur avec des identifiants venant des paramètres des fichiers properties et des messages d'erreur customisés en fonction
de la langue de l'utilisateur.

 */

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    // Liste des clefs d'externalisation
    private List<String> clefsExternalisations;
    public BusinessException() {
        super();
    }
    public BusinessException(Throwable cause) {
        super(cause);
    }
    public List<String> getClefsExternalisations() {
        return clefsExternalisations;
    }
    /**
     * Permet d'ajouter une clef d'erreur
     *
     * @param clef
     *
     * @comportement initialise la liste si besoin
     */
    public void add(String clef) {
        if (clefsExternalisations == null) {
            clefsExternalisations = new ArrayList<>();
        }
        clefsExternalisations.add(clef);
    }
    /**
     * @return permet de confirmer si des erreurs ont été chargées
     */
    public boolean isValid() {
        return clefsExternalisations == null || clefsExternalisations.isEmpty();
    }
}
