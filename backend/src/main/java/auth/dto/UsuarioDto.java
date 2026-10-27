package auth.dto;

public record UsuarioDto(
        long id,
        String login,
        String email,
        String nome,
        String senhaHash
) {}