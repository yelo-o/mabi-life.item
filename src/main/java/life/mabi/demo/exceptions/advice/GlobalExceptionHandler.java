package life.mabi.demo.exceptions.advice;

import life.mabi.demo.exceptions.ErrorObject;
import life.mabi.demo.exceptions.MyResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MyResourceException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(MyResourceException exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(exception.getStatusCode());
        errorObject.setMessage(exception.getMessage());
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatusCode.valueOf(exception.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorObject> handleException(Exception e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(e.getMessage());
        log.error(e.getMessage(), e);

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public void accessDeniedExceptionHandler(Exception e) {
        throw new AccessDeniedException(e.getMessage());
    }

    //401
    @ExceptionHandler(value = BadCredentialsException.class)
    public void badCredentialExceptionHandler(BadCredentialsException e){
        throw new BadCredentialsException(e.getMessage());
    }

}
