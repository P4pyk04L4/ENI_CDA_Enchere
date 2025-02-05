package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import fr.eni.eni_cda_enchere.bo.Enchere;
import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public Enchere read(String id_utilisateur, long no_article, int montant_enchere) {
        String sql = "SELECT e.id_utilisateur, e.no_article, e.montant_enchere, e.date_enchere, " +
                "a.nom_article, a.description, a.photo, a.date_debut_encheres, a.date_fin_encheres, " +
                "a.statut_encheres, a.prix_initial, a.prix_vente " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit " +
                "FROM Enchere AS e " +
                "LEFT JOIN Articles_a_vendre AS a ON e.no_article = a.no_article " +
                "LEFT JOIN Utilisateurs AS u ON e.id_utilisateur = u.pseudo " +
                "WHERE e.id_utilisateur = :id_utilisateur AND e.no_article = :no_article AND e.montant_enchere = :montant_enchere";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        namedParameters.addValue("no_article", no_article);
        namedParameters.addValue("montant", montant_enchere);
        return jdbc.queryForObject(sql, namedParameters, new EnchereRowMapper());
    }

    @Override
    public List<Enchere> readAllFromUserAndArticle(String id_utilisateur, long no_article) {
        String sql = "SELECT e.id_utilisateur, e.no_article, e.montant_enchere, e.date_enchere, " +
                "a.nom_article, a.description, a.photo, a.date_debut_encheres, a.date_fin_encheres, " +
                "a.statut_encheres, a.prix_initial, a.prix_vente " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit " +
                "FROM Enchere AS e " +
                "LEFT JOIN Articles_a_vendre AS a ON e.no_article = a.no_article " +
                "LEFT JOIN Utilisateurs AS u ON e.id_utilisateur = u.pseudo " +
                "WHERE e.id_utilisateur = :id_utilisateur AND e.no_article = :no_article";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        namedParameters.addValue("id_article", no_article);
        return jdbc.query(sql, namedParameters , new EnchereRowMapper());
    }

    @Override
    public List<Enchere> readAllFromUser(String id_utilisateur) {
        String sql = "SELECT e.id_utilisateur, e.no_article, e.montant_enchere, e.date_enchere, " +
                "a.nom_article, a.description, a.photo, a.date_debut_encheres, a.date_fin_encheres, " +
                "a.statut_encheres, a.prix_initial, a.prix_vente " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit " +
                "FROM Enchere AS e " +
                "LEFT JOIN Articles_a_vendre AS a ON e.no_article = a.no_article " +
                "LEFT JOIN Utilisateurs AS u ON e.id_utilisateur = u.pseudo " +
                "WHERE e.id_utilisateur = :id_utilisateur";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", id_utilisateur);
        return jdbc.query(sql, namedParameters, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public List<Enchere> readAllFromArticle(long no_article) {
        String sql = "SELECT e.id_utilisateur, e.no_article, e.montant_enchere, e.date_enchere, " +
                "a.nom_article, a.description, a.photo, a.date_debut_encheres, a.date_fin_encheres, " +
                "a.statut_encheres, a.prix_initial, a.prix_vente " +
                "u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.credit " +
                "FROM Enchere AS e " +
                "LEFT JOIN Articles_a_vendre AS a ON e.no_article = a.no_article " +
                "LEFT JOIN Utilisateurs AS u ON e.id_utilisateur = u.pseudo " +
                "WHERE e.no_article = :no_article";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_article", no_article);
        return jdbc.query(sql, namedParameters, new BeanPropertyRowMapper<>(Enchere.class));
    }

    @Override
    public void create(Enchere enchere) {
        String sql = "INSERT INTO enchere(id_utilisateur, id_article, montant, date_enchere) " +
                "VALUES (:id_utilisateur, :no_article, :montant, :date_enchere)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id_utilisateur", enchere.getAcquereur().getPseudo());
        namedParameters.addValue("no_article", enchere.getArticleAVendre().getNo_article());
        namedParameters.addValue("montant", enchere.getMontant());
        namedParameters.addValue("date_enchere", enchere.getDate());
        jdbc.update(sql, namedParameters);
    }

    static class EnchereRowMapper implements RowMapper<Enchere> {
        @Override
        public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enchere e = new Enchere();
            e.setDate(rs.getObject("date_enchere", LocalDateTime.class));
            e.setMontant(rs.getInt("montant_enchere"));

            ArticleAVendre a = new ArticleAVendre();
            a.setNo_article(rs.getInt("no_article"));
            a.setDescription(rs.getString("description"));
            a.setPhoto(rs.getString("photo"));
            a.setDate_debut_encheres(rs.getDate("date_debut_encheres").toLocalDate());
            a.setDate_fin_encheres(rs.getDate("date_fin_encheres").toLocalDate());
            a.setStatut_enchere(rs.getInt("statut_enchere"));
            a.setPrix_initial(rs.getInt("prix_initial"));
            a.setPrix_vente(rs.getInt("prix_vente"));
            e.setArticleAVendre(a);

            Utilisateur u = new Utilisateur();
            u.setPseudo(rs.getString("pseudo"));
            u.setPrenom(rs.getString("prenom"));
            u.setNom(rs.getString("nom"));
            u.setEmail(rs.getString("email"));
            u.setTelephone(rs.getString("telephone"));
            u.setCredit(rs.getInt("credit"));
            e.setAcquereur(u);

            return e;
        }
    }
}
