package cdut.accounting.controller;

import cdut.accounting.model.dto.CommonResult;
import cdut.accounting.model.dto.LoginDTO;
import cdut.accounting.model.dto.PublicKeyDTO;
import cdut.accounting.model.param.LoginParam;
import cdut.accounting.model.param.RegisterParam;
import cdut.accounting.service.UserService;
import cdut.accounting.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户相关控制器
 */
@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 请求公钥
     */
    @GetMapping("/security/public-key")
    public PublicKeyDTO getPublicKey() {
        PublicKeyDTO dto = new PublicKeyDTO();
        dto.setKey(RSAUtils.getPublicKeyStr());
        return dto;
    }

    /**
     * 用户登录
     */
    @PostMapping("/user/login")
    public LoginDTO login(@RequestBody LoginParam param) {
        logger.debug("user start login");
        String jwtToken = userService.login(param);
        logger.debug("user login");
        return new LoginDTO(true, "登录成功", jwtToken);
    }

    /**
     * 用户注册
     */
    @PostMapping("/user/register")
    public CommonResult register(@RequestBody @Valid RegisterParam param) {
        userService.register(param);
        return new CommonResult(true, "注册成功");
    }
}
