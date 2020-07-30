package cdut.accounting.controller;

import cdut.accounting.model.dto.*;
import cdut.accounting.model.param.AddTeamParam;
import cdut.accounting.model.param.TeamBillParam;
import cdut.accounting.service.TeamService;
import cdut.accounting.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    /**
     * 添加团队
     */
    @PostMapping("/team")
    public CommonResult addTeam(@RequestBody AddTeamParam param) {
        String username = JwtUtils.getUsername();
        teamService.addTeam(username, param.getName());
        return new CommonResult(true, "操作成功");
    }

    /**
     * 提交团队账单
     */
    @PostMapping("/team/bill")
    public CommonResult postTeamBill(@RequestBody TeamBillParam param) {
        String username = JwtUtils.getUsername();
        teamService.saveTeamBill(param, username);
        return new CommonResult(true, "操作成功");
    }

    /**
     * 获取团队账单列表
     */
    @GetMapping("/team/{id}/bill/list")
    public List<TeamBillDTO> getTeamBillList(@PathVariable int id, Date date) {
        return teamService.getTeamBillList(id, date);
    }

    /**
     * 获取团队账单分析数据
     */
    @GetMapping("/team/{id}/bill")
    public TeamBillAnalysisDTO getTeamBillAnalysis(@PathVariable int id, Date date) {
        return teamService.getTeamBillAnalysis(id, date);
    }

    /**
     * 删除团队成员
     */
    @DeleteMapping("/team/{teamId}/member/{userId}")
    public CommonResult deleteMember(@PathVariable int teamId, @PathVariable int userId) {
        teamService.deleteMember(teamId, userId);
        return new CommonResult(true, "操作成功");
    }

    /**
     * 删除团队
     */
    @DeleteMapping("/team/{teamId}")
    public CommonResult deleteTeam(@PathVariable int teamId) {
        teamService.deleteTeam(teamId);
        return new CommonResult(true, "操作成功");
    }

    /**
     * 根据id返回满足最左匹配的id列表
     */
    @GetMapping("/team/{id}")
    public List<TeamSearchDTO> searchTeam(@PathVariable int id) {
        return teamService.searchTeam(id);
    }
}
