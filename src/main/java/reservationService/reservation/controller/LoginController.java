package reservationService.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.service.PartnerService;
import reservationService.reservation.util.ValidUtil;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final PartnerService partnerService;
    /**
     * 점주 로그인
     */
    @PostMapping("/partner")
    public ResponseEntity<?> LoginPartner(
            @Valid LoginDto loginDto, BindingResult bindingResult
            ) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.responseErrorMessage(bindingResult);
        }

        String result = partnerService.login(loginDto) + "점주 로그인 성공";

        return ResponseEntity.ok(result);
    } // 테스트, 테스트케이스 필요.
}
