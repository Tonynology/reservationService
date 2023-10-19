package reservationService.reservation.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * 점포 등록 정보
 */
public class AddStoreDto {

    private String email;

    private String name;

    private String location;

    private double distance;

    private String description;
}
