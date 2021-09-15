package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class FluxErrorTest {

    @Test
    public void fluxErrorHandling() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorResume(e -> {  // this block get executed
                    log.info("Exception is: " + e);
                    return Flux.just("Default value", "Default value2");
                })
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C")
                .expectNext("Default value", "Default value2")
               // .expectError(RuntimeException.class)
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandlingOnErrorReturn() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("Default")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C")
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandlingOnErrorMap() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandlingOnErrorMapWithRetry() {
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new)
                .retry(2)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();
    }

}
