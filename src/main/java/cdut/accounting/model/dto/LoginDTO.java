package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 用户登录dto
 */
@Data
public class LoginDTO {
    private boolean success;
    private String msg;
    private String token;

    public LoginDTO() {
    }

    public LoginDTO(boolean success, String msg, String token) {
        this.success = success;
        this.msg = msg;
        this.token = token;
    }
}
