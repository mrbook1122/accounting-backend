package cdut.accounting.service.impl;

import cdut.accounting.model.document.BillDocument;
import cdut.accounting.model.dto.*;
import cdut.accounting.model.entity.Member;
import cdut.accounting.model.entity.Team;
import cdut.accounting.model.entity.TeamBill;
import cdut.accounting.model.entity.User;
import cdut.accounting.model.param.TeamBillParam;
import cdut.accounting.repository.TeamBillRepository;
import cdut.accounting.repository.TeamRepository;
import cdut.accounting.repository.UserRepository;
import cdut.accounting.service.TeamService;
import cdut.accounting.utils.IDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamBillRepository teamBillRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IDUtils idUtils;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<TeamDTO> getTeamList(String username) {
        List<Team> teams = mongoTemplate.find(Query.query(Criteria.where("members.username").is(username)), Team.class);
        List<TeamDTO> result = new ArrayList<>();
        for (Team t : teams) {
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setId(t.getUid());
            teamDTO.setName(t.getName());
            teamDTO.setNumber(t.getMembers().size());
            teamDTO.setDate(t.getDate());
            String role = null;
            for (Member m : t.getMembers()) {
                if (m.getUsername().equals(username)) {
                    role = m.getRole();
                }
            }
            teamDTO.setRole(role);
            result.add(teamDTO);
        }
        return result;
    }

    @Override
    public List<MemberDTO> getTeamMemberList(int teamId) {
        Team team = teamRepository.findByUid(teamId);
        List<MemberDTO> results = new ArrayList<>();
        for (Member m : team.getMembers()) {
            MemberDTO dto = new MemberDTO();
            BeanUtils.copyProperties(m, dto);
            results.add(dto);
        }
        return results;
    }

    @Override
    public void saveTeamBill(TeamBillParam param, String committer) {
        TeamBill bill = new TeamBill();
        BeanUtils.copyProperties(param, bill);
        bill.setCommitter(committer);
        teamBillRepository.save(bill);
    }

    @Override
    public List<TeamBillDTO> getTeamBillList(int teamId, Date date) {
        Date d1, d2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        d2 = calendar.getTime();
        List<TeamBill> teamBills = teamBillRepository.findByTeamIdAndDateBetween(teamId, d1, d2);
        List<TeamBillDTO> results = new ArrayList<>();
        for (TeamBill bill : teamBills) {
            results.add(new TeamBillDTO(bill.getCommitter(), bill.getType(), bill.getMoney(), bill.getRemarks(),
                    bill.getRelatedPeople().length));
        }
        return results;
    }

    @Override
    public TeamBillAnalysisDTO getTeamBillAnalysis(int teamId, Date date) {
        Date d1, d2;
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        d1 = time.getTime();
        time.add(Calendar.DATE, 1);
        d2 = time.getTime();

        AggregationOperation o1 =
                Aggregation.match(Criteria.where("teamId").is(teamId).and("date").gte(d1).lt(d2));
        AggregationOperation o2 = Aggregation.group("type").sum("money").as("money");
        Aggregation aggregation = Aggregation.newAggregation(o1, o2);
        AggregationResults<BillDocument> results = mongoTemplate
                .aggregate(aggregation, TeamBill.class, BillDocument.class);
        List<BillDocument> documents = results.getMappedResults();

        TeamBillAnalysisDTO bill = new TeamBillAnalysisDTO();
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

    @Override
    public void deleteMember(int teamId, int userId) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("uid").is(teamId)),
                new Update().pull("members", null).filterArray(Criteria.where("uid").is(userId)), Team.class);
    }

    @Override
    public void deleteTeam(int teamId) {
        teamRepository.deleteByUid(teamId);
    }

    @Override
    public void addTeam(String owner, String teamName) {
        User user = userRepository.findByEmail(owner);
        Team team = new Team();
        team.setUid(idUtils.generateUserId());
        team.setName(teamName);
        team.setDate(Calendar.getInstance().getTime());
        team.setOwnerId(user.getUid());
        team.setOwner(owner);
        teamRepository.save(team);
    }

    @Override
    public List<TeamSearchDTO> searchTeam(int id) {
        // 将id变成8位数跟数据库中的id比较大小
        int from = id - 1, end = id + 1;
        int[][] m = {
                {10, 100, 1000, 10000, 100000, 1000000, 10000000},
                {10000000, 1000000, 100000, 10000, 1000, 100, 10},
        };
        for (int i = 0; i < m[0].length; i++) {
            if (id / m[0][i] == 0) {
                from = id * m[1][i] - 1;
                end = (id + 1) * m[1][i] + 1;
                break;
            }
        }
        List<Team> teams = teamRepository.findAllByUidBetween(from, end);
        List<TeamSearchDTO> result = new ArrayList<>();
        for (Team t : teams) {
            int count = t.getMembers() == null ? 0 : t.getMembers().size();
            result.add(new TeamSearchDTO(t.getUid(), t.getName(), count, t.getOwner()));
        }
        return result;
    }
}
