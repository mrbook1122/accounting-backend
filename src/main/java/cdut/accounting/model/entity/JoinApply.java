package cdut.accounting.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户加入团队的申请
 */
@Data
@Document
public class JoinApply {
    private int id;
    private int uid;
    private int userId;
    private String username;
    private int teamId;
    private String teamName;
    /**
     * 申请时间
     */
    private Date date;
    /**
     * 团队拥有者的id
     */
    private int ownerId;

    public JoinApply() {
    }

    public JoinApply(int uid, int userId, String username, int teamId, String teamName, Date date, int ownerId) {
        this.uid = uid;
        this.userId = userId;
        this.username = username;
        this.teamId = teamId;
        this.teamName = teamName;
        this.date = date;
        this.ownerId = ownerId;
    }
}
