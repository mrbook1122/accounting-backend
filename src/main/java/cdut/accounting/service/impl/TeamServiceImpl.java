package cdut.accounting.service.impl;

import cdut.accounting.model.dto.TeamDTO;
import cdut.accounting.model.entity.Member;
import cdut.accounting.model.entity.Team;
import cdut.accounting.repository.TeamRepository;
import cdut.accounting.service.TeamService;
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
    private MongoTemplate mongoTemplate;

    @Override
    public List<TeamDTO> getTeamList(String username) {
        List<Team> teams = mongoTemplate.find(Query.query(Criteria.where("members.username").is(username)), Team.class);
        List<TeamDTO> result = new ArrayList<>();
        for (Team t : teams) {
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setId(t.getUId());
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
}
