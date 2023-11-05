package reservationService.reservation.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 매장 방문 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitedStoreDto {

    String phoneNumber;
    String storeName;
    LocalDateTime visitTime;
}
