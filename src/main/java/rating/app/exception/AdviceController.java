package rating.app.exception;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoServerException;
import com.mongodb.MongoWriteException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rating.app.model.GlobalResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class AdviceController  extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> errorResponse(HttpStatus status, HttpHeaders headers, String message) {
        final GlobalResponse apiError = GlobalResponse.builder()
                .message(message)
                .isSuccess(false)
                .build();

        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        return ResponseEntity.status(status).headers(headers).body(apiError);
    }

    @ExceptionHandler({ HttpClientErrorException.class })
    final ResponseEntity<Object> handleHttpException(Exception ex) {

        final HttpStatus status;
        String message;
        if (ex instanceof HttpStatusCodeException) {
            status = ((HttpStatusCodeException)ex).getStatusCode();
            message = ((HttpStatusCodeException)ex).getStatusText();
            if("".equals(message)) message = "Not Found";
        } else {
            message = ex.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return errorResponse(status, new HttpHeaders(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception, WebRequest request) {
        Set<ConstraintViolation<?>> violations =  exception.getConstraintViolations();
        if(violations != null && violations.size() > 1) {
            Optional<String> message = violations.stream().map(cv -> cv.getMessage()).findFirst();
            return errorResponse(HttpStatus.BAD_REQUEST, new HttpHeaders(), message.get());
        }else {
            Optional<ConstraintViolation<?>> constraintViolation =  exception.getConstraintViolations().stream().findFirst();
            if(constraintViolation.isPresent()) {
                return errorResponse(HttpStatus.BAD_REQUEST, new HttpHeaders(), constraintViolation.get().getMessage());
            }
        }
        return errorResponse(HttpStatus.BAD_REQUEST, new HttpHeaders(), exception.getMessage());
    }

    @ExceptionHandler(MongoServerException.class)
    public ResponseEntity<Object> handleConstraintViolation(MongoServerException exception, WebRequest request) {
        final HttpStatus status;
        String message;
        if (exception instanceof DuplicateKeyException) {
            status = HttpStatus.CONFLICT;
            message = "Already exists";
        } else {
            message = exception.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return errorResponse(status, new HttpHeaders(), message);
    }


    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            @NonNull WebRequest request
    ) {
        return errorResponse(status, headers, ex.getMessage());
    }


}
