package es.mycityhome.inquilinos.service;

import es.mycityhome.inquilinos.config.WeLockConfig;
import es.mycityhome.inquilinos.dto.LockRequestDto;
import es.mycityhome.inquilinos.dto.LockResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 🔒 Servicio para interactuar con la API de We-Lock
 * 
 * Este servicio se encarga de realizar las comunicaciones con la API externa
 * de We-Lock para controlar cerraduras inteligentes.
 */
@Service
public class WeLockService {

    @Autowired
    private WeLockConfig weLockConfig;
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * 🔓 Método para abrir una cerradura a través de la API de We-Lock
     * 
     * @param lockRequest DTO con los datos necesarios para abrir la cerradura
     * @return Respuesta que indica si la operación fue exitosa o no
     */
    public LockResponseDto openLock(LockRequestDto lockRequest) {
        try {
            // URL para abrir la cerradura
            String openLockUrl = weLockConfig.getBaseUrl() + "/api/lock/open";
            
            // Preparar headers con autenticación
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Client-Id", weLockConfig.getClientId());
            headers.set("Client-Secret", weLockConfig.getClientSecret());
            
            // Preparar el cuerpo de la petición
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("lockId", lockRequest.getLockId());
            requestBody.put("userId", lockRequest.getUserId() != null ? lockRequest.getUserId() : "defaultUser");
            requestBody.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            
            // Crear la entidad de la petición
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            
            // Realizar la petición a la API externa
            ResponseEntity<Map> response = restTemplate.postForEntity(openLockUrl, requestEntity, Map.class);
            
            // Procesar respuesta
            boolean success = response.getStatusCode().is2xxSuccessful();
            String message = success ? "Cerradura abierta correctamente" : "Error al abrir la cerradura";
            
            // Devolver respuesta formateada
            return new LockResponseDto(
                success,
                message,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
        } catch (Exception e) {
            // Manejar y registrar errores de comunicación
            return new LockResponseDto(
                false,
                "Error al comunicarse con la API de We-Lock: " + e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
        }
    }
}