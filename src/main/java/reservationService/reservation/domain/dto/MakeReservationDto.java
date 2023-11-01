package reservationService.reservation.domain.dto;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakeReservationDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "날짜와 시간은 필수 항목입니다.")
    @ApiParam(value = "예약 날짜와 시간", example = "2024-01-01T09:30:00")
    LocalDateTime reservationTime;

    String phoneNumber;

    private String storeName;
}
