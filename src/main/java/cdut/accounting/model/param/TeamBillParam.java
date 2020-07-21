package cdut.accounting.model.param;

import lombok.Data;

import java.util.Date;

/**
 * 团队账单请求对象
 */
@Data
public class TeamBillParam {
    private int teamId;
    private double money;
    private String type;
    private Date date;
    private String remarks;
    /**
     * 该笔账单相关的人员
     */
    private int[] relatedPeople;
}
