package reservationService.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PARTNER_ALREADY_EXIST(400, "이미 존재하는 점주입니다"),
    PARTNER_INFO_INVALID(400, "회원 정보를 확인해주세요.")
    ;

    private final int code;
    private final String description;
}
