package auth.model;

public record Usuario(
        long id,
        String login,
        String email,
        String nome,
        String senhaHash
) {}