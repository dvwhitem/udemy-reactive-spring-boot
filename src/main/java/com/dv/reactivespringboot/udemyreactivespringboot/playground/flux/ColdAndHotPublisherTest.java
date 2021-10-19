package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe(s -> log.info("Subscriber 1: {}", s));

        Thread.sleep(2000);

        stringFlux.subscribe(s -> log.info("Subscriber 2: {}", s));

        Thread.sleep(8000);
    }

    @Test
    public void hotPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(s -> log.info("Subscriber 1: {}", s));
        Thread.sleep(1000);
        connectableFlux.subscribe(s -> log.info("Subscriber 2: {}", s));
        Thread.sleep(3000);
    }
}
