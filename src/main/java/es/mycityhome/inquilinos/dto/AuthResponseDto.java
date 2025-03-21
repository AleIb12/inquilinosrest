package es.mycityhome.inquilinos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private String username;
    
    public AuthResponseDto(String token, String username) {
        this.token = token;
        this.username = username;
    }
}