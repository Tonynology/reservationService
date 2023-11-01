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
import reservationService.reservation.repository.CustomerRepository;
import reservationService.reservation.repository.PartnerRepository;
import reservationService.reservation.service.CustomerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    @DisplayName("고객 회원 가입 성공")
    void customerSignUpSuccess() {
        //given
        given(customerRepository.existsByEmail(anyString()))
                .willReturn(false);

        SignUpDto signUpDto = SignUpDto.builder()
                .name("David Lee")
                .email("David@gmail.com")
                .password("DavidLee1!")
                .phoneNumber("010-1234-5678")
                .build();

        //when
        String result = customerService.signUp(signUpDto);

        //then
        assertEquals("David Lee", result);

        verify(customerRepository).save(any());
    }

    @Test
    @DisplayName("고객 회원 가입 실패 - 이미 가입한 고객")
    void customerSignUpFailure() {
        //given
        given(customerRepository.existsByEmail(anyString()))
                .willReturn(true);

        SignUpDto signUpDto = SignUpDto.builder()
                .name("David Lee")
                .email("David@gmail.com")
                .password("DavidLee1!")
                .phoneNumber("010-1234-5678")
                .build();

        //when
        Exception exception = assertThrows(ServiceException.class, () -> {
            customerService.signUp(signUpDto);
        });

        //then
        assertEquals(ErrorCode.CUSTOMER_ALREADY_EXIST,
                ((ServiceException) exception).getErrorCode());
    }
}