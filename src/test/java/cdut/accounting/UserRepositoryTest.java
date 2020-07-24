package cdut.accounting;

import cdut.accounting.model.entity.User;
import cdut.accounting.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void test() {
        User user = userRepository.findByEmail("1985904621@qq.com");
        System.out.println(user);
    }
}
