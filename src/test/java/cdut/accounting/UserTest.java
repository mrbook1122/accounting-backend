package cdut.accounting;

import cdut.accounting.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    void updateName() {
        userService.updateUsername(28608010, "张");
    }
}
