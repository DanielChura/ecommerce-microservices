package com.daniel.common_exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp,
        String path,
        Map<String, String> errors) {

}
