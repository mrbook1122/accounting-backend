package cdut.accounting.model.param;

import lombok.Data;

/**
 * 修改用户名请求参数
 */
@Data
public class UpdateUsernameParam {
    private String fromName;
    private String toName;
}
