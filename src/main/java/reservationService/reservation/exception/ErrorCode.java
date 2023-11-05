package reservationService.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PARTNER_ALREADY_EXIST(400, "이미 존재하는 점주입니다."),
    PARTNER_INFO_INVALID(400, "점주 회원 정보를 확인해주세요."),
    CUSTOMER_ALREADY_EXIST(400, "이미 존재하는 고객입니다."),
    CUSTOMER_INFO_INVALID(400, "고객 회원 정보를 확인해주세요."),
    STORE_NAME_NOT_EXIST(400, "존재하지 않는 점포입니다."),
    CUSTOMER_PHONE_NUMBER_NOT_EXIST(400, "존재하지 않는 고객 번호입니다."),
    RESERVATION_IS_REFUSED(400, "거절된 예약입니다."),
    RESERVATION_NOT_FOUND(400, "예약을 찾을 수 없습니다."),
    ARRIVAL_TOO_LATE(400, "너무 늦게 도착하였습니다"),
    ;

    private final int code;
    private final String description;
}
