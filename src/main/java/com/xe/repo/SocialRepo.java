package com.xe.repo;

import com.xe.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialRepo extends JpaRepository<SocialUser, Long> {

    Optional<SocialUser> findByEmailAndAndRegId(String email, String regId);

}
