package com.ibatech.app.repo;

import com.ibatech.app.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeJpaRepo extends JpaRepository<Exchange,Integer> {
}
