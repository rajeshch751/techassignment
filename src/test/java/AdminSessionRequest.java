import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;
import java.util.UUID;

public class AdminSessionRequest {

    private TestRestTemplate testRestTemplate;
    String testUrl = "http://localhost:8080/";
    URI uri = URI.create(testUrl);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> firstRequest(RestTemplate restTemplate, URI uri) {
        HttpHeaders headers = new HttpHeaders();
        String uid = UUID.randomUUID().toString();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes()));
        headers.set("Invitation_id", uid);
        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        return restTemplate.exchange(request, String.class);
    }
}
