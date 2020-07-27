package ru.stepan.ponomarev.storage_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.stepan.ponomarev.storage_project.dto.SignInDto;
import ru.stepan.ponomarev.storage_project.dto.TokenDto;
import ru.stepan.ponomarev.storage_project.service.SignInService;

import java.nio.file.AccessDeniedException;

@RestController
public class SignInController {

    final private SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

    @PostMapping("/sign-in")
    ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) throws AccessDeniedException {
        return ResponseEntity.ok(signInService.signIn(signInDto));
    }
}
