package cdut.accounting.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 团队账单请求对象
 */
@Data
public class TeamBillParam {
    private int teamId;
    private String money;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String remarks;
    /**
     * 该笔账单相关的人员
     */
    private int[] relatedPeople;
}
