package threadlocal;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

import java.util.List;

@Client("/")
@Header(name = "tenantId", value = "test")
public interface MyClient {

    @Get("/list1")
    Single<List<Message>> list1(@Header(HttpHeaders.AUTHORIZATION) String token);

    @Get("/list2")
    Single<List<Message>> list2(@Header(HttpHeaders.AUTHORIZATION) String token);

    @Get("/details/{name}")
    Single<Message> details(String name);
}
