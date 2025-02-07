package fr.eni.eni_cda_enchere.dal;


import fr.eni.eni_cda_enchere.bo.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public Categorie read(long no_categorie) {
        String sql = "SELECT * FROM Categories WHERE no_categorie = :no_categorie";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", no_categorie);
        return jdbc.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public List<Categorie> findAll() {
        String sql = "SELECT * FROM Categories";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public void createCategory(Categorie cat) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO Categories (libelle) VALUES (:libelle)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("libelle", cat.getLibelle());
        jdbc.update(sql, namedParameters);

        if(keyHolder.getKey() != null) {
            cat.setNo_categorie(keyHolder.getKey().intValue());
        }
    }

    @Override
    public void updateCategory(Categorie cat) {
        String sql = "UPDATE Categories SET libelle = :libelle WHERE no_categorie = :no_categorie";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("libelle", cat.getLibelle());
        namedParameters.addValue("no_categorie", cat.getNo_categorie());
        jdbc.update(sql, namedParameters);
    }

    @Override
    public void deleteCategory(long no_categorie) {
        String sql = "DELETE FROM Categories WHERE no_categorie = :no_categorie";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", no_categorie);
        jdbc.update(sql, namedParameters);
    }
}
