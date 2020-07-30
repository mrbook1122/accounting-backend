package cdut.accounting.model.dto;

import lombok.Data;

/**
 * 团队搜索时返回的dto
 * 传入一个id，返回与这个数字最左匹配的团队id列表
 */
@Data
public class TeamSearchDTO {
    private int id;
    /**
     * 团队名称
     */
    private String name;
    /**
     * 成员数量
     */
    private int count;
    /**
     * 团队创建者
     */
    private String owner;

    public TeamSearchDTO() {
    }

    public TeamSearchDTO(int id, String name, int count, String owner) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.owner = owner;
    }
}
