package auth.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository  {

    private final JdbcTemplate jdbc;
    public UsuarioRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }
}