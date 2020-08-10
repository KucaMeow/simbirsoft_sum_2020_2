package ru.stepan.ponomarev.storage_project.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stepan.ponomarev.storage_project.dto.SignInDto;
import ru.stepan.ponomarev.storage_project.dto.TokenDto;
import ru.stepan.ponomarev.storage_project.model.User;
import ru.stepan.ponomarev.storage_project.repository.UserRepository;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

/**
 * Class with implementation of interface for signIn logic
 */
@Service
public class SignInServiceImpl implements SignInService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    public SignInServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * SignIn method. Get user from database by username and check if username and password are valid, then generate token
     * @param signInDto SignInDto object with authentication info as username and password
     * @return TokenDto object with JWT Token
     * @throws AccessDeniedException Exception if there is problem with log in
     */
    @Override
    public TokenDto signIn(SignInDto signInDto) throws AccessDeniedException {
        Optional<User> userOptional = userRepository.findUserByUsername(signInDto.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getHashedPassword())) {
                String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("name", user.getUsername())
                        .claim("role", user.getRole().name())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");

    }
}
