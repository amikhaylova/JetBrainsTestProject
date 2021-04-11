package jet.brains.test.analytics.service;

import jet.brains.test.analytics.dto.MessageDTO;
import jet.brains.test.analytics.dto.SendMessageRequestDTO;
import jet.brains.test.analytics.entity.Recipient;
import jet.brains.test.analytics.entity.RepeatedMessage;
import jet.brains.test.analytics.entity.Template;
import jet.brains.test.analytics.exception.NoSuchTemplateException;
import jet.brains.test.analytics.repository.RepeatedMessageRepository;
import jet.brains.test.analytics.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;


@Service
public class MessageService {
    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    RepeatedMessageRepository messageRepository;

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
        if (requestDTO.getRepeat()) {
            RepeatedMessage repeatedMessage = new RepeatedMessage();
            repeatedMessage.setText(message);
            repeatedMessage.setTemplate(template);
            messageRepository.save(repeatedMessage);
        }
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

    public ResponseEntity<String> sendMessage(String address, String message) throws ResourceAccessException {
        MessageDTO messageDTO = new MessageDTO(message);
        HttpEntity<MessageDTO> request = new HttpEntity<>(messageDTO);
        ResponseEntity<String> response = restTemplate
                .exchange(address, HttpMethod.POST, request, String.class);
        System.out.println(response);
        return response;
    }


    //additional task concerning saved messages sending every 10 minutes
    @Transactional
    @Scheduled(fixedDelay = 600000)
    public void scheduleRepeatedMessage() {
        List<RepeatedMessage> repeatedMessages = messageRepository.findAll();
        for (RepeatedMessage message : repeatedMessages) {
            String messageText = message.getText();
            Template template = message.getTemplate();
            List<Recipient> recipients = template.getRecipients();
            for (Recipient recipient : recipients) {
                try {
                    sendMessage(recipient.getAddress(), messageText);
                } catch (ResourceAccessException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

}
