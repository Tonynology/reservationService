package reservationService.reservation.controller;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reservationService.reservation.domain.dto.MakeReservationDto;
import reservationService.reservation.domain.dto.ReviewDto;
import reservationService.reservation.domain.dto.StoreListResponseDto;
import reservationService.reservation.domain.dto.VisitedStoreDto;
import reservationService.reservation.service.CustomerService;
import reservationService.reservation.util.ValidUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 매장 예약
     */
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

    /**
     * 매장 리스트 가나다순, 거리순, 별점순 조회
     */
    @GetMapping("list/store/{listType}")
    public ResponseEntity<List<StoreListResponseDto>> getStoreList(
            @ApiParam(value = "Category: '이름' : Name, '거리' : distance, '별점' : rating", required = true)
            @PathVariable String listType
    ) {
        return ResponseEntity.ok(customerService.getStoreList(listType));
    }

    /**
     * 키오스트에 도착 정보 전달
     */
    @PutMapping("/reservation/list")
    public ResponseEntity<?> customerVisit(
            @Valid VisitedStoreDto visitedStoreDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.responseErrorMessage(bindingResult);
        }
        return ResponseEntity.ok(customerService.visitComplete(visitedStoreDto));
    }

    @PostMapping("/reservation/{reservationId}/review")
    public ResponseEntity<?> giveReview(
            @PathVariable Long reservationId, ReviewDto reviewDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.responseErrorMessage(bindingResult);
        }
        return ResponseEntity.ok(customerService.registerReview(reservationId, reviewDto));
    }
}
