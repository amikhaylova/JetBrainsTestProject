package jet.brains.test.analytics.repository;

import jet.brains.test.analytics.entity.RepeatedMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RepeatedMessageRepository extends CrudRepository<RepeatedMessage, Integer> {
   List<RepeatedMessage> findAll();
}
