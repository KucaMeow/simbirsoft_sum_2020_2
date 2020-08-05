package ru.stepan.ponomarev.storage_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.stepan.ponomarev.storage_project.dto.UserInfo;
import ru.stepan.ponomarev.storage_project.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByUsername(String username);

    @Query("select new ru.stepan.ponomarev.storage_project.dto.UserInfo(user.username, user.balance)" +
            " from User user where user.username = :username")
    public UserInfo getUserInfoByUsername(String username);
}
