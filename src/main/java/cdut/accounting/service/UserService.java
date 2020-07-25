package cdut.accounting.service;

import cdut.accounting.model.dto.CommonResult;
import cdut.accounting.model.dto.UserInfoDTO;
import cdut.accounting.model.param.LoginParam;
import cdut.accounting.model.param.RegisterParam;

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
}
