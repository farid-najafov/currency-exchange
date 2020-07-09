package com.xe.controller;

import com.xe.dto.PasswordResetDto;
import com.xe.entity.PasswordResetToken;
import com.xe.entity.User;
import com.xe.repo.PasswordResetTokenRepository;
import com.xe.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

    private final UserService userService;
    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetController(UserService userService, PasswordResetTokenRepository tokenRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @ModelAttribute("passwordResetFrom")
    public PasswordResetDto passwordReset() {
        return new PasswordResetDto();
    }

    @GetMapping
    public String displayResetPasswordPage(@RequestParam(required = false) String token,
                                           Model model) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null) {
            model.addAttribute("error","Could not find password reset token.");
        } else if (resetToken.isExpired()) {
            model.addAttribute("error", "Token has expired, please request a new password reset.");
        } else {
            model.addAttribute("token", resetToken.getToken());
        }

        model.addAttribute("passwordResetFrom", new PasswordResetDto());
        return "reset-password";
    }

    @PostMapping
    @Transactional
    public String handlePasswordReset(@Valid @ModelAttribute("passwordResetForm") PasswordResetDto form,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "redirect:/reset-password?token=" + form.getToken();

        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        User user = token.getUser();
        String updatedPassword = form.getPassword();
        userService.updatePassword(updatedPassword, user.getId());
        tokenRepository.delete(token);

        redirectAttributes.addFlashAttribute("success","Password successfully reset, please log in to continue");
        log.info("Successfully registered");

        return "redirect:/login";
    }
}

