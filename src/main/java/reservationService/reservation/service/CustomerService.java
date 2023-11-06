package reservationService.reservation.service;

import reservationService.reservation.domain.dto.*;

import java.util.List;

public interface CustomerService {

    String signUp(SignUpDto signUpDto);
    String login(LoginDto loginDto);
    String reservationStore(MakeReservationDto makeReservationDto);
    List<StoreListResponseDto> getStoreList(String listType);
    String visitComplete(VisitedStoreDto visitedStoreDto);

    String registerReview(Long reservationId, ReviewDto reviewDto);

}
