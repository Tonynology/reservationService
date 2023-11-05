package reservationService.reservation.domain.dto;

import lombok.*;
import reservationService.reservation.domain.model.Store;

/**
 * 점포 리스트 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreListResponseDto {

    private String name;
    private String location;
    private Double distance;
    private String description;
    private Double ratingAverage;

    public static StoreListResponseDto from(Store store) {
        return StoreListResponseDto.builder()
                .name(store.getName())
                .location(store.getLocation())
                .distance(store.getDistance())
                .description(store.getDescription())
                .build();
    }
}
