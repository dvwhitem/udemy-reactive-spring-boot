package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FluxFilterTest {

    List<String> names = Arrays.asList("Henry", "Josh", "Kate", "Delgado", "Jenny");

    @Test
    public void filterTest() {
        Flux<String> stringFlux = Flux.fromIterable(names)
                .map(String::toLowerCase)
                .filter(s -> s.startsWith("j"))
                .filter(s -> s.length() > 4)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("jenny")
                .verifyComplete();

    }
}
