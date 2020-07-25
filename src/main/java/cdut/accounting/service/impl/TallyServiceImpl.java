package cdut.accounting.service.impl;

import cdut.accounting.model.document.BillDocument;
import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.entity.Tally;
import cdut.accounting.model.param.BillParam;
import cdut.accounting.repository.TallyRepository;
import cdut.accounting.service.TallyService;
import cdut.accounting.utils.JwtUtils;
import cdut.accounting.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TallyServiceImpl implements TallyService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TallyRepository tallyRepository;

    @Override
    public UserBillAnalysisDTO getUserBill(String date) {
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

        AggregationOperation o1 =
                Aggregation.match(new Criteria().orOperator(Criteria.where("reism").is(false),
                        Criteria.where("reism").is(true).and("reismStatus").is(false))
                        .and("date").gte(d1).lt(d2));
        AggregationOperation o2 = Aggregation.group("type").sum("money").as("money");
        Aggregation aggregation = Aggregation.newAggregation(o1, o2);
        AggregationResults<BillDocument> results = mongoTemplate
                .aggregate(aggregation, "tally", BillDocument.class);
        List<BillDocument> documents = results.getMappedResults();

        UserBillAnalysisDTO bill = new UserBillAnalysisDTO();
        for (BillDocument d : documents) {
            switch (d.getId()) {
                case "expense":
                    bill.setExpenses(NumberUtils.formatDouble(d.getMoney()));
                    break;
                case "income":
                    bill.setIncome(NumberUtils.formatDouble(d.getMoney()));
                    break;
            }
        }
        return bill;
    }

    @Override
    public void saveUserBill(BillParam billParam) {
        String username = JwtUtils.getUsername();
//        TallyCategory tallyCategory = new TallyCategory();
        Tally tally = new Tally(new Date(), billParam.getType(), billParam.getLabel(), billParam.getMoney(),
                billParam.getRemarks(), billParam.isReism(), false, username);
        tallyRepository.save(tally);
    }

    @Override
    public List<UserBillDTO> getUserBillList(String username) {
        Date d1, d2;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        d1 = c.getTime();
        c.add(Calendar.DATE, 1);
        d2 = c.getTime();
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUsername(d1, d2, username);
        List<UserBillDTO> list = new ArrayList<>();
        for (Tally t : tallies) {
            list.add(new UserBillDTO().convertFrom(t));
        }
        return list;
    }
}
