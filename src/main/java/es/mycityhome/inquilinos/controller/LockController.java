package es.mycityhome.inquilinos.controller;

import es.mycityhome.inquilinos.dto.LockRequestDto;
import es.mycityhome.inquilinos.dto.LockResponseDto;
import es.mycityhome.inquilinos.service.WeLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 🚪 Controlador REST para la gestión de cerraduras
 * 
 * Este controlador expone endpoints para la operación de cerraduras inteligentes
 * a través de la integración con la API de We-Lock.
 */
@RestController
@RequestMapping("/api/lock")
public class LockController {

    @Autowired
    private WeLockService weLockService;

    /**
     * 🔑 Endpoint para abrir una cerradura
     * 
     * Este endpoint recibe una petición con el ID de la cerradura a abrir
     * y realiza la operación a través del servicio de We-Lock.
     * 
     * @param lockRequest DTO con la información de la cerradura a abrir
     * @return Respuesta HTTP con el resultado de la operación
     */
    @PostMapping("/open")
    public ResponseEntity<?> openLock(@Valid @RequestBody LockRequestDto lockRequest) {
        try {
            // Registramos el usuario que realiza la petición y la hora actual
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            
            // Si no se proporciona userId, utilizamos el username autenticado
            if (lockRequest.getUserId() == null || lockRequest.getUserId().isEmpty()) {
                lockRequest.setUserId(currentUsername);
            }
            
            // Si no se proporciona timestamp, lo generamos
            if (lockRequest.getTimestamp() == null || lockRequest.getTimestamp().isEmpty()) {
                lockRequest.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            }
            
            // Llamamos al servicio para abrir la cerradura
            LockResponseDto response = weLockService.openLock(lockRequest);
            
            // Si la operación fue exitosa, devolvemos 200, de lo contrario 400
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            LockResponseDto errorResponse = new LockResponseDto(
                false,
                "Error al procesar la petición: " + e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}