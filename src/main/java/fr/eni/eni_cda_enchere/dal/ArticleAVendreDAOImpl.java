package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleAVendreDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ArticleAVendre getArticleAVendre(int id) {
        String sql = "select * from ARTICLES_A_VENDRE where no_article = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public List<ArticleAVendre> getAllArticleAVendre() {
        String sql = "select * from ARTICLES_A_VENDRE";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public List<ArticleAVendre> getAllActiveArticleAVendre() {
        String sql = "select * from ARTICLES_A_VENDRE where statut_enchere = 1";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public List<ArticleAVendre> getFilteredArticleAVendre(int noCategorie, String nom) {
        String sql = "select * from ARTICLES_A_VENDRE where 1=1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if(noCategorie != 0){
            sql += " and no_categorie = :noCategorie";
            params.addValue("noCategorie", noCategorie);
        }
        if(nom != null){
            sql += " and nom = :nom";
            params.addValue("nom", nom);
        }
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ArticleAVendre.class));
    }

    @Override
    public void createArticleAVendre(ArticleAVendre articleAVendre, Utilisateur utilisateur, Adresse adresse) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO ARTICLES_A_VENDRE (nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, prix_vente, id_utilisateur, no_categorie, no_adresse_retrait) VALUES (:nom_article, :description, :date_debut_encheres,:date_fin_encheres, :statut_enchere, :prix_initial, :prix_vente, :id_utilisateur, :no_categorie, :no_adresse_retrait)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nom_article", articleAVendre.getNom_article());
        params.addValue("description", articleAVendre.getDescription());
        params.addValue("date_debut_encheres", articleAVendre.getDate_debut_encheres());
        params.addValue("date_fin_encheres", articleAVendre.getDate_fin_encheres());
        params.addValue("statut_enchere", articleAVendre.getStatut_enchere());
        params.addValue("prix_initial", articleAVendre.getPrix_initial());
        params.addValue("prix_vente", articleAVendre.getPrix_vente());
        params.addValue("id_utilisateur", utilisateur.getPseudo());
        params.addValue("no_categorie", articleAVendre.getCategorie().getId());
        params.addValue("adresse_retrait", adresse.getNo_adresse());
        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        if(keyHolder.getKey() != null) {
            articleAVendre.setNo_article(keyHolder.getKey().intValue());
        }
    }

    @Override
    public void updateArticleAVendre(ArticleAVendre articleAVendre) {
        String sql = "UPDATE ARTICLES_A_VENDRE SET " +
                "nom_article = :nom_article, " +
                "description = :description, " +
                "date_debut_encheres = :date_debut_encheres, " +
                "date_fin_encheres = :date_fin_encheres, " +
                "statut_enchere = :statut_enchere, " +
                "prix_initial = :prix_initial, " +
                "prix_vente = :prix_vente, " +
                "id_utilisateur = :id_utilisateur, " +
                "no_categorie = :no_categorie, " +
                "no_adresse_retrait = :no_adresse_retrait";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nom_article", articleAVendre.getNom_article());
        params.addValue("description", articleAVendre.getDescription());
        params.addValue("date_debut_encheres", articleAVendre.getDate_debut_encheres());
        params.addValue("date_fin_encheres", articleAVendre.getDate_fin_encheres());
        params.addValue("statut_enchere", articleAVendre.getStatut_enchere());
        params.addValue("prix_initial", articleAVendre.getPrix_initial());
        params.addValue("prix_vente", articleAVendre.getPrix_vente());
        params.addValue("id_utilisateur", articleAVendre.getVendeur().getPseudo());
        params.addValue("no_categorie", articleAVendre.getCategorie().getId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteArticleAVendre(int id) {
        String sql = "DELETE FROM ARTICLES_A_VENDRE WHERE no_article = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
