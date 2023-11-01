package reservationService.reservation.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.domain.model.Partner;
import reservationService.reservation.exception.ErrorCode;
import reservationService.reservation.exception.ServiceException;
import reservationService.reservation.repository.CustomerRepository;
import reservationService.reservation.repository.PartnerRepository;
import reservationService.reservation.service.CustomerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Test
    @DisplayName("점주 회원 가입 성공")
    void partnerSignUpSuccess() {
        //given
        given(partnerRepository.existsByEmail(anyString()))
                .willReturn(false);

        SignUpDto signUpDto = SignUpDto.builder()
                .name("Jason")
                .email("jason@gmail.com")
                .password("P@ssword1234")
                .phoneNumber("010-1234-5678")
                .build();
        //when
        String result = partnerService.signUp(signUpDto);

        //then
        assertEquals("Jason", result);

//        verify(partnerRepository).save(any());
    }

    @Test
    @DisplayName("점주 회원 가입 실패 - 이미 가입한 점주")
    void partnerSignUpFailure() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("Jason")
                .email("jason@gmail.com")
                .password("P@ssword1234")
                .phoneNumber("010-1234-5678")
                .build();
        given(partnerRepository.existsByEmail(anyString()))
                .willReturn(true);

        //when
        Exception exception = assertThrows(ServiceException.class, () -> {
            partnerService.signUp(signUpDto);
        });

        //then
        assertEquals(ErrorCode.PARTNER_ALREADY_EXIST,
                ((ServiceException) exception).getErrorCode());
    }

    @Test
    @DisplayName("점주 로그인 성공")
    void partnerLogInSuccess() {
        //given
        given(partnerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(Partner.builder()
                                .email("Jason@gmail.com")
                                .name("Jason")
                                .password("P@ssword1234")
                                .build()));
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(true);

        LoginDto loginDto = LoginDto.builder()
                .email("Jason@gmail.com")
                .password("P@ssword1234")
                .build();
        //when
        String result = partnerService.login(loginDto);

        //then
        assertEquals("Jason", result);

        verify(partnerRepository).findByEmail(any());
    }
}