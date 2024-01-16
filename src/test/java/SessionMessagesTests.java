import com.tech.assignment.dto.RestaurantDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class SessionMessagesTests extends AdminSessionRequest {



      List<String>   sessionmsgs=Arrays.asList("user2:SINGAPORE_RESTARUNT","user3:BOONKENG_RESTARUNT","user1:BOMBAY_RESATRUNT");
    List<String>  adminFinalizedRestarunats=Arrays.asList("user2:SINGAPORE_RESTARUNT","user3:BOONKENG_RESTARUNT");
    public String submit_user_msgs() throws ExecutionException, InterruptedException {
        String uid=UUID.randomUUID().toString();
        ResponseEntity < String > firstResponse = firstRequest(restTemplate, uri);
        String cookie = firstResponse.getHeaders().getFirst("Set-Cookie");
        ExecutorService es = Executors.newFixedThreadPool(3);
         getListCallable(uid, cookie,"user1","user1","BOMBAY_RESATRUNT");
        getListCallable(uid, cookie,"user2","user2","SINGAPORE_RESTARUNT");
        getListCallable(uid, cookie,"user3","user3","BOONKENG_RESTARUNT");


        return cookie;

    }

    @Test
    public void view_session_msg_user1() throws ExecutionException, InterruptedException {
        String cookie=submit_user_msgs();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        String auth="user1"+":"+"user1";
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        String viewSession = testUrl + "viewSessionMsgs";
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.GET, URI.create(viewSession));
        ResponseEntity<String[]> userResp = restTemplate.exchange(requestMsg, String[].class);
        List<String> viewMsgLst=Arrays.asList(userResp.getBody());
        assertTrue(sessionmsgs.size() == viewMsgLst.size());
        assertTrue(viewMsgLst.containsAll(sessionmsgs));
    }


    @Test
    public void view_session_msg_user2() throws ExecutionException, InterruptedException {
        String cookie=submit_user_msgs();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        String auth="user2"+":"+"user2";
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        String viewSession = testUrl + "viewSessionMsgs";
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.GET, URI.create(viewSession));
        ResponseEntity<String[]> userResp = restTemplate.exchange(requestMsg, String[].class);
        List<String> viewMsgLst=Arrays.asList(userResp.getBody());
        assertTrue(sessionmsgs.size() == viewMsgLst.size());
        assertTrue(viewMsgLst.containsAll(sessionmsgs));
    }


    @Test
    public void view_session_msg_user3() throws ExecutionException, InterruptedException {
        String cookie=submit_user_msgs();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        String auth="user3"+":"+"user3";
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        String viewSession = testUrl + "viewSessionMsgs";
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.GET, URI.create(viewSession));
        ResponseEntity<String[]> userResp = restTemplate.exchange(requestMsg, String[].class);
        List<String> viewMsgLst=Arrays.asList(userResp.getBody());
        assertTrue(sessionmsgs.size() == viewMsgLst.size());
        assertTrue(viewMsgLst.containsAll(sessionmsgs));
    }

    @Test
    public void view_session_msg_admin() throws ExecutionException, InterruptedException {
        String cookie=submit_user_msgs();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        String auth="admin"+":"+"admin";
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        String viewSession = testUrl + "viewSessionMsgs";
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.GET, URI.create(viewSession));
        ResponseEntity<String[]> userResp = restTemplate.exchange(requestMsg, String[].class);
        List<String> viewMsgLst=Arrays.asList(userResp.getBody());
        assertTrue(sessionmsgs.size() == viewMsgLst.size());
        assertTrue(viewMsgLst.containsAll(sessionmsgs));

    }


    @Test
    public void view_session_msg_admin_endsession_finalize() throws ExecutionException, InterruptedException {
        String cookie=submit_user_msgs();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookie);
        String auth="admin"+":"+"admin";
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
        String finalSession = testUrl + "adminfinalizedrestaurant";
        RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.GET, URI.create(finalSession));
        ResponseEntity<String[]> userResp = restTemplate.exchange(requestMsg, String[].class);
        List<String> viewMsgLst=Arrays.asList(userResp.getBody());
       // System.out.println(viewMsgLst);
        assertNotNull(viewMsgLst);

    }



    private Boolean getListCallable(String uid, String cookie,String userName, String Password,String restaruntName) {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", cookie);
            headers.set("Invitation_id", uid);
            String auth=userName+":"+Password;
            headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
            String saveMsg = testUrl + "submitMsg?msg=" + restaruntName;
            RequestEntity<Object> requestMsg = new RequestEntity<>(headers, HttpMethod.GET, URI.create(saveMsg));
            ResponseEntity<Boolean> userResp = restTemplate.exchange(requestMsg, Boolean.class);

            return userResp.getBody();

    }


}
