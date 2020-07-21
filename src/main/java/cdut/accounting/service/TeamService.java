package cdut.accounting.service;

import cdut.accounting.model.dto.MemberDTO;
import cdut.accounting.model.dto.TeamBillAnalysisDTO;
import cdut.accounting.model.dto.TeamBillDTO;
import cdut.accounting.model.dto.TeamDTO;
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
    List<TeamDTO> getTeamList(String username);

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
    void saveTeamBill(TeamBillParam param, String committer);

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
}