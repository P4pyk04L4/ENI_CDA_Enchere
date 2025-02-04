package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Utilisateur;
import fr.eni.eni_cda_enchere.dal.UtilisateurDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
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
    public void deleteUser(String pseudo) {
        utilisateurDAO.delete(pseudo);
    }
}
