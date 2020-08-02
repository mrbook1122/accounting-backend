package cdut.accounting.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 团队账单
 */
@Data
@Document
public class TeamBill {
    private String id;
    private int uid;
    private int teamId;
    private double money;
    private String type;
    private Date date;
    private String remarks;
    private int[] relatedPeople;
    /**
     * 提交这笔账单的人
     */
    private String committer;

    public TeamBill() {
    }

    public TeamBill(int uid, int teamId, double money, String type, Date date, String remarks, int[] relatedPeople, String committer) {
        this.uid = uid;
        this.teamId = teamId;
        this.money = money;
        this.type = type;
        this.date = date;
        this.remarks = remarks;
        this.relatedPeople = relatedPeople;
        this.committer = committer;
    }
}
