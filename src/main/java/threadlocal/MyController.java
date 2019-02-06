package threadlocal;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.List;

@Controller("/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MyController {

    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @Get("/list1")
    public Single<List<Message>> list1(Principal principal) {
        return Flowable.just("Goran", "Sergio")
                .flatMapSingle(name -> service.getDetails(name)).toList();
    }

    @Get("/list2")
    public Flowable<Message> list2(Principal principal) {
        return Flowable.just("Goran", "Sergio")
                .flatMapSingle(name -> service.getDetails(name));
    }

    @Get("/details/{name}")
    public Single<Message> details(String name) {
        log.info("Get details for {}", name);
        return Single.just(new Message("Hello " + name));
    }

}
