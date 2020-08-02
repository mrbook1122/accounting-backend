package cdut.accounting.service.impl;

import cdut.accounting.exception.FinanceAccountExistsException;
import cdut.accounting.exception.JoinTeamException;
import cdut.accounting.exception.UserExistsException;
import cdut.accounting.model.dto.FinanceAccountDTO;
import cdut.accounting.model.dto.JoinApplyDTO;
import cdut.accounting.model.dto.UserInfoDTO;
import cdut.accounting.model.entity.*;
import cdut.accounting.model.param.AddFinanceAccountParam;
import cdut.accounting.model.param.LoginParam;
import cdut.accounting.model.param.RegisterParam;
import cdut.accounting.repository.FinanceAccountRepository;
import cdut.accounting.repository.JoinApplyRepository;
import cdut.accounting.repository.TeamRepository;
import cdut.accounting.repository.UserRepository;
import cdut.accounting.service.UserService;
import cdut.accounting.utils.IDUtils;
import cdut.accounting.utils.JwtUtils;
import cdut.accounting.utils.RSAUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private FinanceAccountRepository financeAccountRepository;

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
        int userId = idUtils.generateUserId();
        User user = new User(userId, param.getUsername(), param.getEmail(), password);
        userRepository.save(user);
    }

    @Override
    public String login(LoginParam param) {
        String password = RSAUtils.privateDecrypt(param.getPassword());
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(param.getEmail(), password);
        Authentication authentication = authenticationManager.authenticate(token);
        User user = userRepository.findByEmail(param.getEmail());
        return JwtUtils.generateToken(authentication.getName(), user.getUid());
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
        Team team = teamRepository.findByUid(teamId);
        // 判断用户是否已经在团队中
        for (Member m : team.getMembers()) {
            if (m.getUserId() == user.getUid()) {
                throw new JoinTeamException("你已加入此团队，不能重复加入");
            }
        }
        JoinApply apply = new
                JoinApply(id, user.getUid(), user.getUsername(), teamId, team.getName(),
                Calendar.getInstance().getTime(),
                team.getOwnerId());
        joinApplyRepository.save(apply);
    }

    @Override
    public void exitTeam(String email, int teamId) {
        User user = userRepository.findByEmail(email);
        mongoTemplate.updateFirst(Query.query(Criteria.where("uid").is(teamId)),
                new Update().pull("members", null).filterArray(Criteria.where("uid").is(user.getUid())), Team.class);
    }

    @Override
    public List<JoinApplyDTO> auditList(String email) {
        User user = userRepository.findByEmail(email);
        List<JoinApply> applies = joinApplyRepository.findAllByOwnerId(user.getUid());
        List<JoinApplyDTO> result = new ArrayList<>();
        for (JoinApply j : applies) {
            result.add(new JoinApplyDTO(j.getUid(), j.getUsername(), j.getTeamName(), j.getDate()));
        }
        return result;
    }

    @Override
    public void addFinanceAccount(String email, AddFinanceAccountParam param) {
        User user = userRepository.findByEmail(email);
        // 1.先查询是否存在相同的账户
        if (financeAccountRepository
                .findByTypeAndNameAndOwnerId(param.getType(), param.getName(), user.getUid()) != null) {
            throw new FinanceAccountExistsException();
        }

        int id = idUtils.generateID();
        double money = Double.parseDouble(param.getBalance());
        FinanceAccount account = new FinanceAccount(id, param.getType(),
                param.getName(), money, user.getUid());
        financeAccountRepository.save(account);
    }

    @Override
    public void deleteAccount(String email, int id) {
        User user = userRepository.findByEmail(email);
        financeAccountRepository.deleteByUidAndOwnerId(id, user.getUid());
    }

    @Override
    public List<FinanceAccountDTO> getFinanceAccountList(String email) {
        User user = userRepository.findByEmail(email);
        List<FinanceAccount> accounts = financeAccountRepository.findAllByOwnerId(user.getUid());
        List<FinanceAccountDTO> result = new ArrayList<>();
        for (FinanceAccount f : accounts) {
            result.add(new FinanceAccountDTO(f.getUid(), f.getType(), f.getName(), f.getBalance()));
        }
        return result;
    }

    @Override
    public void allowJoin(int id) {
        // 1.查询审核条目
        JoinApply apply = joinApplyRepository.findByUid(id);
        if (apply != null) {
            // 2.添加用户到团队中
            Member member = new Member(apply.getUserId(), apply.getUsername(), "成员");
            mongoTemplate.updateFirst(Query.query(Criteria.where("uid").is(apply.getTeamId())), new Update().push(
                    "members", member), Team.class);
            // 3.删除审核条目
            joinApplyRepository.deleteByUid(id);
        }
    }

    @Override
    public void rejectJoin(String email, int id) {
        User user = userRepository.findByEmail(email);
        joinApplyRepository.deleteByUidAndOwnerId(id, user.getUid());
    }
}
