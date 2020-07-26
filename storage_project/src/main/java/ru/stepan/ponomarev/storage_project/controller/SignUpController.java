package ru.stepan.ponomarev.storage_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.stepan.ponomarev.storage_project.dto.SignUpDto;
import ru.stepan.ponomarev.storage_project.dto.TokenDto;
import ru.stepan.ponomarev.storage_project.service.SignUpService;

@RestController
public class SignUpController {

    final private SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PostMapping("/sign-up")
    ResponseEntity<TokenDto> signUp (@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(signUpService.signUp(signUpDto));
    }
}
