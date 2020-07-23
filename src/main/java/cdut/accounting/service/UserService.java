package cdut.accounting.service;

import cdut.accounting.model.dto.CommonResult;
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
}
