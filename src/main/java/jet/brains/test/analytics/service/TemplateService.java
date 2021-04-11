package jet.brains.test.analytics.service;

import jet.brains.test.analytics.dto.UploadRequestDTO;
import jet.brains.test.analytics.entity.Template;
import jet.brains.test.analytics.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {
    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    RecipientService recipientService;

    public boolean uploadTemplate(UploadRequestDTO requestDTO) {
        Template template = new Template();
        template.setTemplateId(requestDTO.getTemplateId());
        template.setTemplate(requestDTO.getTemplate());
        template.setRecipients(recipientService.transferFromStringListToRecipientList(requestDTO.getRecipients()));
        templateRepository.save(template);
        return true;
    }
}
