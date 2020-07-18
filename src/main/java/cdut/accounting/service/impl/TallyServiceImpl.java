package cdut.accounting.service.impl;

import cdut.accounting.model.document.BillDocument;
import cdut.accounting.model.dto.UserBill;
import cdut.accounting.service.TallyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TallyServiceImpl implements TallyService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserBill getUserBill(String date) {
        Date d1, d2;
        Calendar time = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        if (date.length() == 10) {
            int day = Integer.parseInt(date.substring(8, 10));
            time.set(year, month - 1, day, 0, 0, 0);
            d1 = time.getTime();
            time.add(Calendar.DATE, 1);
            d2 = time.getTime();
        } else {
            time.set(year, month - 1, 0, 0, 0, 0);
            d1 = time.getTime();
            time.add(Calendar.MONTH, 1);
            d2 = time.getTime();
        }

        AggregationOperation o1 = Aggregation.match(new Criteria().andOperator(Criteria.where("date").gte(d1).lt(d2)));
        AggregationOperation o2 = Aggregation.group("type").sum("money").as("money");
        Aggregation aggregation = Aggregation.newAggregation(o1, o2);
        AggregationResults<BillDocument> results = mongoTemplate.aggregate(aggregation, "tally", BillDocument.class);
        List<BillDocument> documents = results.getMappedResults();

        UserBill bill = new UserBill();
        for (BillDocument d : documents) {
            switch (d.getId()) {
                case "expense":
                    bill.setExpense(d.getMoney());
                    break;
                case "income":
                    bill.setIncome(d.getMoney());
                    break;
            }
        }
        return bill;
    }
}
