package reservationService.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reservationService.reservation.domain.dto.AddStoreDto;
import reservationService.reservation.domain.dto.ReservationListDto;
import reservationService.reservation.service.PartnerService;
import reservationService.reservation.util.ValidUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {
    private final PartnerService partnerService;

    /**
     * 점포 등록
     */
    @PostMapping("/add/store")
    public ResponseEntity<?> registerStore(
            @RequestBody @Valid AddStoreDto addStoreDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.responseErrorMessage(bindingResult);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(partnerService.registerStore(addStoreDto))
                .append(" 님의 매장이 등록되었습니다.");
        return ResponseEntity.ok(sb.toString());
    }

    /**
     * 예약 목록 확인
     */
    @GetMapping("/reservation/list")
    public ResponseEntity<List<ReservationListDto>> responseReservation(String email) {
        return ResponseEntity.ok(partnerService.getReservationList(email));
    }

    @PutMapping("/reservation/refuse")
    public ResponseEntity<String> partnerRefuseReservation(Long reservationId) {
        return ResponseEntity.ok(partnerService.refuseReservation(reservationId));
    }
}
