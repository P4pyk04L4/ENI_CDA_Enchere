package fr.eni.eni_cda_enchere.configuration.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class CustomPasswordEncoder {
    public static PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder()); // Définit bcrypt comme encodage par défaut

        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
