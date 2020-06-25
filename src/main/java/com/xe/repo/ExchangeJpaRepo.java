package com.xe.repo;

import com.xe.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeJpaRepo extends JpaRepository<Exchange,Integer> {
}
