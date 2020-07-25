package cdut.accounting.controller;

import cdut.accounting.model.dto.CommonResult;
import cdut.accounting.model.dto.LoginDTO;
import cdut.accounting.model.dto.PublicKeyDTO;
import cdut.accounting.model.dto.UserInfoDTO;
import cdut.accounting.model.param.*;
import cdut.accounting.service.UserService;
import cdut.accounting.utils.JwtUtils;
import cdut.accounting.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        String jwtToken = userService.login(param);
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

    /**
     * 获取用户信息
     */
    @GetMapping("/user/info")
    public UserInfoDTO getUserInfo() {
        String email = JwtUtils.getUserEmail();
        return userService.getUserInfo(email);
    }

    /**
     * 修改用户名
     */
    @PutMapping("/user/username")
    public CommonResult updateUsername(@RequestBody UpdateUsernameParam param) {
        String email = JwtUtils.getUserEmail();
        userService.updateUsername(email, param.getToName());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 修改邮箱
     */
    @PutMapping("/user/email")
    public CommonResult updateEmail(@RequestBody UpdateEmailParam param) {
        String email = JwtUtils.getUserEmail();
        userService.updateEmail(email, param.getToEmail());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 修改签名
     */
    @PutMapping("/user/signature")
    public CommonResult updateSignature(@RequestBody UpdateSignatureParam param) {
        String email = JwtUtils.getUserEmail();
        userService.updateSignature(email, param.getToSignature());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 修改用户密码
     */
    @PutMapping("/user/password")
    public CommonResult updatePassword(@RequestBody UpdatePasswordParam param) {
        String email = JwtUtils.getUserEmail();
        userService.updatePassword(email, param.getToPassword());
        return new CommonResult(true, "更新成功");
    }
}
