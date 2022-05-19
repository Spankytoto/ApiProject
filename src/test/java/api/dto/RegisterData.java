package api.dto;

import lombok.Data;

@Data
public class RegisterData {
    private String email;
    private String password;
    private Integer id;
    private String token;
    private String error;
}
