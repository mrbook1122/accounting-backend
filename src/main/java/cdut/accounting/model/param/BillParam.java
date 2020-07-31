package cdut.accounting.model.param;

import lombok.Data;

import java.util.Date;

/**
 * 提交账单的请求对象
 */
@Data
public class BillParam {
    /**
     * 分类
     */
    private String label;
    /**
     * 金额
     */
    private double money;
    /**
     * 关联账户id
     */
    private int accountId;
    /**
     * 收入还是支出(income,expense)
     */
    private String type;
    /**
     * 时间
     */
    private Date date;
    /**
     * 报销
     */
    private boolean reism;
    /**
     * 备注
     */
    private String remarks;
}
