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
}
