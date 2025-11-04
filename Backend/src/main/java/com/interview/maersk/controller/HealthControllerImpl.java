package com.interview.maersk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@Slf4j
public class HealthControllerImpl implements HealthController {

    public Mono<ResponseEntity<String>> healthCheck() {
        log.info("Health check endpoint called");
        return Mono.just(ResponseEntity.ok("Service is up and running"));
    }
}
