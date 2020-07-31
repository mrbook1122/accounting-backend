package cdut.accounting.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 团队成员
 */
@Data
@NoArgsConstructor
public class Member {
    private int userId;
    /**
     * 成员用户名
     */
    private String username;
    /**
     * 角色
     */
    private String role;

    public Member(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
