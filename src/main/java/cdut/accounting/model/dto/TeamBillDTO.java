package cdut.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 团队账单列表dto
 */
@Data
@AllArgsConstructor
public class TeamBillDTO {
    private String committer;
    private String type;
    private double money;
    private String remarks;
    /**
     * 该笔账单相关的人数
     */
    private int relates;
}
