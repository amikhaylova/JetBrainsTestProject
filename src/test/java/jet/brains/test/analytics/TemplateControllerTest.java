package jet.brains.test.analytics;

import com.google.gson.Gson;
import jet.brains.test.analytics.controller.TemplatesController;
import jet.brains.test.analytics.dto.SendMessageRequestDTO;
import jet.brains.test.analytics.dto.UploadRequestDTO;
import jet.brains.test.analytics.service.MessageService;
import jet.brains.test.analytics.service.TemplateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TemplatesController.class)
public class TemplateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TemplateService templateService;

    @MockBean
    MessageService messageService;

    private SendMessageRequestDTO sendMessageRequestDTO;
    private UploadRequestDTO uploadRequestDTO;

    @BeforeEach
    void setUp() {
        sendMessageRequestDTO = new SendMessageRequestDTO();
        List<Map<String, String>> variables = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("teamName", "Analytics Platform");
        variables.add(map);
        sendMessageRequestDTO.setVariables(variables);
        uploadRequestDTO = new UploadRequestDTO();
        uploadRequestDTO.setTemplate("Jetbrains Internship in $teamName$ team.");
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add("http://some.server.url/endpoint");
        recipients.add("http://some.other.url/endpoint");
        uploadRequestDTO.setRecipients(recipients);
    }

    @AfterEach
    void tearDown() {
        sendMessageRequestDTO = null;
        uploadRequestDTO = null;
    }

    @Test
    void uploadTemplateTest200() throws Exception {
        given(templateService.uploadTemplate(any())).willReturn(true);
        uploadRequestDTO.setTemplateId("internshipRequest");
        Gson gson = new Gson();
        String json = gson.toJson(uploadRequestDTO);
        mockMvc.perform(
                post("/upload_template")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void uploadTemplateTestMissingParameter() throws Exception {
        given(templateService.uploadTemplate(any())).willReturn(true);
        Gson gson = new Gson();
        String json = gson.toJson(uploadRequestDTO);
        mockMvc.perform(
                post("/upload_template")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(400));
    }

    @Test
    void uploadTemplateTestEmptyParameter() throws Exception {
        given(templateService.uploadTemplate(any())).willReturn(true);
        uploadRequestDTO.setTemplateId("internshipRequest");
        uploadRequestDTO.setTemplate("");
        Gson gson = new Gson();
        String json = gson.toJson(uploadRequestDTO);
        mockMvc.perform(
                post("/upload_template")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(400));
    }

    @Test
    void sendMessagesTest200() throws Exception {
        given(messageService.sendMessage(any())).willReturn(true);
        sendMessageRequestDTO.setTemplateId("internshipRequest");
        Gson gson = new Gson();
        String json = gson.toJson(sendMessageRequestDTO);
        mockMvc.perform(
                post("/send_messages")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(200));
    }

    @Test
    void sendMessagesTestMissingParameter() throws Exception {
        given(messageService.sendMessage(any())).willReturn(true);
        Gson gson = new Gson();
        String json = gson.toJson(sendMessageRequestDTO);
        mockMvc.perform(
                post("/send_messages")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(400));
    }

    @Test
    void sendMessagesTestEmptyParameter() throws Exception {
        given(messageService.sendMessage(any())).willReturn(true);
        sendMessageRequestDTO.setTemplateId("");
        Gson gson = new Gson();
        String json = gson.toJson(sendMessageRequestDTO);
        mockMvc.perform(
                post("/send_messages")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(400));
    }
}
