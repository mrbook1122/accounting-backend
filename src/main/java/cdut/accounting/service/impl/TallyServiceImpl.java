package cdut.accounting.service.impl;

import cdut.accounting.exception.FinanceAccountNotExistsException;
import cdut.accounting.model.document.BillDocument;
import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.dto.UserBillWithAccountDTO;
import cdut.accounting.model.entity.FinanceAccount;
import cdut.accounting.model.entity.Tally;
import cdut.accounting.model.entity.User;
import cdut.accounting.model.param.BillParam;
import cdut.accounting.repository.FinanceAccountRepository;
import cdut.accounting.repository.TallyRepository;
import cdut.accounting.repository.UserRepository;
import cdut.accounting.service.TallyService;
import cdut.accounting.utils.DateUtils;
import cdut.accounting.utils.IDUtils;
import cdut.accounting.utils.JwtUtils;
import cdut.accounting.utils.NumberUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FinanceAccountRepository financeAccountRepository;
    @Autowired
    private IDUtils idUtils;
    @Autowired
    private ObjectMapper objectMapper;

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
        // 1.查询关联账户是否存在
        FinanceAccount account = null;
        if (billParam.getAccountId() != 0) {
            account = financeAccountRepository.findByUid(billParam.getAccountId());
            if (account == null) {
                throw new FinanceAccountNotExistsException();
            }
        }
        // 2.存入账单
        String email = JwtUtils.getUserEmail();
        User user = userRepository.findByEmail(email);
        int id = idUtils.generateID();
        String accountType = account == null ? null : account.getType();
        String accountName = account == null ? null : account.getName();
        Tally tally = new Tally(id, new Date(), billParam.getType(), billParam.getLabel(), billParam.getMoney(),
                billParam.getRemarks(), billParam.isReism(), false, user.getUsername(),
                user.getUid(), billParam.getAccountId(),
                accountType, accountName);
        tallyRepository.save(tally);
        // 3.如果有关联账户则扣减账户余额
        if (account != null) {
            account.setBalance(account.getBalance() - billParam.getMoney());
            financeAccountRepository.save(account);
        }
    }

    @Override
    public String getUserBillList(String email, Date date) {
        Date[] dates = DateUtils.getPeriodByDay(date);
        User user = userRepository.findByEmail(email);
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUserId(dates[0], dates[1], user.getUid());
        StringBuilder sb = new StringBuilder("[");
        int num;
        Tally t;
        try {
            for (int i = 0; i < (num = tallies.size()); i++) {
                if ((t = tallies.get(i)).getAccountId() != 0) {
                    UserBillWithAccountDTO dto = new UserBillWithAccountDTO();
                    BeanUtils.copyProperties(t, dto);
                    dto.setId(t.getUid());
                    sb.append(objectMapper.writeValueAsString(dto));
                } else {
                    UserBillDTO dto = new UserBillDTO();
                    BeanUtils.copyProperties(t, dto);
                    dto.setId(t.getUid());
                    sb.append(objectMapper.writeValueAsString(dto));
                }
                if (i < num - 1) {
                    sb.append(',');
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        sb.append(']');
        return sb.toString();
    }
}
