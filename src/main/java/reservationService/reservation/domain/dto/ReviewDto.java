package reservationService.reservation.domain.dto;

import lombok.*;

/**
 * 고객 리뷰
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private int rating;
    private String contents;
}
