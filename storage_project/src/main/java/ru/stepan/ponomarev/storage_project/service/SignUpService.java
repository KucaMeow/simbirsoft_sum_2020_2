package ru.stepan.ponomarev.storage_project.service;

import ru.stepan.ponomarev.storage_project.dto.SignUpDto;
import ru.stepan.ponomarev.storage_project.dto.TokenDto;

/**
 * Class for signUp logic
 */
public interface SignUpService {
    /**
     * Sign up method. Creates new user from SignUpDto and save it to database, then generate token and return it
     * @param signUpDto SignUpDto object with username and password to create user
     * @return TokenDto object with JWT token
     */
    TokenDto signUp(SignUpDto signUpDto);
}
