package com.dv.reactivespringboot.udemyreactivespringboot.playground.mono;

import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Supplier;

public class MonoFactoryTest {

    @Test
    public void monoUsingJustOrEmpty() {
        Mono<String> stringMono = Mono.justOrEmpty(null);
        StepVerifier
                .create(stringMono.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "Janson";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
        StepVerifier
                .create(stringMono.log())
                .expectNext("Janson")
                .verifyComplete();
    }
}
