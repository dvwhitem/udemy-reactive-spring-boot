package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxTest {

    @Test
    public void fluxTest() {

      Flux<String> reactiveFlux = Flux.just("Spring Boot", "Reactive Spring", "Reactive Kafka")
              //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
              .concatWith(Flux.just("After Error"))
              .log();
      reactiveFlux.subscribe(System.out::println, e -> System.err.println("Exception is "+ e), () -> System.out.println("Completed"));
    }
}
