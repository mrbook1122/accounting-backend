package cdut.accounting.service;

import cdut.accounting.model.dto.*;
import cdut.accounting.model.param.TeamBillParam;

import java.util.Date;
import java.util.List;

/**
 * 团队
 */
public interface TeamService {
    /**
     * 获取当前用户加入的团队列表
     */
    List<TeamDTO> getTeamList(int userId);

    /**
     * 获取团队成员列表
     *
     * @param teamId 团队id
     */
    List<MemberDTO> getTeamMemberList(int teamId);

    /**
     * 保存团队账单
     *
     * @param committer 提交账单的人
     */
    void saveTeamBill(TeamBillParam param, int userId);

    /**
     * 获取团队账单列表
     * @param teamId 团队id
     * @param date 账单日期
     */
    List<TeamBillDTO> getTeamBillList(int teamId, Date date);

    /**
     * 获取团队的日度分析
     */
    TeamBillAnalysisDTO getTeamBillAnalysis(int teamId, Date date);

    /**
     * 删除团队成员
     */
    void deleteMember(int teamId, int userId);

    /**
     * 删除团队
     */
    void deleteTeam(int teamId);

    /**
     * 添加团队
     */
    void addTeam(int userId, String teamName);

    /**
     * 返回最左前缀匹配id的团队列表
     */
    List<TeamSearchDTO> searchTeam(int id);

    /**
     * 提升团队成员权限
     */
    void upgradeMember(int teamId, int userId);

    /**
     * 降低团队成员权限
     */
    void degradeMember(int teamId, int userId);
}
