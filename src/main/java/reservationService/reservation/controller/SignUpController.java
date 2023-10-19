package reservationService.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.service.PartnerService;
import reservationService.reservation.util.ValidUtil;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {

    private final PartnerService partnerService;

    /**
     * 점주 회원 가입
     */
    @PostMapping("/partner")
    public ResponseEntity<?> signUpPartner(
            @Valid SignUpDto signUpDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ValidUtil.responseErrorMessage(bindingResult);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(partnerService.signUp(signUpDto))
                .append("점주 회원 가입 성공");

        return ResponseEntity.ok(sb.toString());
    } // 테스트 케이스 필요.
}
