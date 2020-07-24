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
        System.out.println(JwtUtils.generateToken("mrbook"));
    }

    @Test
    void id() {

        System.out.println(idUtils.generateID());
    }
}
