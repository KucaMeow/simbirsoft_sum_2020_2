package ru.stepan.ponomarev.storage_project.service;

import ru.stepan.ponomarev.storage_project.dto.SignInDto;
import ru.stepan.ponomarev.storage_project.dto.TokenDto;

import java.nio.file.AccessDeniedException;

/**
 * Class for signIn logic
 */
public interface SignInService {
    /**
     * SignIn method. Get user from database by username and check if username and password are valid, then generate token
     * @param signInDto SignInDto object with authentication info as username and password
     * @return TokenDto object with JWT Token
     * @throws AccessDeniedException Exception if there is problem with log in
     */
    TokenDto signIn(SignInDto signInDto) throws AccessDeniedException;
}
