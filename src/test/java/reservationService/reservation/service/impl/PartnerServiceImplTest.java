package reservationService.reservation.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.exception.ErrorCode;
import reservationService.reservation.exception.ServiceException;
import reservationService.reservation.repository.PartnerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Test
    @DisplayName("점주 회원 가입 성공")
    void partnerSignUpSuccess() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("Jason")
                .email("jason@gmail.com")
                .password("123456")
                .phoneNumber("010-1234-5678")
                .build();
        given(partnerRepository.existsByEmail(anyString()))
                .willReturn(false);
        //when
        String result = partnerService.signUp(signUpDto);
        // chat gpt 물어보기.

        //then
        assertEquals("Jason", result);
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 점주 회원 가입 실패")
    void partnerSignUpFailure() {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
                .name("Jason")
                .email("jason@gmail.com")
                .password("123456")
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
}