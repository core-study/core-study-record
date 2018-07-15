package com.tmon.practice.webflux.controller;

import com.tmon.practice.webflux.model.Greet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GreetingController {
    @RequestMapping("/")
    Mono<Greet> greet() {
        return Mono.just(new Greet("Hello world!"));
    }
}
