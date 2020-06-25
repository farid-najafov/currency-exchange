package com.ibatech.app.repo;

import com.ibatech.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepo extends JpaRepository<User,Integer> {
}
