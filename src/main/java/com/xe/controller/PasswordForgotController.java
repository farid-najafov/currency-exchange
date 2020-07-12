package com.xe.controller;

import com.xe.dto.PasswordForgotDto;
import com.xe.entity.Mail;
import com.xe.entity.PasswordResetToken;
import com.xe.entity.User;
import com.xe.exception.UserNotFoundException;
import com.xe.repo.PasswordResetTokenRepository;
import com.xe.service.EmailService;
import com.xe.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

    private final UserService userService;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public PasswordForgotController(UserService userService, PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto() {
        return new PasswordForgotDto();
    }

    @GetMapping
    public String showForgotPassword() {
        log.info("GET -> /forgot-password");
        return "forgot-password";
    }

    @PostMapping
    public String processForgotPasswordForm(
            @ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto from,
            BindingResult result,
            HttpServletRequest request) throws Throwable {

        if (result.hasErrors()) return "forgot-password";

        Optional<User> user = userService.findByEmail(from.getEmail());
        if (!user.isPresent()) {
            result.rejectValue("email", null, "We could not find an account for that e-mail address.");
            return "forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();

        token.setToken(UUID.randomUUID().toString());
        token.setUser(user.orElseThrow(() -> {
            throw new UserNotFoundException("User not found ");
        }));

        token.setExpiryDate(10);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo(user.orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        }).getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();

        model.put("token", token);
        model.put("user", user.orElseThrow(() -> {
            throw new UserNotFoundException("User not found");
        }));
        model.put("signature", "https://memorynotfound.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);

        emailService.sendEmail(mail);

        return "redirect:/forgot-password?success";
    }
}
