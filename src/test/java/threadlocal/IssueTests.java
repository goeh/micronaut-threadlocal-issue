package threadlocal;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpHeaderValues;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class IssueTests {

    static EmbeddedServer embeddedServer;
    static RxHttpClient httpClient;

    @BeforeClass
    public static void setup() {
        embeddedServer = ApplicationContext.run(EmbeddedServer.class);
        httpClient = embeddedServer.getApplicationContext().createBean(RxHttpClient.class, embeddedServer.getURL());
    }

    @AfterClass
    public static void cleanup() {
        if (embeddedServer != null) {
            embeddedServer.stop();
        }
    }

    private MyClient getClient() {
        return embeddedServer.getApplicationContext().getBean(MyClient.class);
    }

    private String getAuthorizationHeader(String username, String password) {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
        HttpResponse<BearerAccessRefreshToken> resp = httpClient.toBlocking().exchange(HttpRequest.POST("/login", creds), BearerAccessRefreshToken.class);
        BearerAccessRefreshToken auth = resp.body();
        return HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER + " " + auth.getAccessToken();
    }

    @Test
    public void list1() {
        String auth = getAuthorizationHeader("user", "password");
        List<Message> result = getClient().list1(auth).blockingGet();
        assertFalse(result.isEmpty());
    }

    @Test
    public void list2() {
        String auth = getAuthorizationHeader("user", "password");
        List<Message> result = getClient().list2(auth).blockingGet();
        assertFalse(result.isEmpty());
    }
}
