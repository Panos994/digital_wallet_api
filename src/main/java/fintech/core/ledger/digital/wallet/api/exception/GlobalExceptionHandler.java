package fintech.core.ledger.digital.wallet.api.exception;

import fintech.core.ledger.digital.wallet.api.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ApiErrorDTO apiErrorDTO = ApiErrorDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(404).body(apiErrorDTO);
    }
}
