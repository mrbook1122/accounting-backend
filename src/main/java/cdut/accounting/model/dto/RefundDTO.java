package cdut.accounting.model.dto;

/**
 * 账单报销
 */
public class RefundDTO {
    private int id;
    private String accountType;
    private String accountName;
    private double money;
    private String remarks;
    private String label;

    public RefundDTO() {
    }

    public RefundDTO(int id, String accountType, String accountName, double money, String remarks, String label) {
        this.id = id;
        this.accountType = accountType;
        this.accountName = accountName;
        this.money = money;
        this.remarks = remarks;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
