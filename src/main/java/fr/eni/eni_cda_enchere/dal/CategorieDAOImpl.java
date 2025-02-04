package fr.eni.eni_cda_enchere.dal;


import fr.eni.eni_cda_enchere.bo.Categorie;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class CategorieDAOImpl implements CategorieDAO {

    private NamedParameterJdbcTemplate jdbc;

    @Override
    public Categorie read(long no_categorie) {
        String sql = "SELECT * FROM Categorie WHERE no_categorie = :no_categorie";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", no_categorie);
        return jdbc.queryForObject(sql, namedParameters, Categorie.class);
    }

    @Override
    public List<Categorie> findAll() {
        String sql = "SELECT * FROM Categorie";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Categorie.class));
    }

    @Override
    public void createCategory(String libelle) {
        String sql = "INSERT INTO Categorie (libelle) VALUES (:libelle)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("libelle", libelle);
        jdbc.update(sql, namedParameters);
    }

    @Override
    public void updateCategory(Categorie cat) {
        String sql = "UPDATE Categorie SET libelle = :libelle WHERE no_categorie = :no_categorie";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("libelle", cat.getLibelle());
        namedParameters.addValue("no_categorie", cat.getId());
        jdbc.update(sql, namedParameters);
    }

    @Override
    public void deleteCategory(long no_categorie) {
        String sql = "DELETE FROM Categorie WHERE no_categorie = :no_categorie";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_categorie", no_categorie);
        jdbc.update(sql, namedParameters);
    }
}
