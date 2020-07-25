package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class UserInfoDTO {
    private int userId;
    private String username;
    private String email;
    private String signature;
}
