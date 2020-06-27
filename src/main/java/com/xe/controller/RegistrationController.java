package com.xe.controller;

import com.xe.entity.User;
import com.xe.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String handle_get(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String handle_post(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) {

        if (bindingResult.hasErrors()) return "registration";

        Optional<User> byEmail = userService.findByEmail(user.getEmail());

        if (byEmail.isPresent()) {
            model.addAttribute("user", new User());
            model.addAttribute("registrationError", "Email is already taken");
            log.warn("Duplicate email");
            return "registration";
        }

        userService.addUser(user);
        ra.addFlashAttribute("success","Registration is successful, please log in to continue");
        log.info("Successfully registered");

        return "redirect:";
    }


}
