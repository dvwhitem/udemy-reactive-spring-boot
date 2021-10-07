package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class FluxBackpressureTest {

    @Test
    public void backpressureTest() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenRequest(1)
                .expectNext(3)
                .thenCancel()
                .verify();
    }

    @Test
    public void backpressureTest2() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux
                .subscribe(
                        integer -> log.info("Element is : {}", integer),
                        e -> log.error("Exception is: {}", e),
                        () -> log.info("Done."),
                        subscription -> subscription.request(4)
                );
    }

    @Test
    public void backpressureTestCancel() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux
                .subscribe(
                        integer -> log.info("Element is : {}", integer),
                        e -> log.error("Exception is: {}", e),
                        () -> log.info("Done."),
                        Subscription::cancel
                );
    }

    @Test
    public void customizeDebugBackpressure() {
        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        request(1);
                        log.info("Value received is {} ", value);
                        if(value == 4) cancel();
                    }
                });
    }
}
