package ru.stepan.ponomarev.storage_project.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.SignUpDto;
import ru.stepan.ponomarev.storage_project.dto.TokenDto;
import ru.stepan.ponomarev.storage_project.model.Role;
import ru.stepan.ponomarev.storage_project.model.User;
import ru.stepan.ponomarev.storage_project.repository.UsersRepository;

import java.util.ArrayList;

/**
 * Class with implementation of interface for signUp logic
 */
@Service
public class SignUpServiceImpl implements SignUpService {

    final private UsersRepository usersRepository;
    final private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    public SignUpServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Sign up method. Creates new user from SignUpDto and save it to database, then generate token and return it
     * @param signUpDto SignUpDto object with username and password to create user
     * @return TokenDto object with JWT token
     */
    @Override
    public TokenDto signUp(SignUpDto signUpDto) {
        User userToSave = User.builder()
                .balance(0)
                .hashedPassword(passwordEncoder.encode(signUpDto.getPassword()))
                .orders(new ArrayList<>())
                .username(signUpDto.getUsername())
                .role(Role.ROLE_USER)
                .build();
        User user = usersRepository.save(userToSave);
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("name", user.getUsername())
                .claim("role", user.getRole().name())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new TokenDto(token);
    }
}
