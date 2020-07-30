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
    private int teamId;
    /**
     * 申请时间
     */
    private Date date;

    public JoinApply() {
    }

    public JoinApply(int uid, int userId, int teamId, Date date) {
        this.uid = uid;
        this.userId = userId;
        this.teamId = teamId;
        this.date = date;
    }
}
