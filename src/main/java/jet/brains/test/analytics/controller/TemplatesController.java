package jet.brains.test.analytics.controller;


import jet.brains.test.analytics.dto.SendMessageRequestDTO;
import jet.brains.test.analytics.dto.UploadRequestDTO;
import jet.brains.test.analytics.service.MessageService;
import jet.brains.test.analytics.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TemplatesController {
    @Autowired
    TemplateService templateService;

    @Autowired
    MessageService messageService;

    @PostMapping("/upload_template")
    public void uploadTemplate(@Valid @RequestBody UploadRequestDTO requestDTO) {
        templateService.uploadTemplate(requestDTO);
    }

    @PostMapping("/send_messages")
    public void sendMessages(@Valid @RequestBody SendMessageRequestDTO requestDTO) {
        messageService.sendMessage(requestDTO);
    }

}
