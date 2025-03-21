package es.mycityhome.inquilinos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * 🔐 DTO para peticiones de apertura de cerraduras
 * 
 * Esta clase define la estructura de datos necesaria para realizar
 * una solicitud de apertura de cerradura a la API de We-Lock.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockRequestDto {
    /**
     * Identificador único de la cerradura a abrir
     */
    @NotBlank(message = "El ID de la cerradura es obligatorio")
    private String lockId;
    
    /**
     * Identificador del usuario que realiza la petición
     */
    private String userId;
    
    /**
     * Marca de tiempo de la solicitud
     */
    private String timestamp;
}