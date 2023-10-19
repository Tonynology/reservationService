package reservationService.reservation.domain.model;

import lombok.*;
import reservationService.reservation.domain.dto.SignUpDto;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;


    public static Partner from(SignUpDto signUpDto) {
        return Partner.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhoneNumber())
                .build();
    }
}
