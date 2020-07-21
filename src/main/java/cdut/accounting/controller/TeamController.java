package cdut.accounting.controller;

import cdut.accounting.model.dto.TeamDTO;
import cdut.accounting.service.TeamService;
import cdut.accounting.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/list")
    public List<TeamDTO> getTeamList() {
        String username = JwtUtils.getUsername();
        return teamService.getTeamList(username);
    }
}
