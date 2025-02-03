package fr.eni.eni_cda_enchere.configuration.security;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class ENEScurityConfig {

    protected Log logger = LogFactory.getLog(ENEScurityConfig.class);

    @Bean
    UserDetailsManager userDetailsManager(DataSource ds) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(ds);

        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM utilisateurs WHERE pseudo=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role FROM roles WHERE pseudo=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/*").permitAll();
            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/images/*").permitAll();
            auth.anyRequest().denyAll();
        });

        http.formLogin(formLogin -> {
            formLogin.loginPage("/login").permitAll();
            formLogin.defaultSuccessUrl("/");
        });

        http.logout(logout -> {
            logout
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll();
        });

        return http.build();
    }
}
