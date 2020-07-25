package cdut.accounting.service.impl;

import cdut.accounting.model.dto.HistogramDTO;
import cdut.accounting.model.dto.PieChartDTO;
import cdut.accounting.model.dto.PieChartItem;
import cdut.accounting.model.entity.Tally;
import cdut.accounting.model.entity.TeamBill;
import cdut.accounting.repository.TallyRepository;
import cdut.accounting.repository.TeamBillRepository;
import cdut.accounting.service.AnalysisService;
import cdut.accounting.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TallyRepository tallyRepository;
    @Autowired
    private TeamBillRepository teamBillRepository;

    @Override
    public HistogramDTO getUserHistogram(Date date, String username) {
        Date d1, d2;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        d1 = c.getTime();
        int numberOfDays = c.getActualMaximum(Calendar.DATE);
        c.add(Calendar.MONTH, 1);
        d2 = c.getTime();
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUsername(d1, d2, username);
        HistogramDTO result = new HistogramDTO();
        int[] income = new int[numberOfDays];
        int[] expenses = new int[numberOfDays];

        Calendar calendar = Calendar.getInstance();
        for (Tally t : tallies) {
            calendar.setTime(t.getDate());
            int day = calendar.get(Calendar.DATE);
            if (t.getType().equals("income")) {
                income[day - 1] += t.getMoney();
            } else {
                expenses[day - 1] -= t.getMoney();
            }
        }
        result.setExpenses(expenses);
        result.setIncome(income);
        return result;
    }

    @Override
    public PieChartDTO getUserPieChart(Date date, String username) {
        Date d1, d2;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        d1 = c.getTime();
        c.add(Calendar.MONTH, 1);
        d2 = c.getTime();
        List<Tally> tallies = tallyRepository.findByDateBetweenAndUsername(d1, d2, username);
        HashMap<String, Double> expenseMap = new HashMap<>();
        HashMap<String, Double> incomeMap = new HashMap<>();
        double expenseAmount = 0;
        double incomeAmount = 0;
        for (Tally t : tallies) {
            if (t.getType().equals("income")) {
                incomeAmount += t.getMoney();
                if (incomeMap.containsKey(t.getLabel())) {
                    double tmp = incomeMap.get(t.getLabel());
                    incomeMap.put(t.getLabel(), tmp + t.getMoney());
                } else {
                    incomeMap.put(t.getLabel(), t.getMoney());
                }
            } else {
                expenseAmount += t.getMoney();
                if (expenseMap.containsKey(t.getLabel())) {
                    double tmp = expenseMap.get(t.getLabel());
                    expenseMap.put(t.getLabel(), tmp + t.getMoney());
                } else {
                    expenseMap.put(t.getLabel(), t.getMoney());
                }
            }
        }
        List<PieChartItem> expenses = new ArrayList<>();
        List<PieChartItem> income = new ArrayList<>();
        for (Map.Entry<String, Double> entry : expenseMap.entrySet()) {
            expenses.add(new PieChartItem(entry.getKey(), formatDouble(entry.getValue() / expenseAmount)));
        }
        for (Map.Entry<String, Double> entry : incomeMap.entrySet()) {
            income.add(new PieChartItem(entry.getKey(), formatDouble(entry.getValue() / incomeAmount)));
        }
        PieChartDTO result = new PieChartDTO();
        result.setExpenses(expenses);
        result.setIncome(income);
        return result;
    }

    @Override
    public HistogramDTO getTeamHistogram(Date date, int teamId) {
        Date[] dates = DateUtils.getPeriodByMonth(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dates[0]);
        int numberOfDays = calendar.getActualMaximum(Calendar.DATE);

        List<TeamBill> teamBills = teamBillRepository.findByTeamIdAndDateBetween(teamId, dates[0], dates[1]);
        int[] income = new int[numberOfDays];
        int[] expenses = new int[numberOfDays];
        for (TeamBill t : teamBills) {
            calendar.setTime(t.getDate());
            int day = calendar.get(Calendar.DATE);
            if (t.getType().equals("income")) {
                income[day - 1] += t.getMoney();
            } else {
                expenses[day - 1] -= t.getMoney();
            }
        }
        HistogramDTO result = new HistogramDTO();
        result.setIncome(income);
        result.setExpenses(expenses);
        return result;
    }

    @Override
    public PieChartDTO getTeamPieChart(Date date, int teamId) {
        Date[] dates = DateUtils.getPeriodByMonth(date);
        List<TeamBill> teamBills = teamBillRepository.findByTeamIdAndDateBetween(teamId, dates[0], dates[1]);
        HashMap<String, Double> expenseMap = new HashMap<>();
        HashMap<String, Double> incomeMap = new HashMap<>();
        double expenseAmount = 0;
        double incomeAmount = 0;
        return null;
    }

    /**
     * 格式化小数保留两位
     */
    private double formatDouble(double d) {
        int a = (int) (d * 100);
        return a / 100.0;
    }
}
