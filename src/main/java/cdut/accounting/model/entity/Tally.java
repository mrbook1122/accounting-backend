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
    private int uid;
    /**
     * 记账日期
     */
    private Date date;
    /**
     * 账单类型（expense-支出，income收入）
     */
    private String type;
    /**
     * 账单分类（标签）
     */
    private String label;
    /**
     * 账单金额（支出和收入统一用正数表示）
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
    private int userId;
    /**
     * 账单关联的账户id，如果为0则表示未关联账户
     */
    private int accountId;
    /**
     * (支付宝、微信...)
     */
    private String accountType;
    private String accountName;

    public Tally(int uid, Date date, String type, String label, double money, String remarks, boolean reism,
                 boolean reismStatus, String username, int userId, int accountId, String accountType, String accountName) {
        this.uid = uid;
        this.date = date;
        this.type = type;
        this.label = label;
        this.money = money;
        this.remarks = remarks;
        this.reism = reism;
        this.reismStatus = reismStatus;
        this.username = username;
        this.userId = userId;
        this.accountId = accountId;
        this.accountType = accountType;
        this.accountName = accountName;
    }
}
