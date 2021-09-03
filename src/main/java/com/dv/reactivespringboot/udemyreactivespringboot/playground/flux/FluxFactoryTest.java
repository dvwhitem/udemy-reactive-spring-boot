package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxFactoryTest {

    List<String> names = Arrays.asList(
            "Frank", "Georginio", "Kevin", "Keil"
    );

    @Test
    public void fluxUsingIterable() {
       Flux<String> stringFlux = Flux.fromIterable(names).log();
        StepVerifier.create(stringFlux)
                .expectNext("Frank", "Georginio", "Kevin", "Keil")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        String[] names = {"Frank", "Georginio", "Kevin", "Keil"};
        Flux<String> stringFlux = Flux.fromArray(names).log();
        StepVerifier
                .create(stringFlux)
                .expectNext("Frank", "Georginio", "Kevin", "Keil")
                .verifyComplete();
    }

    @Test
    public void fluxUsingStream() {
        Flux<String> streamFlux = Flux.fromStream(names.stream()).log();
        StepVerifier
                .create(streamFlux)
                .expectNext("Frank", "Georginio", "Kevin", "Keil")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {
        Flux<Integer> integerFlux = Flux.range(1, 7);
        StepVerifier
                .create(integerFlux.log())
                .expectNext(1,2,3,4,5,6,7)
                .verifyComplete();
    }
}
