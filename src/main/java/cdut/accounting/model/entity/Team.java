package cdut.accounting.model.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 团队
 */
@Data
@Document
public class Team {
    private String id;
    private int uid;
    /**
     * 团队名称
     */
    private String name;
    /**
     * 成员
     */
    private List<Member> members;
    /**
     * 创建时间
     */
    private Date date;
}
