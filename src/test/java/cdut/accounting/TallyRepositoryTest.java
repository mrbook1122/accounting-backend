package cdut.accounting;

import cdut.accounting.model.dto.UserBill;
import cdut.accounting.model.entity.Tally;
import cdut.accounting.model.entity.TallyCategory;
import cdut.accounting.repository.TallyRepository;
import cdut.accounting.service.TallyService;
import org.apache.catalina.filters.RemoteIpFilter;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@SpringBootTest
public class TallyRepositoryTest {
    @Autowired
    private TallyRepository repository;

    @Autowired
    private TallyService tallyService;

    @Autowired
    private MongoTemplate template;

    @Test
    void service() {
        String date = "2020-07-15";
        UserBill bill = tallyService.getUserBill(date);
        System.out.println(bill);
    }

    @Test
    void insert() {
        TallyCategory category = new TallyCategory(null, "expense", "购物");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
//        for (int i = 0; i < 20; i++) {
//        }
    }

    @Test
    void read() {
        Iterable<Tally> tallies = repository.findAll();
        for (Tally tally : tallies) {
            System.out.println(tally);
        }
    }

    @Test
    void t1() {
        AggregationOperation o1 = Aggregation.group("type").sum("money").as("money");
        AggregationOperation o2 = null;
        Aggregation aggregation = Aggregation.newAggregation(o1, o2);
//        AggregationResults<Object> results = template.aggregate(aggregation, "tally", Object.class);
//        System.out.println(results.getRawResults().size());
//        System.out.println(results.getRawResults());
//        List<Document> list = results.getRawResults().getList("results", Document.class);
//        System.out.println(list.get(0).get("_id"));
//        System.out.println(list.get(0).get("money"));
//        Double d = (Double) list.get(0).get("money");
//        NumberFormat format = new DecimalFormat(".5");
//        System.out.println(format.format(d));
        AggregationResults<TallyObject> results = template.aggregate(aggregation, "tally", TallyObject.class);
        List<TallyObject> objects = results.getMappedResults();
        for (TallyObject o : objects) {
            System.out.println(o);
        }
    }

}
