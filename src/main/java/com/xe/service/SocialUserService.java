package com.xe.service;

import com.xe.entity.SocialUser;
import com.xe.exception.UserNotFoundException;
import com.xe.repo.SocialRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class SocialUserService {
    private final SocialRepo repo;

    public SocialUserService(SocialRepo repo) {
        this.repo = repo;
    }


    public void addUserSocial(OAuth2AuthenticationToken socialUser) {

        Map<String, Object> attributes = socialUser.getPrincipal().getAttributes();

        if (!repo.findByEmailAndAndRegId((String) attributes.get("email"), socialUser.getAuthorizedClientRegistrationId()).isPresent()) {
            log.info("ENTERED TO SAVE");
            SocialUser user = new SocialUser((String) attributes.get("name"), (String) attributes.get("email"), socialUser.getAuthorizedClientRegistrationId());
            repo.save(user);
        }

    }

    public SocialUser findByEmailAndRegID(String email, String regId) {
        Optional<SocialUser> user = repo.findByEmailAndAndRegId(email, regId);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }


}
