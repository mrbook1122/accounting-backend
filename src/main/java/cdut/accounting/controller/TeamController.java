package cdut.accounting.controller;

import cdut.accounting.model.dto.CommonResult;
import cdut.accounting.model.dto.MemberDTO;
import cdut.accounting.model.dto.TeamDTO;
import cdut.accounting.model.param.TeamBillParam;
import cdut.accounting.service.TeamService;
import cdut.accounting.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;

    /**
     * 获取用户的团队列表
     */
    @GetMapping("/user/team/list")
    public List<TeamDTO> getTeamList() {
        String username = JwtUtils.getUsername();
        return teamService.getTeamList(username);
    }

    /**
     * 获取团队的成员列表
     *
     * @param id 团队id
     */
    @GetMapping("/team/{id}/member/list")
    public List<MemberDTO> getTeamMemberList(@PathVariable int id) {
        return teamService.getTeamMemberList(id);
    }

    @PostMapping("/team/bill")
    public CommonResult postTeamBill(@RequestBody TeamBillParam param) {
        String username = JwtUtils.getUsername();
        teamService.saveTeamBill(param, username);
        return new CommonResult(true, "操作成功");
    }
}
