package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluxMapTest {

    List<String> names = Arrays.asList("Henry", "Josh", "Kate", "Delgado", "Jenny");

    @Test
    public void transformUsingMapTest() {
        Flux<String> stringFlux = Flux.fromIterable(names)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("HENRY", "JOSH", "KATE", "DELGADO", "JENNY")
                .verifyComplete();
    }

    @Test
    public void transformUsingMapLengthTest() {
        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(String::length)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext(5, 4, 4, 7, 5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMapLengthRepeatTest() {
        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(String::length)
                .repeat(1)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext(5, 4, 4, 7, 5, 5, 4, 4, 7, 5)
                .verifyComplete();
    }

    @Test
    public void transformUsingMapFilterTest() {
        Flux<String> stringFlux = Flux.fromIterable(names)
                .filter(s -> s.length() > 4)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("HENRY", "DELGADO", "JENNY")
                .verifyComplete();
    }

}
