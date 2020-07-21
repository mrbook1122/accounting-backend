package cdut.accounting.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@Document
public class User {
    @Id
    private String id;
    private int uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像url
     */
    private String avatar;
}
