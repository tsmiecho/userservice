package online.smiechowicz.service;

import online.smiechowicz.domain.PathConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class ApplicationTest {

    @Autowired
    private TestRestTemplate client;

    @Test
    public void testSayHello() {
        ResponseEntity<String> response = client.getForEntity(PathConstants.USERS, String.class);
//        assertEquals("Hello World", response.getBody());
    }
}
