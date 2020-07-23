package cdut.accounting.model.param;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 用户注册param
 */
@Data
public class RegisterParam {
    @NotBlank
    private String username;
    @Email
    private String email;
    private String password;
}
