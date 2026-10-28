package auth.controller;

import auth.exception.AuthenticationException;
import auth.model.ErrorResponse;
import auth.model.LoginRequest;
import auth.model.LoginResponse;
import auth.model.UserIdentity;
import auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        UserIdentity u = authService.autenticar(req.identificador(), req.senha());
        return ResponseEntity.ok(new LoginResponse(u.id(), u.nome()));
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<ErrorResponse> credenciaisInvalidas(AuthenticationException e) {
        return ResponseEntity.status(401).body(new ErrorResponse("Credenciais inválidas"));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    ResponseEntity<ErrorResponse> requisicaoInvalida(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("Requisição inválida"));
    }
}