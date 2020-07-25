package cdut.accounting.model.param;

import lombok.Data;

/**
 * 修改密码请求参数
 */
@Data
public class UpdatePasswordParam {
    private String fromPassword;
    private String toPassword;
}
