package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Categorie;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleAVendreDAOImpl implements ArticleAVendreDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ArticleAVendreDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public ArticleAVendre getArticleAVendre(int id) {
        String sql = "SELECT aav.no_article, aav.nom_article, aav.description, aav.photo, aav.date_debut_encheres, " +
                "aav.date_fin_encheres, aav.statut_enchere, aav.prix_initial, aav.prix_vente, " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit, " +
                "c.libelle, " +
                "a.rue, a.code_postal, a.ville, a.adresse_eni " +
                "FROM articles_a_vendre AS aav " +
                "LEFT JOIN utilisateurs AS u ON aav.id_utilisateur = u.pseudo " +
                "LEFT JOIN categories AS c ON aav.no_categorie = c.no_categorie " +
                "LEFT JOIN adresses AS a ON aav.no_adresse_retrait = a.no_adresse " +
                "WHERE aav.no_article = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> getAllArticleAVendre() {
        String sql = "SELECT aav.no_article, aav.nom_article, aav.description, aav.photo, aav.date_debut_encheres, " +
                "aav.date_fin_encheres, aav.statut_enchere, aav.prix_initial, aav.prix_vente, " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit, " +
                "c.libelle, " +
                "a.rue, a.code_postal, a.ville, a.adresse_eni " +
                "FROM articles_a_vendre AS aav " +
                "LEFT JOIN utilisateurs AS u ON aav.id_utilisateur = u.pseudo " +
                "LEFT JOIN categories AS c ON aav.no_categorie = c.no_categorie " +
                "LEFT JOIN adresses AS a ON aav.no_adresse_retrait = a.no_adresse ";
        return namedParameterJdbcTemplate.query(sql, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> getAllActiveArticleAVendre() {
        String sql = "SELECT aav.no_article, aav.nom_article, aav.description, aav.photo, aav.date_debut_encheres, " +
                "aav.date_fin_encheres, aav.statut_enchere, aav.prix_initial, aav.prix_vente, " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit, " +
                "c.libelle, " +
                "a.rue, a.code_postal, a.ville, a.adresse_eni " +
                "FROM articles_a_vendre AS aav " +
                "LEFT JOIN utilisateurs AS u ON aav.id_utilisateur = u.pseudo " +
                "LEFT JOIN categories AS c ON aav.no_categorie = c.no_categorie " +
                "LEFT JOIN adresses AS a ON aav.no_adresse_retrait = a.no_adresse " +
                "WHERE aav.statut_enchere = 1";
        return namedParameterJdbcTemplate.query(sql, new ArticleAVendreRowMapper());
    }

    @Override
    public List<ArticleAVendre> getFilteredArticleAVendre(int noCategorie, String nom) {
        String sql = "SELECT aav.no_article, aav.nom_article, aav.description, aav.photo, aav.date_debut_encheres, " +
                "aav.date_fin_encheres, aav.statut_enchere, aav.prix_initial, aav.prix_vente, " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit, " +
                "c.libelle, " +
                "a.rue, a.code_postal, a.ville, a.adresse_eni " +
                "FROM articles_a_vendre AS aav " +
                "LEFT JOIN utilisateurs AS u ON aav.id_utilisateur = u.pseudo " +
                "LEFT JOIN categories AS c ON aav.no_categorie = c.no_categorie " +
                "LEFT JOIN adresses AS a ON aav.no_adresse_retrait = a.no_adresse " +
                "WHERE 1=1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        if(noCategorie != 0){
            sql += " and c.no_categorie = :noCategorie";
            params.addValue("noCategorie", noCategorie);
        }
        if(nom != null){
            sql += " and u.nom = :nom";
            params.addValue("nom", nom);
        }
        return namedParameterJdbcTemplate.query(sql, params, new ArticleAVendreRowMapper());
    }

    @Override
    public void createArticleAVendre(ArticleAVendre articleAVendre) {
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
        params.addValue("id_utilisateur", articleAVendre.getVendeur().getPseudo());
        params.addValue("no_categorie", articleAVendre.getCategorie().getNo_categorie());
        params.addValue("no_adresse_retrait", articleAVendre.getRetrait().getNo_adresse());
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
        params.addValue("no_categorie", articleAVendre.getCategorie().getNo_categorie());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteArticleAVendre(int id) {
        String sql = "DELETE FROM ARTICLES_A_VENDRE WHERE no_article = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    static class ArticleAVendreRowMapper implements RowMapper<ArticleAVendre> {

        @Override
        public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleAVendre aav = new ArticleAVendre();
            aav.setNo_article(rs.getInt("no_article"));
            aav.setNom_article(rs.getString("nom_article"));
            aav.setDescription(rs.getString("description"));
            aav.setPhoto(rs.getString("photo"));
            aav.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
            aav.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
            aav.setStatut_enchere(rs.getInt("statut_enchere"));
            aav.setPrix_initial(rs.getInt("prix_initial"));
            aav.setPrix_vente(rs.getInt("prix_vente"));

            Utilisateur u = new Utilisateur();
            u.setPseudo(rs.getString("pseudo"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setTelephone(rs.getString("telephone"));
            u.setCredit(rs.getInt("credit"));
            aav.setVendeur(u);

            Categorie c = new Categorie();
            c.setLibelle(rs.getString("libelle"));
            aav.setCategorie(c);

            Adresse a = new Adresse();
            a.setRue(rs.getString("rue"));
            a.setCode_postal(rs.getString("code_postal"));
            a.setVille(rs.getString("ville"));
            aav.setRetrait(a);

            return aav;
        }
    }
}
