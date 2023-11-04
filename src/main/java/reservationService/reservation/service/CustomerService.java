package reservationService.reservation.service;

import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.MakeReservationDto;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.domain.dto.StoreListResponseDto;

import java.util.List;

public interface CustomerService {

    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String reservationStore(MakeReservationDto makeReservationDto);
    List<StoreListResponseDto> getStoreList(String listType);

}
