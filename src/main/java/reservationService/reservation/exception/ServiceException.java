package reservationService.reservation.exception;

import reservationService.reservation.exception.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
