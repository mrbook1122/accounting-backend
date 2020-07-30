package cdut.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 审核列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinApplyDTO {
    private int id;
    private String username;
    private String teamname;
    private Date date;
}
