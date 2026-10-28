package auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    CommandLineRunner seed(JdbcTemplate jdbc, PasswordEncoder encoder) {
        return args -> jdbc.update(
                "INSERT OR IGNORE INTO usuario (login, email, nome, senha_hash) VALUES (?, ?, ?, ?)",
                "maria", "maria@exemplo.com", "Maria Souza", encoder.encode("senha123"));
    }
}