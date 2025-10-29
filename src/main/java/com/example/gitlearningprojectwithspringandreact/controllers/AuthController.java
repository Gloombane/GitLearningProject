package com.example.gitlearningprojectwithspringandreact.controllers;
import com.example.gitlearningprojectwithspringandreact.entities.Session;
import com.example.gitlearningprojectwithspringandreact.entities.User;
import com.example.gitlearningprojectwithspringandreact.services.sessionService.SessionServiceImpl;
import com.example.gitlearningprojectwithspringandreact.services.userService.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(
        origins = "http://localhost:3000",
        allowCredentials = "true"
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final UserServiceImpl userService;
    private final SessionServiceImpl sessionService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserServiceImpl userService, SessionServiceImpl sessionService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
    }




    @PostMapping("/register")
    public ResponseEntity<?> processRegistration(@RequestParam String email,
                                                 @RequestParam String password) {
        if (userService.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email уже зарегистрирован");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // userService сам должен заэнкодить
        userService.registerUser(user);
        return ResponseEntity.ok("Регистрация успешна");
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                   @RequestParam String password) {

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Пользователь не найден");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Неверный пароль");
        }

        Session session = sessionService.createSession(user.getEmail(), 2 * 60 * 60);

        ResponseCookie cookie = ResponseCookie.from("sessionId", session.getSessionId())
                .httpOnly(true)
                .path("/")
                .maxAge(2 * 60 * 60)
                .build();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("email", user.getEmail());
        responseBody.put("role", user.getRole());

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(responseBody);
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Достаём sessionId из куки
        String sessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        // Удаляем кастомную сессию из БД
        if (sessionId != null && !sessionId.isBlank()) {
            sessionService.deleteSession(sessionId);
        }

        // Чистим куку
        ResponseCookie deleteCookie = ResponseCookie.from("sessionId", "")
                .path("/")
                .maxAge(0)     // сразу истекает
                .httpOnly(true)
                .build();

        response.addHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.ok("Вы вышли");
    }
    @GetMapping("/check")
    public ResponseEntity<?> checkSession(@CookieValue(name = "sessionId", required = false) String sessionId) {
        if (sessionId != null && sessionService.isValid(sessionId)) {
            String username = sessionService.getUsername(sessionId); // если есть
            String role = sessionService.getRole(sessionId);         // если хранится в сессии

            Map<String, String> response = new HashMap<>();
            response.put("email", username != null ? username : "unknown");
            response.put("role", role != null ? role : "USER");

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not authenticated");
    }



}
