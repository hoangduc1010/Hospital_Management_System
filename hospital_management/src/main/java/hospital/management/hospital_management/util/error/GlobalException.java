package hospital.management.hospital_management.util.error;


import hospital.management.hospital_management.dto.response.RestResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value={CustomException.class})
    public ResponseEntity<RestResponseDTO<Object>> customException(CustomException e) {
        RestResponseDTO<Object> restResponseException = new RestResponseDTO<>();
        restResponseException.setStatusCode(HttpStatus.BAD_REQUEST.value());
        restResponseException.setErrorMessage(e.getMessage());
        restResponseException.setMessage("Exception !");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponseException);
    }
    @ExceptionHandler(value ={ConstraintViolationException.class})
    public ResponseEntity<RestResponseDTO<Object>> violationException(ConstraintViolationException e) {
        RestResponseDTO<Object> restResponseException = new RestResponseDTO<>();
        restResponseException.setStatusCode(HttpStatus.BAD_REQUEST.value());
        restResponseException.setErrorMessage(e.getMessage());
        restResponseException.setMessage("Exception !");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponseException);
    }

}
