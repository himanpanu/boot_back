package rating.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rating.app.model.ErrorModel;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { HttpClientErrorException.class})
    protected ResponseEntity<ErrorModel> handleHttpException(
            Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorModel.builder().error(ex.getMessage()).status(HttpStatus.BAD_REQUEST.value()).build());
    }

}
