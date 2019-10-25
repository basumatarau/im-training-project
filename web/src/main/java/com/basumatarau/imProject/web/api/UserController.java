package com.basumatarau.imProject.web.api;

import com.basumatarau.imProject.serializer.customDto.UserAccountRegistrationDto;
import com.basumatarau.imProject.serializer.customDto.UserProfileDto;
import com.basumatarau.imProject.service.userService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/login", "/me"})
    @ResponseStatus(HttpStatus.OK)
    public UserProfileDto getUserInfo(Principal principal) {
        return userService.getUserProfileByUserEmail(principal.getName());
    }

    @PostMapping(value = "/signup", consumes = {"application/json;charset=utf-8"})
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@RequestBody UserAccountRegistrationDto newAccount,
                       Principal principal) {
        userService.registerNewUserAccount(newAccount);
    }
}
