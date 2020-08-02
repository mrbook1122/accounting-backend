package cdut.accounting.controller;

import cdut.accounting.model.dto.*;
import cdut.accounting.model.entity.User;
import cdut.accounting.model.param.*;
import cdut.accounting.repository.UserRepository;
import cdut.accounting.service.UserService;
import cdut.accounting.utils.JwtUtils;
import cdut.accounting.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户相关控制器
 */
@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        return userService.getUserInfo(email);
    }

    /**
     * 修改用户名
     */
    @PatchMapping("/user/username")
    public CommonResult updateUsername(@RequestBody UpdateUsernameParam param) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.updateUsername(email, param.getToName());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 修改邮箱
     */
    @PatchMapping("/user/email")
    public CommonResult updateEmail(@RequestBody UpdateEmailParam param) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.updateEmail(email, param.getToEmail());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 修改签名
     */
    @PatchMapping("/user/signature")
    public CommonResult updateSignature(@RequestBody UpdateSignatureParam param) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.updateSignature(email, param.getToSignature());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 修改用户密码
     */
    @PatchMapping("/user/password")
    public CommonResult updatePassword(@RequestBody UpdatePasswordParam param) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.updatePassword(email, param.getToPassword());
        return new CommonResult(true, "更新成功");
    }

    /**
     * 申请加入团队
     */
    @PutMapping("/user/team/{teamId}")
    public CommonResult joinTeam(@PathVariable int teamId) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.joinTeam(email, teamId);
        return CommonResult.success();
    }

    /**
     * 退出团队
     */
    @DeleteMapping("/user/team/{teamId}")
    public CommonResult exitTeam(@PathVariable int teamId) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.exitTeam(email, teamId);
        return CommonResult.success();
    }

    /**
     * 用户待审核列表
     */
    @GetMapping("/user/audit/list")
    public List<JoinApplyDTO> auditList() {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        return userService.auditList(email);
    }

    /**
     * 用户财产账户列表
     */
    @GetMapping("/user/finance/account/list")
    public List<FinanceAccountDTO> getFinanceAccountList() {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        return userService.getFinanceAccountList(email);
    }

    /**
     * 添加财产账户
     */
    @PostMapping("/user/finance/account")
    public CommonResult addFinanceAccount(@RequestBody @Valid AddFinanceAccountParam param) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.addFinanceAccount(email, param);
        return CommonResult.success();
    }

    /**
     * 删除财产账户
     */
    @DeleteMapping("/user/finance/account/{id}")
    public CommonResult deleteFinanceAccount(@PathVariable int id) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.deleteAccount(email, id);
        return CommonResult.success();
    }

    /**
     * 同意用户加入团队
     * <p>
     * TODO 应该当前用户和拥有者是同一人
     *
     * @param id 审核条目id
     */
    @PatchMapping("/user/audit/{id}/allow")
    public CommonResult allowJoin(@PathVariable int id) {
        userService.allowJoin(id);
        return CommonResult.success();
    }

    @PatchMapping("/user/audit/{id}/reject")
    public CommonResult rejectJoin(@PathVariable int id) {
        int userId = JwtUtils.getUserId();
        User user = userRepository.findByUid(userId);
        String email = user.getEmail();
        userService.rejectJoin(email, id);
        return CommonResult.success();
    }
}
