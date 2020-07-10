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

    @ModelAttribute("user")
    public User user() {
        return new User();
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
    public String handle_post(@Valid @ModelAttribute("user") User user,
                              BindingResult result,
                              RedirectAttributes ra) {

        if (result.hasErrors()) return "registration";

        Optional<User> existing = userService.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) return "registration";

        userService.addUser(user);

        ra.addFlashAttribute("success","Registration is successful, please log in to continue");
        log.info("Successfully registered");

        return "redirect:/login";
    }

}
