package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Enchere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public Enchere read(String id_utilisateur, long no_article, int montant) {
        String sql = "SELECT * FROM Enchere WHERE id_utilisateur = ? AND id_article = ? AND montant = ?";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        namedParameters.addValue("id_article", no_article);
        namedParameters.addValue("montant", montant);
        return jdbc.queryForObject(sql, namedParameters, Enchere.class);
    }

    @Override
    public List<Enchere> readAllFromUserAndArticle(String id_utilisateur, long no_article) {
        String sql = "SELECT * FROM Enchere WHERE id_utilisateur = ? AND id_article = ?";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        namedParameters.addValue("id_article", no_article);
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> readAllFromUser(String id_utilisateur) {
        String sql = "SELECT * FROM Enchere WHERE id_utilisateur = ?";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        return jdbc.query(sql, namedParameters, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> readAllFromArticle(long no_article) {
        String sql = "SELECT * FROM Enchere WHERE id_article = ?";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_article", no_article);
        return jdbc.query(sql, namedParameters, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public void create(Enchere enchere) {
        String sql = "INSERT INTO enchere(id_utilisateur, id_article, montant, date_enchere) VALUES (?, ?, ?)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", enchere.getId_utilisateur());
        namedParameters.addValue("no_article", enchere.getNo_article());
        namedParameters.addValue("montant", enchere.getMontant());
        namedParameters.addValue("date_enchere", enchere.getDate());
        jdbc.update(sql, namedParameters);
    }
}
