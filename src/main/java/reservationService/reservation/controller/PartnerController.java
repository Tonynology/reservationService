package reservationService.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservationService.reservation.domain.dto.AddStoreDto;
import reservationService.reservation.service.PartnerService;
import reservationService.reservation.util.ValidUtil;

import javax.validation.Valid;

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
}
