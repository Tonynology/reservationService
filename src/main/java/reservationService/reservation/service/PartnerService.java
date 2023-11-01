package reservationService.reservation.service;

import reservationService.reservation.domain.dto.AddStoreDto;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.SignUpDto;

public interface PartnerService {

    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String registerStore(AddStoreDto addStoreDto);
}
