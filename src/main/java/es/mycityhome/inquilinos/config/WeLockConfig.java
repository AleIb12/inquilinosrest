package es.mycityhome.inquilinos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 🔧 Configuración para la integración con la API de We-Lock
 * 
 * Esta clase proporciona la configuración y beans necesarios para
 * conectarse con la API externa de We-Lock para el control de cerraduras.
 */
@Configuration
public class WeLockConfig {

    /**
     * URL base de la API de We-Lock
     */
    @Value("${welock.api.baseUrl}")
    private String baseUrl;
    
    /**
     * Identificador de cliente para autenticación en la API
     */
    @Value("${welock.api.clientId}")
    private String clientId;
    
    /**
     * Secreto de cliente para autenticación en la API
     */
    @Value("${welock.api.clientSecret}")
    private String clientSecret;
    
    /**
     * 🌐 Cliente HTTP para realizar peticiones a la API externa
     * 
     * @return Instancia de RestTemplate configurada
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    /**
     * Obtiene la URL base de la API We-Lock
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * Obtiene el ID de cliente para la autenticación
     */
    public String getClientId() {
        return clientId;
    }
    
    /**
     * Obtiene el secreto de cliente para la autenticación
     */
    public String getClientSecret() {
        return clientSecret;
    }
}