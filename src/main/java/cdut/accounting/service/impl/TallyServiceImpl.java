package cdut.accounting.service.impl;

import cdut.accounting.exception.FinanceAccountNotExistsException;
import cdut.accounting.model.document.BillDocument;
import cdut.accounting.model.dto.RefundDTO;
import cdut.accounting.model.dto.UserBillAnalysisDTO;
import cdut.accounting.model.dto.UserBillDTO;
import cdut.accounting.model.dto.UserBillWithAccountDTO;
import cdut.accounting.model.entity.FinanceAccount;
import cdut.accounting.model.entity.Tally;
import cdut.accounting.model.entity.User;
import cdut.accounting.model.param.BillParam;
import cdut.accounting.model.param.RefundBillParam;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TallyServiceImpl implements TallyService {
    public static final Logger logger = LoggerFactory.getLogger(TallyServiceImpl.class);

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
        Date[] dates = DateUtils.convert(date);


        int userId = JwtUtils.getUserId();
        AggregationOperation o1 =
                Aggregation.match(Criteria.where("reism").is(false)
                        .and("userId").is(userId)
                        .and("date").gte(dates[0]).lt(dates[1]));
        AggregationOperation o2 = Aggregation.group("type").sum("money").as("money");
        Aggregation aggregation = Aggregation.newAggregation(o1, o2);
        AggregationResults<BillDocument> results = mongoTemplate
                .aggregate(aggregation, "tally", BillDocument.class);
        List<BillDocument> documents = results.getMappedResults();

        UserBillAnalysisDTO bill = new UserBillAnalysisDTO();
        for (BillDocument d : documents) {
            switch (d.getId()) {
                case "expenses":
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
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        int id = idUtils.generateID();
        String accountType = account == null ? null : account.getType();
        String accountName = account == null ? null : account.getName();
        double money = Double.parseDouble(billParam.getMoney());
        Tally tally = new Tally(id, new Date(), billParam.getType(), billParam.getLabel(), Double.parseDouble(billParam.getMoney()),
                billParam.getRemarks(), billParam.isReism(), false, user.getUsername(),
                user.getUid(), billParam.getAccountId(),
                accountType, accountName);
        tallyRepository.save(tally);
        // 3.如果有关联账户则更改账户余额
        if (account != null) {
            if (tally.getType().equals("income")) {
                account.setBalance(account.getBalance() + money);
            } else {
                account.setBalance(account.getBalance() - money);
            }
            financeAccountRepository.save(account);
        }
    }

    @Override
    public String getUserBillList(int userId, Date date) {
        Date[] dates = DateUtils.getPeriodByDay(date);
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUserIdOrderByDateDesc(dates[0], dates[1], userId);
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

    @Override
    public List<RefundDTO> getNonRefundList(int userId, Date date) {
        Date[] dates = DateUtils.getPeriodByMonth(date);
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUserIdAndReismAndReismStatus(dates[0], dates[1],
                userId, true, false);
        List<RefundDTO> result = new ArrayList<>();
        for (Tally t : tallies) {
            result.add(new RefundDTO(t.getUid(), t.getAccountType(), t.getAccountName(), t.getMoney(), t.getRemarks()
                    , t.getLabel()));
        }
        return result;
    }

    @Override
    public List<RefundDTO> getRefundList(int userId, Date date) {
        Date[] dates = DateUtils.getPeriodByMonth(date);
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUserIdAndReismAndReismStatus(dates[0], dates[1],
                userId, true, true);
        List<RefundDTO> result = new ArrayList<>();
        for (Tally t : tallies) {
            result.add(new RefundDTO(t.getUid(), t.getAccountType(), t.getAccountName(), t.getMoney(), t.getRemarks()
                    , t.getLabel()));
        }
        return result;
    }

    @Override
    public void refundBill(RefundBillParam param) {
        // 报销总金额
        double amount = 0;
        for (int i : param.getBillIdList()) {
            Tally t = tallyRepository.findByUid(i);
            t.setReismStatus(true);
            amount += t.getMoney();
            tallyRepository.save(t);
        }
        // 如果有关联账户，则报销到账户中
        if (param.getAccountId() != 0) {
            FinanceAccount account = financeAccountRepository.findByUid(param.getAccountId());
            account.setBalance(account.getBalance() + amount);
            financeAccountRepository.save(account);
        }
    }
}
