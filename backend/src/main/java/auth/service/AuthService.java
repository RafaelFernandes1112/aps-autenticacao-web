package auth.service;

import auth.exception.AuthenticationException;
import auth.model.UserIdentity;
import auth.model.Usuario;
import auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final String hashFicticio;

    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
        this.hashFicticio = encoder.encode("senha-ficticia");
    }

    public UserIdentity autenticar(String identificador, String senha) {
        Optional<Usuario> u = repo.buscarPorIdentificador(normalizar(identificador));
        if (u.isEmpty()) {
            encoder.matches(senha, hashFicticio);   // equaliza o tempo de resposta
            throw new AuthenticationException();
        }
        if (!encoder.matches(senha, u.get().senhaHash())) throw new AuthenticationException();
        return new UserIdentity(u.get().id(), u.get().nome());
    }

    private String normalizar(String s) { return s.trim().toLowerCase(Locale.ROOT); }
}