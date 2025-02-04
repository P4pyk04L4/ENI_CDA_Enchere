package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    private final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse) " +
            "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :credit, :admin, :noAdresse)";
    private final String FIND_ALL_UTILISATEUR = "SELECT * FROM UTILISATEURS";
    private final String FIND_BY_PSEUDO = "SELECT pseudo, nom, prenom, email, telephone, mot_de_passe, credit, administrateur, no_adresse " +
            "FROM UTILISATEURS " +
            "WHERE pseudo = :pseudo";
    private final String DELETE_BY_PSEUDO = "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo";
    private final String UPDATE_UTILISATEUR = """
    UPDATE UTILISATEURS 
    SET nom = :nom, 
        prenom = :prenom, 
        email = :email, 
        telephone = :telephone, 
        mot_de_passe = :motDePasse, 
        credit = :credit, 
        administrateur = :admin, 
        no_adresse = :noAdresse
    WHERE pseudo = :pseudo
""";


    public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void INSERT(Utilisateur utilisateur) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", utilisateur.getPseudo());
        params.addValue("nom", utilisateur.getNom());
        params.addValue("prenom", utilisateur.getPrenom());
        params.addValue("email", utilisateur.getEmail());
        params.addValue("telephone", utilisateur.getTelephone());
        params.addValue("motDePasse", utilisateur.getMotDePasse());
        params.addValue("credit", utilisateur.getCredit());
        params.addValue("admin", utilisateur.isAdmin());
     //   params.addValue("noAdresse", utilisateur.getAdresse() != null ? utilisateur.getAdresse().getNoAdresse() : null);

        namedParameterJdbcTemplate.update(INSERT, params);
    }


    @Override
    public Optional<Utilisateur> findByPseudo(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                FIND_BY_PSEUDO,namedParameters,new BeanPropertyRowMapper<>(Utilisateur.class)
        ));
    }

    @Override
    public List<Utilisateur> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL_UTILISATEUR, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    @Override
    public void UPDATE(Utilisateur utilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", utilisateur.getPseudo());
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("telephone", utilisateur.getTelephone());
        namedParameters.addValue("motDePasse", utilisateur.getMotDePasse());
        namedParameters.addValue("credit", utilisateur.getCredit());
        namedParameters.addValue("admin", utilisateur.isAdmin() ? 1 : 0);

        namedParameterJdbcTemplate.update(UPDATE_UTILISATEUR, namedParameters);
    }

    @Override
    public void DELETE(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        namedParameterJdbcTemplate.update(DELETE_BY_PSEUDO, namedParameters);
    }

}
