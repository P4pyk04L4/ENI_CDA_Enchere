package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.dal.AdresseDAO;
import fr.eni.eni_cda_enchere.dal.UtilisateurDAO;
import fr.eni.eni_cda_enchere.exceptions.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurDAO utilisateurDAO;
    private final AdresseDAO adresseDAO;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO, PasswordEncoder passwordEncoder) {
        this.adresseDAO = adresseDAO;
        this.utilisateurDAO = utilisateurDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(Utilisateur user) {
        BusinessException be = new BusinessException();
        boolean isValid = true;
        isValid &= validerUtilisateur(user, be);
        isValid &= validerPseudo(user.getPseudo(), be);
        isValid &= validerEmail(user.getEmail(), be);
        isValid &= validerTel(user.getTelephone(), be);
        isValid &= validerAdresse(user.getAdresse(), be);
        if (isValid) {
            utilisateurDAO.create(user);
        } else {
            be.add("Erreur lors de la création d'un nouvel utilisateur");
            throw be;
        }

    }

    @Override
    public Optional<Utilisateur> findByPseudo(String pseudo) {
        return utilisateurDAO.findByPseudo(pseudo);
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurDAO.findAll();
    }

    @Override
    public void updateUser(Utilisateur user) {
        utilisateurDAO.update(user);
    }

    @Override
    @Transactional
    public void updateByUser(Utilisateur user) {
        int noAdress = 0;

        // Vérification du numéro d'adresse
        boolean isNewAdress = false;
        Adresse oldAdress = adresseDAO.getAdresse(user.getAdresse().getNo_adresse());
        if(oldAdress != null) {
            if(!oldAdress.getRue().equals(user.getAdresse().getRue())
            || !oldAdress.getCode_postal().equals(user.getAdresse().getCode_postal())
            || !oldAdress.getVille().equals(user.getAdresse().getVille())) {
                isNewAdress = true;
            }
        }
        // Création si besoin
        if(isNewAdress) {
           noAdress = adresseDAO.insertAdresse(user.getAdresse());
           user.getAdresse().setNo_adresse(noAdress);
        }

        // Update par l'utilisateur
        utilisateurDAO.updateByUser(user);
    }

    @Override
    public void updatePassword(Utilisateur utilisateur) {
        utilisateurDAO.updatePassword(utilisateur);
    }

    @Override
    public void deleteUser(String pseudo) {
        utilisateurDAO.delete(pseudo);
    }


    /**
     * Méthodes de validation des BO
     */

    private boolean validerUtilisateur(Utilisateur u, BusinessException be) {
        if (u == null){
            be.add("Utilisateur null.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerPseudo(String pseudo, BusinessException be) {
        Optional<Utilisateur> user = utilisateurDAO.findByPseudo(pseudo);
        String regex = "^[a-zA-Z0-9]+$\n";
        Pattern p =  Pattern.compile(regex);

        if (user.isPresent()) {
            be.add("Pseudo non disponible.");
            return false;
        } else if (!p.matcher(pseudo).matches()){
            be.add("Le pseudo ne contient pas que des caractères alphanumériques.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerEmail(String email, BusinessException be) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regex);

        if (!p.matcher(email).matches()) {
            be.add("Email non valide.") ;
            return false;
        } else {
            return true;
        }
    }

    private boolean validerPwd(String pwd, BusinessException be) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z\\d@#$%^&+=!]).{8,20}$\n";
        Pattern p = Pattern.compile(regex);

        if (!p.matcher(pwd).matches()) {
            be.add("Password non valide.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerTel(String tel, BusinessException be) {
        String regex = "^\\d+$\n";
        Pattern p = Pattern.compile(regex);

        if (!p.matcher(tel).matches()) {
            be.add("Le numéro de téléphone ne doit contenir que des chiffres.");
            return false;
        } else {
            return true;
        }
    }

    private boolean validerAdresse(Adresse adresse, BusinessException be) {
        if (adresse == null) {
            be.add("Adresse null.");
            return false;
        } else {
            return true;
        }
    }


}
