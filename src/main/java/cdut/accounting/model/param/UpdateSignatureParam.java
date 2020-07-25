package cdut.accounting.model.param;

import lombok.Data;

/**
 * 修改签名请求参数
 */
@Data
public class UpdateSignatureParam {
    private String fromSignature;
    private String toSignature;
}
