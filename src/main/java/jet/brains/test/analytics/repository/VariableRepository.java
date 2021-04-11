package jet.brains.test.analytics.repository;

import jet.brains.test.analytics.entity.Template;
import jet.brains.test.analytics.entity.Variable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VariableRepository extends CrudRepository<Variable, Integer> {
    List<Variable> findByTemplate(Template template);
}
