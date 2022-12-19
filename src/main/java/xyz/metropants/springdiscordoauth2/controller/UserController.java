package xyz.metropants.springdiscordoauth2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.metropants.springdiscordoauth2.config.security.oauth2.DiscordOAuth2User;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/@me")
    public ResponseEntity<DiscordOAuth2User> me(@AuthenticationPrincipal DiscordOAuth2User user) {
        return ResponseEntity.ok(user);
    }

}
