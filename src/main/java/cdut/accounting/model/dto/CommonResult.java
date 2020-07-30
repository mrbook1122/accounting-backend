package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 通用返回结果
 */
@Data
public class CommonResult {
    private boolean success;
    private String msg;

    public CommonResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static CommonResult success() {
        return new CommonResult(true, "操作成功");
    }
}
