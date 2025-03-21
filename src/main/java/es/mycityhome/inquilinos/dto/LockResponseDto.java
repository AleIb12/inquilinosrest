package es.mycityhome.inquilinos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 📝 DTO para respuestas de operaciones con cerraduras
 * 
 * Esta clase encapsula la respuesta devuelta por el sistema
 * tras realizar operaciones en cerraduras inteligentes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockResponseDto {
    /**
     * Indica si la operación fue exitosa
     */
    private boolean success;
    
    /**
     * Mensaje descriptivo del resultado de la operación
     */
    private String message;
    
    /**
     * Marca de tiempo de la respuesta
     */
    private String timestamp;
}