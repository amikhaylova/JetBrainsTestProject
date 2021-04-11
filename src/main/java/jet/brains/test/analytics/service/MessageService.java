package jet.brains.test.analytics.service;

import jet.brains.test.analytics.dto.MessageDTO;
import jet.brains.test.analytics.dto.SendMessageRequestDTO;
import jet.brains.test.analytics.entity.Recipient;
import jet.brains.test.analytics.entity.Template;
import jet.brains.test.analytics.exception.NoSuchTemplateException;
import jet.brains.test.analytics.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;


@Service
public class MessageService {
    @Autowired
    TemplateRepository templateRepository;

    private final RestTemplate restTemplate;

    public MessageService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public boolean sendMessage(SendMessageRequestDTO requestDTO) throws NoSuchTemplateException {
        String templateId = requestDTO.getTemplateId();
        Template template = templateRepository.getTemplatesByTemplateId(templateId);
        if (template == null) {
            throw new NoSuchTemplateException(templateId);
        }
        List<Recipient> recipients = template.getRecipients();
        String message = formMessage(template.getTemplate(), requestDTO.getVariables());
        for (Recipient recipient : recipients) {
            sendMessage(recipient.getAddress(), message);
        }
        return true;
    }

    public String formMessage(String template, List<Map<String, String>> variables) {
        String message = template;
        for (Map<String, String> map : variables) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                message = message.replace("$" + entry.getKey() + "$", entry.getValue());
            }
        }
        return message;
    }

    public ResponseEntity<String> sendMessage(String address, String message) {
        MessageDTO messageDTO = new MessageDTO(message);
        HttpEntity<MessageDTO> request = new HttpEntity<>(messageDTO);
        ResponseEntity<String> response = restTemplate
                .exchange(address, HttpMethod.POST, request, String.class);
        return response;
    }

}
