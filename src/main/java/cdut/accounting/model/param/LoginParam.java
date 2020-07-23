package cdut.accounting.model.param;

import lombok.Data;

/**
 * 用户登录参数
 */
@Data
public class LoginParam {
    private String email;
    private String password;
}
