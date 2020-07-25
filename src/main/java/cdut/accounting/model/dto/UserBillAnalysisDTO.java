package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 用户账单数据（月度账单和日度账单）
 */
@Data
public class UserBillAnalysisDTO {
    /**
     * 支出
     */
    private double expenses;
    /**
     * 收入
     */
    private double income;
}
