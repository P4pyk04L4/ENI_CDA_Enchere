package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.dal.AdresseDAO;
import fr.eni.eni_cda_enchere.dal.UtilisateurDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        utilisateurDAO.create(user);
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
}
