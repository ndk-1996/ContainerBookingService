package com.interview.maersk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Tag(name = "Service health API", description = "Endpoint for service health check")
public interface HealthController {

    @Operation(summary = "Check the health status of the service")
    @GetMapping()
    Mono<ResponseEntity<String>> healthCheck();
}
