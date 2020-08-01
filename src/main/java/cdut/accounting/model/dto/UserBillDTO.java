package cdut.accounting.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserBillDTO {
    private int id;
    private String type;
    private boolean reism;
    private String label;
    private double money;
    private Date date;
    private String remarks;

    public UserBillDTO() {
    }

    public UserBillDTO(int id, String type, boolean reism, String label, double money, Date date, String remarks) {
        this.id = id;
        this.type = type;
        this.reism = reism;
        this.label = label;
        this.money = money;
        this.date = date;
        this.remarks = remarks;
    }
}
