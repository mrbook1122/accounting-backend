package cdut.accounting.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 账单条目（表示一条记账信息）
 */
@Data
@NoArgsConstructor
@Document
public class Tally {
    @Id
    private String id;
    /**
     * 记账日期
     */
    private Date date;
    /**
     * 账单类型（expense-支出，income收入）
     */
    private String type;
    /**
     * 账单分类（购物、餐饮...）
     */
    private TallyCategory tallyCategory;
    /**
     * 账单金额
     */
    private double money;
    /**
     * 备注
     */
    private String remark;
    /**
     * 报销
     */
    private boolean reism;
    /**
     * 报销状态，false表示未报销，true表示已报销
     */
    private boolean reismStatus;

    public Tally(Date date, String type, TallyCategory tallyCategory, double money) {
        this.date = date;
        this.type = type;
        this.tallyCategory = tallyCategory;
        this.money = money;
    }
}
