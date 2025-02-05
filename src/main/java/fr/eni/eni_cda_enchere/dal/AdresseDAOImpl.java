package fr.eni.eni_cda_enchere.dal;

import fr.eni.eni_cda_enchere.bo.Adresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdresseDAOImpl implements AdresseDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Adresse getAdresse(long id) {
        String sql = "select * from ADRESSES where no_adresse = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Adresse.class));
    }

    @Override
    public List<Adresse> getAdresses() {
        String sql = "select * from ADRESSES";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adresse.class));
    }

    @Override
    public int insertAdresse(Adresse adresse) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO ADRESSES (rue, code_postal, ville, adresse_eni) VALUES (:rue, :code_postal, :ville, :adresse_eni)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rue", adresse.getRue());
        params.addValue("code_postal", adresse.getCode_postal());
        params.addValue("ville", adresse.getVille());
        params.addValue("adresse_eni", 0);
        namedParameterJdbcTemplate.update(sql, params, keyHolder);

        if(keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return -1;
        }

    }

    @Override
    public void updateAdresse(Adresse adresse) {
        String sql = "Update ADRESSES SET rue = :rue, code_postal = :code_postal, ville = :ville, adresse_eni = :adresse_eni WHERE no_adresse = :no_adresse";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rue", adresse.getRue());
        params.addValue("code_postal", adresse.getCode_postal());
        params.addValue("ville", adresse.getVille());
        params.addValue("adresse_eni", 0);
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteAdresse(Adresse adresse) {
        String sql = "delete from ADRESSES where no_adresse = :no_adresse";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("no_adresse", adresse.getNo_adresse());
        namedParameterJdbcTemplate.update(sql, params);
    }
}
