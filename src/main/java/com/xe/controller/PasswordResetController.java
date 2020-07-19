package com.xe.controller;

import com.xe.dto.PasswordResetDto;
import com.xe.entity.PasswordResetToken;
import com.xe.entity.User;
import com.xe.repo.PasswordResetTokenRepository;
import com.xe.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@AllArgsConstructor
@Log4j2
@Controller
@RequestMapping("/reset-password")
public class PasswordResetController {

    private final UserService userService;
    private final PasswordResetTokenRepository tokenRepository;

    @ModelAttribute("passwordResetForm")
    public PasswordResetDto passwordReset() {
        return new PasswordResetDto();
    }

    @GetMapping
    public String displayResetPasswordPage(@RequestParam(required = false) String token,
                                           Model model, RedirectAttributes redirectAttributes) {

        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null) {
            model.addAttribute("error", "Could not find password reset token.");
        } else if (resetToken.isExpired() || resetToken.isUsed()) {
            redirectAttributes.addFlashAttribute("err", "Token has expired or used , please request a new password " +
                    "reset.");
            return "redirect:forgot-password";
        } else {
            model.addAttribute("token", resetToken.getToken());
        }

        model.addAttribute("passwordResetForm", new PasswordResetDto());
        return "reset-password";
    }

    @PostMapping
    @Transactional
    public String handlePasswordReset(@Valid @ModelAttribute("passwordResetForm") PasswordResetDto form,
                                      BindingResult result,
                                      RedirectAttributes ra) {

        if (result.hasErrors()) {
            ra.addFlashAttribute("error", "Please follow the password policy: \n" +
                    "(Password must be at least 3 characters long and fields must match)");
            return "redirect:/reset-password?token=" + form.getToken();
        }

        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        User user = token.getUser();
        String updatedPassword = form.getPassword();
        userService.updatePassword(updatedPassword, user.getId());
        token.setUsed(true);
        tokenRepository.save(token);
        ra.addFlashAttribute("success", "Password successfully reset, please log in to continue");
        log.info("Successfully registered");
        return "redirect:/login";
    }

    @ExceptionHandler({Exception.class})
    public RedirectView handleErr(RedirectAttributes ra) {
        ra.addFlashAttribute("msg", "Please choose correct details to convert");
        return new RedirectView("/main-page");
    }
}

