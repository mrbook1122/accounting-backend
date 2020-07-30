package cdut.accounting;

import cdut.accounting.model.entity.Team;
import cdut.accounting.repository.TeamRepository;
import cdut.accounting.service.TeamService;
import cdut.accounting.utils.IDUtils;
import cdut.accounting.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamTest {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private IDUtils idUtils;
    @Autowired
    private TeamService teamService;

    @Test
    void t() {
//        teamService.getTeamList("mrbok");
//        String token = JwtUtils.generateToken("mrbook");
//        System.out.println(token);
//        JwtUtils.getAuthentication(token);
        System.out.println(System.currentTimeMillis());
    }

    @Test
    void id() {
        System.out.println(teamService.searchTeam(70704040));
    }

    @Test
    void addTeam() {
        String owner = "Mrbook";
        String name = "团队1";
        teamService.addTeam(owner, name);
    }
}
