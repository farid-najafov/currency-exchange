package com.xe.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xe.entity.SocialUser;
import com.xe.service.SocialUserService;
import com.xe.service.UserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExchangeHistoryController {

    private final UserService userService;
    private final SocialUserService socialUserService;

    public ExchangeHistoryController(UserService userService, SocialUserService socialUserService) {
        this.userService = userService;
        this.socialUserService = socialUserService;
    }

    @GetMapping
    public String get(Principal p, Model model) {

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            model.addAttribute("user", socialUserService.findByEmailAndRegID(user.getPrincipal().getAttribute("email"),
                    user.getAuthorizedClientRegistrationId()));
        } else {
            model.addAttribute("user", userService.findByEmail(p.getName()).orElse(null));
        }
        model.addAttribute("name", UserService.getUserNameFromPrincipal(p));
        return "history";
    }
}
