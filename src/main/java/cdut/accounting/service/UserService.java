package cdut.accounting.service;

import cdut.accounting.model.dto.JoinApplyDTO;
import cdut.accounting.model.dto.UserInfoDTO;
import cdut.accounting.model.param.AddFinanceAccountParam;
import cdut.accounting.model.param.LoginParam;
import cdut.accounting.model.param.RegisterParam;

import java.util.List;

/**
 * 用户service
 */
public interface UserService {
    /**
     * 用户注册
     */
    void register(RegisterParam param);

    /**
     * 用户登录
     *
     * @return jwt token
     */
    String login(LoginParam param);

    /**
     * 根据邮箱获取用户信息
     */
    UserInfoDTO getUserInfo(String email);

    /**
     * 更新用户名
     *
     * @param email   被更新用户的邮箱
     * @param newName 新的用户名
     */
    void updateUsername(String email, String newName);

    void updateEmail(String email, String newEmail);

    void updateSignature(String email, String signature);

    void updatePassword(String email, String password);

    /**
     * 申请加入团队
     */
    void joinTeam(String email, int teamId);

    /**
     * 退出团队
     */
    void exitTeam(String email, int teamId);

    /**
     * 获取用户的审核列表
     */
    List<JoinApplyDTO> auditList(String email);

    /**
     * 添加财产账户
     */
    void addFinanceAccount(String email, AddFinanceAccountParam param);

    /**
     * 删除财产账户
     * 根据用户id和账户id一起删除，防止利用接口删除其他账户
     */
    void deleteAccount(String email, int id);
}
