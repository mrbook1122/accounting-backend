package cdut.accounting.service;

import cdut.accounting.model.dto.TeamDTO;

import java.util.List;

/**
 * 团队
 */
public interface TeamService {
    /**
     * 获取当前用户加入的团队列表
     */
    List<TeamDTO> getTeamList(String username);
}
