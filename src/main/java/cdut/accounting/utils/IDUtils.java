package cdut.accounting.utils;

import cdut.accounting.model.document.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * ID生成工具类
 * <p>
 * 因为需要注入MongoTemplate，所以这个类不是提供静态方法
 */
@Component
public class IDUtils {
    @Autowired
    private MongoTemplate mongoTemplate;

    public int generateID() {
        Counter c = mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is("id")), new Update().inc("value",
                1),
                Counter.class, "counter");
        return c.getValue();
    }

    /**
     * 生成用户id
     */
    public int generateUserId() {
        Counter c = mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is("uid")), new Update().inc("value",
                1),
                Counter.class, "counter");
        Random random = new Random();
        int a = c.getValue();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                s.append(random.nextInt(9) + 1);
            } else s.append(random.nextInt(10));
            s.append(a % 10);
            a /= 10;
        }
        return Integer.parseInt(s.toString());
    }
}
