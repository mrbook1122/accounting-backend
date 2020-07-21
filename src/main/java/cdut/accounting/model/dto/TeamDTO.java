package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 团队dto
 */
@Data
public class TeamDTO {
    private int id;
    private String name;
    /**
     * 请求者在团队中的角色
     */
    private String role;
    /**
     * 团队成员数量
     */
    private int number;
}
