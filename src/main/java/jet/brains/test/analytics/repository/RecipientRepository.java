package jet.brains.test.analytics.repository;

import jet.brains.test.analytics.entity.Recipient;
import org.springframework.data.repository.CrudRepository;

public interface RecipientRepository extends CrudRepository<Recipient, String> {
    boolean existsByAddress(String address);
    Recipient getRecipientByAddress(String address);
}
