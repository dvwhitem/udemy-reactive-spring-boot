package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void fluxTest() {

      Flux<String> reactiveFlux = Flux.just("Spring Boot", "Reactive Spring", "Reactive Kafka")
              //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
              .concatWith(Flux.just("After Error"))
              .log();
      reactiveFlux.subscribe(System.out::println, e -> System.err.println("Exception is "+ e), () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElementsWithoutError() {
        Flux<String> stringFlux = Flux.just("Spring Boot", "Spring Reactive", "Reactive Kafka")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring Boot")
                .expectNext("Spring Reactive")
                .expectNext("Reactive Kafka")
                .verifyComplete();
    }

    @Test
    public void fluxTestElementsWithError() {
        Flux<String> stringFlux = Flux.just("Spring Boot", "Spring Reactive", "Reactive Kafka")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring Boot")
                .expectNext("Spring Reactive")
                .expectNext("Reactive Kafka")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsWithError2() {
        Flux<String> stringFlux = Flux.just("Spring Boot", "Spring Reactive", "Reactive Kafka")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring Boot", "Spring Reactive", "Reactive Kafka")
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsCountWithError() {
        Flux<String> stringFlux = Flux.just("Spring Boot", "Spring Reactive", "Reactive Kafka")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }
}
