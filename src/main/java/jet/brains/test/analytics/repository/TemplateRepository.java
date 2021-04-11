package jet.brains.test.analytics.repository;

import jet.brains.test.analytics.entity.Template;
import org.springframework.data.repository.CrudRepository;

public interface TemplateRepository extends CrudRepository<Template, String> {
    Template getTemplatesByTemplateId(String templateID);
}