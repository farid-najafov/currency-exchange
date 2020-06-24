package app.repo;

import app.entity.Exchange;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeJpaRepo extends JpaRepository<Exchange,Integer> {
}
