package dev.kalbarczyk.sportsmanager.competitor.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class CompetitorExceptionHandler {

    @ExceptionHandler(CompetitorException.Invalid.class)
    public ResponseEntity<Map<String, Object>> badRequest(CompetitorException.Invalid ex) {
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessages()));
    }
}
