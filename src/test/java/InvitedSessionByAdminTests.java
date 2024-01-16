import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvitedSessionByAdminTests extends AdminSessionRequest {





    @Test
    public void verify_Admin_User1_same_Session() {

        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String sessionId1 = firstResponse.getBody();
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("user1:user1".getBytes()));
        RequestEntity< Object > user1request = new RequestEntity < > (headers, HttpMethod.GET, uri);
        ResponseEntity < String > user1Response =  restTemplate.exchange(user1request, String.class);
        assertEquals(sessionId1,user1Response.getBody());
    }


    @Test
    public void verify_Admin_User2_same_Session() {

        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String sessionId1 = firstResponse.getBody();
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("user2:user2".getBytes()));
        RequestEntity< Object > user2request = new RequestEntity < > (headers, HttpMethod.GET, uri);
        ResponseEntity < String > user2Response =  restTemplate.exchange(user2request, String.class);
        assertEquals(sessionId1,user2Response.getBody());
    }


    @Test
    public void verify_Admin_User3_same_Session() {

        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String sessionId1 = firstResponse.getBody();
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("user3:user3".getBytes()));
        RequestEntity< Object > user3request = new RequestEntity < > (headers, HttpMethod.GET, uri);
        ResponseEntity < String > user3resp =  restTemplate.exchange(user3request, String.class);
        assertEquals(sessionId1,user3resp.getBody());
    }
}
