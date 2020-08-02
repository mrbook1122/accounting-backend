package cdut.accounting.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加财产账户请求参数
 */
@Data
public class AddFinanceAccountParam {
    /**
     * 账户类型
     */
    @NotBlank
    private String type;
    /**
     * 账户名称
     */
    @NotBlank.List(@NotBlank)
    private String name;
    /**
     * 余额
     */
    private String balance;
}
