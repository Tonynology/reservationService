package reservationService.reservation.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 점포 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddStoreDto {

    @Size(min = 11, message = "점주님의 이메일을 입력해주세요.")
    private String email;

    @Size(min = 2, message = "점주님의 이름을 입력해주세요.")
    private String name;

    @Size(min = 3, message = "점포 위치를 입력해주세요.")
    private String location;

    @NotNull(message = "점포까지의 거리를 입려하세요.")
    private double distance;

    @Size(min = 5, message = "점포를 설명해주세요.")
    private String description;
}
