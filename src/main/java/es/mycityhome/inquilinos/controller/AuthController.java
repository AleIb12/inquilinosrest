package es.mycityhome.inquilinos.controller;

import es.mycityhome.inquilinos.dto.AuthRequestDto;
import es.mycityhome.inquilinos.dto.AuthResponseDto;
import es.mycityhome.inquilinos.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * 🔐 Controlador para gestionar la autenticación de usuarios
 * 
 * Este controlador proporciona endpoints para que los usuarios puedan
 * autenticarse y obtener tokens JWT necesarios para acceder a los
 * recursos protegidos del sistema.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 🔑 Endpoint para iniciar sesión y obtener token JWT
     * 
     * @param authRequest DTO con las credenciales de usuario (username y password)
     * @return Token JWT válido si la autenticación es exitosa
     * @throws Exception Si las credenciales son inválidas o el usuario está deshabilitado
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthRequestDto authRequest) throws Exception {
        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
            
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails.getUsername());
            
            return ResponseEntity.ok(new AuthResponseDto(token, userDetails.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error de autenticación: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para autenticar al usuario con las credenciales proporcionadas
     * 
     * @param username Nombre de usuario
     * @param password Contraseña
     * @throws Exception Si ocurre un error durante la autenticación
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("Usuario deshabilitado", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales incorrectas", e);
        }
    }
}