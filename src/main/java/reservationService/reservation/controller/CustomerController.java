package reservationService.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservationService.reservation.domain.dto.MakeReservationDto;
import reservationService.reservation.service.CustomerService;
import reservationService.reservation.util.ValidUtil;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/reservation/store")
    public ResponseEntity<?> makeStoreReserve(
            @Valid MakeReservationDto makeReservationDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.responseErrorMessage(bindingResult);
        }

        String result = customerService.reservationStore(makeReservationDto) + " 매장 예약 성공";

        return ResponseEntity.ok(result.toString());
    }

}
