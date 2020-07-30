package cdut.accounting.service.impl;

import cdut.accounting.exception.UserExistsException;
import cdut.accounting.model.dto.UserInfoDTO;
import cdut.accounting.model.entity.JoinApply;
import cdut.accounting.model.entity.User;
import cdut.accounting.model.param.LoginParam;
import cdut.accounting.model.param.RegisterParam;
import cdut.accounting.repository.JoinApplyRepository;
import cdut.accounting.repository.UserRepository;
import cdut.accounting.service.UserService;
import cdut.accounting.utils.IDUtils;
import cdut.accounting.utils.JwtUtils;
import cdut.accounting.utils.RSAUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private IDUtils idUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JoinApplyRepository joinApplyRepository;

    @Override
    public void register(RegisterParam param) {
        if (userRepository.findByEmail(param.getEmail()) != null) {
            throw new UserExistsException();
        }
        // 解密
        String password = RSAUtils.privateDecrypt(param.getPassword());
        // 加密存储密码
        password = passwordEncoder.encode(password);
        // 生成用户id
        int userId = idUtils.generateID();
        User user = new User(userId, param.getUsername(), param.getEmail(), password);
        userRepository.save(user);
    }

    @Override
    public String login(LoginParam param) {
        String password = RSAUtils.privateDecrypt(param.getPassword());
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(param.getEmail(), password);
        Authentication authentication = authenticationManager.authenticate(token);
        return JwtUtils.generateToken(authentication.getName());
    }

    @Override
    public UserInfoDTO getUserInfo(String email) {
        User user = userRepository.findByEmail(email);
        UserInfoDTO dto = new UserInfoDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setUserId(user.getUid());
        return dto;
    }

    @Override
    public void updateUsername(String email, String newName) {
        User user = userRepository.findByEmail(email);
        user.setUsername(newName);
        userRepository.save(user);
    }

    @Override
    public void updateEmail(String email, String newEmail) {
        if (userRepository.findByEmail(newEmail) != null) {
            throw new UserExistsException();
        }
        User user = userRepository.findByEmail(email);
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void updateSignature(String email, String signature) {
        User user = userRepository.findByEmail(email);
        user.setSignature(signature);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        password = RSAUtils.privateDecrypt(password);
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void joinTeam(String email, int teamId) {
        User user = userRepository.findByEmail(email);
        int id = idUtils.generateID();
        JoinApply apply = new JoinApply(id, user.getUid(), teamId, Calendar.getInstance().getTime());
        joinApplyRepository.save(apply);
    }
}
