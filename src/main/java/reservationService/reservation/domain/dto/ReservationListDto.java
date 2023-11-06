package reservationService.reservation.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationListDto {

    Long reservationNumber;
    String storeName;
    String phoneNumber;
    boolean isValid;
    LocalDateTime time;
}
