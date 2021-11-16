package com.example.reactiveapi.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void fluxApproach1() {
        Flux<Integer> integerFlux = webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier
                .create(integerFlux)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();

    }

    @Test
    public void fluxApproach2() {
        webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    public void fluxApproach3() {

        var expectedIntegerList = Arrays.asList(1, 2, 3, 4);

        var integerFlux = webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectBodyList(Integer.class)
                .returnResult();

        assertEquals(expectedIntegerList, integerFlux.getResponseBody());
    }

    @Test
    public void fluxApproach4() {

        var expectedIntegerList = Arrays.asList(1, 2, 3, 4);

        webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectBodyList(Integer.class)
                .consumeWith(response -> {
                    assertEquals(expectedIntegerList, response.getResponseBody());
                });

    }
}
