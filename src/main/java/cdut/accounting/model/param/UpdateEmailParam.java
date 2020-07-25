package cdut.accounting.model.param;

import lombok.Data;

/**
 * 修改邮箱请求参数
 */
@Data
public class UpdateEmailParam {
    private String fromEmail;
    private String toEmail;
}
