package kr.co.hanbit.product.management.presentation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //전역예외핸들러

    @ExceptionHandler(ConstraintViolationException.class)
    //도메인객체에서 유효성검사 실패 시 예외 처리 핸들러
    public ResponseEntity<ErrorMessage> handleConstraintViolatedException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream()
                .map(
                        constraintViolation ->
                                extractField(constraintViolation.getPropertyPath()) +
                                        ", " + constraintViolation.getMessage()
                )
                .toList();

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);

    }

    private String extractField(Path path) {
        String[] splittedArray = path.toString().split("[.]");
        int lastIndex = splittedArray.length - 1;
        return splittedArray[lastIndex];
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //컨트롤러에서 유효성검사 실패 시 예외 처리 핸들러
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(
                        fieldError ->
                                fieldError.getField() + ", " + fieldError.getDefaultMessage()
                )
                .toList();

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    //EntityNotFoundException에 대한 예외 처리 핸들러
    public ResponseEntity<ErrorMessage> handleEntityNotFoundExceptionException(EntityNotFoundException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorMessage errorMessage= new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
