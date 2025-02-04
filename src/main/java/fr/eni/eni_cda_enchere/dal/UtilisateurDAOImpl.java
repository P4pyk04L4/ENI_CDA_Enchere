package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    String sql = "SELECT * FROM UTILISATEURS";

    public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(Utilisateur utilisateur) {

    }

    @Override
    public Optional<Utilisateur> findByPseudo(String pseudo) {
        return Optional.empty();
    }

    @Override
    public List<Utilisateur> findAll() {
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public void delete(String pseudo) {

    }
}
