package cdut.accounting.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String money;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
