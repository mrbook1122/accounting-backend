package cdut.accounting.model.dto;

import java.util.Date;

public class UserBillWithAccountDTO extends UserBillDTO {
    private String accountName;
    private String accountType;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public UserBillWithAccountDTO() {
    }

    public UserBillWithAccountDTO(int id, String type, boolean reism, String label, double money,
                                  Date date, String remarks, String accountName, String accountType) {
        super(id, type, reism, label, money, date, remarks);
        this.accountName = accountName;
        this.accountType = accountType;
    }
}
