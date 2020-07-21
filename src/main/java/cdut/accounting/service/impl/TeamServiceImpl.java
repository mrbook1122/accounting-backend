package cdut.accounting.service.impl;

import cdut.accounting.model.dto.MemberDTO;
import cdut.accounting.model.dto.TeamDTO;
import cdut.accounting.model.entity.Member;
import cdut.accounting.model.entity.Team;
import cdut.accounting.model.entity.TeamBill;
import cdut.accounting.model.param.TeamBillParam;
import cdut.accounting.repository.TeamBillRepository;
import cdut.accounting.repository.TeamRepository;
import cdut.accounting.service.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamBillRepository teamBillRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<TeamDTO> getTeamList(String username) {
        List<Team> teams = mongoTemplate.find(Query.query(Criteria.where("members.username").is(username)), Team.class);
        List<TeamDTO> result = new ArrayList<>();
        for (Team t : teams) {
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setId(t.getUid());
            teamDTO.setName(t.getName());
            teamDTO.setNumber(t.getMembers().size());
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
}
