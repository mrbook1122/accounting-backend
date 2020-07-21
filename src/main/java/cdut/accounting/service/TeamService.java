package cdut.accounting.service;

import cdut.accounting.model.dto.MemberDTO;
import cdut.accounting.model.dto.TeamDTO;
import cdut.accounting.model.param.TeamBillParam;

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
}
