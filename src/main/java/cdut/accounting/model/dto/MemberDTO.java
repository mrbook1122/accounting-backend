package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 团队成员dto
 */
@Data
public class MemberDTO {
    private int uid;
    private String username;
    private String role;

    public MemberDTO() {
    }

    public MemberDTO(int uid, String username, String role) {
        this.uid = uid;
        this.username = username;
        this.role = role;
    }
}
