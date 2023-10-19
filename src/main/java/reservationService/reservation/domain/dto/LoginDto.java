package reservationService.reservation.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 로그인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
