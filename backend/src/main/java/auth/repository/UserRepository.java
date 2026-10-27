package auth.repository;

import auth.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;
    public UserRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public Optional<Usuario> buscarPorIdentificador(String id) {
        return jdbc.query(
                "SELECT id, login, email, nome, senha_hash FROM usuario WHERE login = ? OR email = ?",
                (rs, n) -> new Usuario(rs.getLong("id"), rs.getString("login"),
                        rs.getString("email"), rs.getString("nome"), rs.getString("senha_hash")),
                id, id).stream().findFirst();
    }
}