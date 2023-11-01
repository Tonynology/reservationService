package reservationService.reservation.service;

import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.MakeReservationDto;
import reservationService.reservation.domain.dto.SignUpDto;

public interface CustomerService {

    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);

    String reservationStore(MakeReservationDto makeReservationDto);
}
