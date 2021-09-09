package com.dv.reactivespringboot.udemyreactivespringboot.playground.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxFlatMapTest {

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s));
                }).log();

        StepVerifier.create(stringFlux).
                expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapParallel() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2) // Flux<Flux<String>> -> (A,B), (C,D), (E,F)
                .flatMap((s) ->
                        s.map(this::convertToList).subscribeOn(parallel())
                                .flatMap(Flux::fromIterable)// Flux<List<String>>
                ).log();

        StepVerifier.create(stringFlux).
                expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapParallelOrder() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2) // Flux<Flux<String>> -> (A,B), (C,D), (E,F)
                .concatMap(s -> s.map(this::convertToList).subscribeOn(parallel())) // Flux<List<String>>
                //.flatMapSequential(s -> s.map(this::convertToList).subscribeOn(parallel())) // Flux<List<String>>
                .flatMap(Flux::fromIterable).log(); // Flux<String>

        StepVerifier.create(stringFlux).
                expectNextCount(12)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "new Value");
    }

}
