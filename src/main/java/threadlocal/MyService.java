package threadlocal;

import io.reactivex.Single;

import javax.inject.Singleton;

@Singleton
public class MyService {

    private final MyClient client;

    public MyService(MyClient client) {
        this.client = client;
    }

    public Single<Message> getDetails(String name) {
        return client.details(name);
    }

}
