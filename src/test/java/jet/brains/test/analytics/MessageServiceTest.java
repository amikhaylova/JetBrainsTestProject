package jet.brains.test.analytics;

import jet.brains.test.analytics.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Test
    public void formMessageTest() {
        String template = "This is $var1$ test. And it is the $var2$ test in the world!.";
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("var1", "formMessage");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("var2", "best");
        List<Map<String, String>> variables = new ArrayList<>();
        variables.add(map1);
        variables.add(map2);
        String actual = messageService.formMessage(template, variables);
        String expected = "This is formMessage test. And it is the best test in the world!.";
        assertEquals(actual, expected);
    }

    @Test
    public void correctSendToEndpointTest() {
        ResponseEntity<String> jsonResponse = messageService.sendMessage("https://postman-echo.com/post", "test string");
        String json = jsonResponse.toString();
        assertTrue(json.contains("test string"));
    }

}
