package cdut.accounting.model.entity;

import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.dto.base.OutputConverter;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
     * 账单分类（标签）
     */
    private String label;
    /**
     * 账单金额
     */
    private double money;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 报销
     */
    private boolean reism;
    /**
     * 报销状态，false表示未报销，true表示已报销
     */
    private boolean reismStatus;
    /**
     * 用户名
     */
    private String username;

    public Tally(Date date, String type, TallyCategory tallyCategory, double money) {
        this.date = date;
        this.type = type;
        this.tallyCategory = tallyCategory;
        this.money = money;
    }

    public Tally(Date date, String type, String label, double money, String remarks, boolean reism,
                 boolean reismStatus, String username) {
        this.date = date;
        this.type = type;
        this.label = label;
        this.money = money;
        this.remarks = remarks;
        this.reism = reism;
        this.reismStatus = reismStatus;
        this.username = username;
    }
}
