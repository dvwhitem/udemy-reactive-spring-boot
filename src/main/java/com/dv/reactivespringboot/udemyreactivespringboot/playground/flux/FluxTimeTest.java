package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class FluxTimeTest {

    @Test
    public void infiniteSequence() throws InterruptedException {
        Flux<Long> infiniteFlux = Flux
                .interval(Duration.ofMillis(200))
                .log(); //start from 0 -> ...N

        infiniteFlux.subscribe((element) -> log.info("Value is {}", element));
        Thread.sleep(3000);
    }

    @Test
    public void infiniteSequenceTakeElements() throws InterruptedException {
        Flux<Long> infiniteFlux = Flux
                .interval(Duration.ofMillis(200))
                .take(3)
                .log(); //start from 0 -> ...N

        StepVerifier.create(infiniteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete();

    }

    @Test
    public void infiniteSequenceMap() throws InterruptedException {
        Flux<Integer> infiniteFlux = Flux
                .interval(Duration.ofMillis(200))
                .map(Long::intValue)
                .take(3)
                .log(); //start from 0 -> ...N

        StepVerifier.create(infiniteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();

    }

    @Test
    public void infiniteSequenceWithDelay() {
        Flux<Integer> infiniteFlux = Flux
                .interval(Duration.ofMillis(200))
                .delayElements(Duration.ofSeconds(1))
                .map(Long::intValue)
                .take(3)
                .log(); //start from 0 -> ...N

        StepVerifier.create(infiniteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();

    }
}
