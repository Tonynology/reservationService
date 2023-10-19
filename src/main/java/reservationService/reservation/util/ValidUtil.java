package reservationService.reservation.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidUtil {

    public static ResponseEntity<?> responseErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> {
            sb.append(error.getField() + ": " + error.getDefaultMessage() + "\n");
        });
        return new ResponseEntity<>(new String(sb), HttpStatus.BAD_REQUEST);
    }
}
