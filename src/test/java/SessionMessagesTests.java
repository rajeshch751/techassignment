import com.tech.assignment.dto.RestaurantDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionMessagesTests extends AdminSessionRequest {


    @Test
    public void view_User1_Msg_By_Admin() {
        String uid=UUID.randomUUID().toString();
        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String sessionId1 = firstResponse.getBody();
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Invitation_id",uid);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("user1:user1".getBytes()));
      //  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String restaruntName="BOMBAYRESTARUANT";

        String saveMsg = testUrl+"persistMessageRest?msg="+restaruntName;
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.POST, URI.create(saveMsg));
        String encoding = Base64.getEncoder().encodeToString(("user1" + ":" + "user1").getBytes());
        restTemplate.exchange(requestMsg, String.class);
      //  System.out.println(encoding);
      //  String authHeader = "Basic " + encoding;
      //  System.out.println(restTemplate.exchange(requestMsg, String.class));
    }

    @Test
    public void view_User2_Msg_By_Admin() {

        String uid=UUID.randomUUID().toString();
        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String sessionId1 = firstResponse.getBody();
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Invitation_id",uid);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("user2:user2".getBytes()));
        //  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String restaruntName="SINGAPORERESTARUNT";

        String saveMsg = testUrl+"persistMessageRest?msg="+restaruntName;
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.POST, URI.create(saveMsg));
       // String encoding = Base64.getEncoder().encodeToString(("user1" + ":" + "user1").getBytes());
        restTemplate.exchange(requestMsg, String.class);
    }


    @Test
    public void view_User3_Msg_By_Admin() {

        String uid=UUID.randomUUID().toString();
        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String sessionId1 = firstResponse.getBody();
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        headers.set("Invitation_id",uid);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("user3:user3".getBytes()));
        //  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String restaruntName="BOONKENGRESTARUNT";

        String saveMsg = testUrl+"persistMessageRest?msg="+restaruntName;
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.POST, URI.create(saveMsg));
       // String encoding = Base64.getEncoder().encodeToString(("user1" + ":" + "user1").getBytes());
        restTemplate.exchange(requestMsg, String.class);
    }


}
