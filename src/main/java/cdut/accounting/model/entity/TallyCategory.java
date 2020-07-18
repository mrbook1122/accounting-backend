package cdut.accounting.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 账单分类（购物、餐饮...）
 */
@Data
@NoArgsConstructor
@Document
public class TallyCategory {
    @Id
    private String id;
    /**
     * 分类图标url
     */
    private String icon;
    /**
     * 分类类型（expense-支出，income收入）
     */
    private String type;
    /**
     * 分类名称
     */
    private String name;

    public TallyCategory(String icon, String type, String name) {
        this.icon = icon;
        this.type = type;
        this.name = name;
    }
}
