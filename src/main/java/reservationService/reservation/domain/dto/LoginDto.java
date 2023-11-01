package reservationService.reservation.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 로그인 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @Size(min=11, message = "이메일은 필수 항목입니다.")
    private String email;

    @Size(min=6, message = "비밀번호는 필수 항목입니다.")
    private String password;
}
