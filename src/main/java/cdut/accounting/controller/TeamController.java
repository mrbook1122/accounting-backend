package cdut.accounting.controller;

import cdut.accounting.model.dto.*;
import cdut.accounting.model.param.AddTeamParam;
import cdut.accounting.model.param.TeamBillParam;
import cdut.accounting.service.TeamService;
import cdut.accounting.utils.DateUtils;
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
        int userId = JwtUtils.getUserId();
        return teamService.getTeamList(userId);
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
        int userId = JwtUtils.getUserId();
        teamService.addTeam(userId, param.getName());
        return new CommonResult(true, "操作成功");
    }

    /**
     * 提交团队账单
     */
    @PostMapping("/team/bill")
    public CommonResult postTeamBill(@RequestBody TeamBillParam param) {
        int userId = JwtUtils.getUserId();
        teamService.saveTeamBill(param, userId);
        return new CommonResult(true, "操作成功");
    }

    /**
     * 获取团队账单列表
     */
    @GetMapping("/team/{id}/bill/list")
    public List<TeamBillDTO> getTeamBillList(@PathVariable int id, String date) {
        Date time = DateUtils.convertByDay(date);
        return teamService.getTeamBillList(id, time);
    }

    /**
     * 获取团队账单分析数据
     */
    @GetMapping("/team/{id}/bill")
    public TeamBillAnalysisDTO getTeamBillAnalysis(@PathVariable int id, String date) {
        Date time = DateUtils.convertByDay(date);
        return teamService.getTeamBillAnalysis(id, time);
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

    /**
     * 提升成员权限
     */
    @PatchMapping("/team/{teamId}/member/{userId}/upgrade")
    public CommonResult upgradeMember(@PathVariable int teamId, @PathVariable int userId) {
        teamService.upgradeMember(teamId, userId);
        return CommonResult.success();
    }

    /**
     * 降低成员权限
     */
    @PatchMapping("/team/{teamId}/member/{userId}/downgrade")
    public CommonResult downgradeMember(@PathVariable int teamId, @PathVariable int userId) {
        teamService.degradeMember(teamId, userId);
        return CommonResult.success();
    }
}
