package cdut.accounting.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 财产账户
 */
@Data
@Document
public class FinanceAccount {
    private String id;
    private int uid;
    /**
     * 账户类型
     */
    private String type;
    /**
     * 账户名称
     */
    private String name;
    /**
     * 余额
     */
    private double balance;
    /**
     * 账户所有者
     */
    private int ownerId;

    public FinanceAccount(int uid, String type, String name, double balance, int ownerId) {
        this.uid = uid;
        this.type = type;
        this.name = name;
        this.balance = balance;
        this.ownerId = ownerId;
    }
}
