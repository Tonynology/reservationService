package reservationService.reservation.domain.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 점주와 고객의 회원가입을 위한 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    @Email(message = "잘못된 이메일 형식입니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])" +
            "(?=\\S+$).{6,20}$",
            message = "비밀번호는 6~20자, 영문 대소문자, 숫자, " +
                    "특수문자(!,@,#,$,%,^,&,+,=)가 최소 1개씩은 포함되어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "전화번호는 010-0000-0000 형식이어야 합니다.")
    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String phoneNumber;
}
