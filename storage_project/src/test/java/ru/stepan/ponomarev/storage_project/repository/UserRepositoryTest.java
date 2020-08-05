package ru.stepan.ponomarev.storage_project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.stepan.ponomarev.storage_project.dto.UserInfo;
import ru.stepan.ponomarev.storage_project.model.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void getUserInfoByUsernameTest() {
        User user = User.builder()
                .username("TEST")
                .orders(new ArrayList<>())
                .hashedPassword("TEST")
                .balance(100)
                .build();
        userRepository.save(user);
        UserInfo toCompare = UserInfo.builder().username("TEST").balance(100).build();
        UserInfo userInfo = userRepository.getUserInfoByUsername("TEST");
        assertEquals(toCompare, userInfo);
    }

}
