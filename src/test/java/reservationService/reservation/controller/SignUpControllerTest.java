package reservationService.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.service.CustomerService;
import reservationService.reservation.service.PartnerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {

    @MockBean
    private PartnerService partnerService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void successSignUpPartner() throws Exception {
        //given
        given(partnerService.signUp(any(SignUpDto.class)))
                .willReturn("John Doe");

        SignUpDto signUpDto = SignUpDto.builder()
                .email("John@example.com")
                .name("John Doe")
                .password("Password1!")
                .phoneNumber("010-1234-5678")
                .build();

        //when
        //then
        mockMvc.perform(post("/signup/partner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                signUpDto
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("John Doe 점주 회원 가입 성공"));

        verify(partnerService).signUp(any(SignUpDto.class));
    }

    @Test
    void successSignUpCustomer() throws Exception {
        //given
        given(customerService.signUp(any(SignUpDto.class)))
                .willReturn("David Lee");

        SignUpDto signUpDto = SignUpDto.builder()
                .email("David@example.com")
                .name("David Lee")
                .password("Davidlee1!")
                .phoneNumber("010-1234-5678")
                .build();

        mockMvc.perform(post("/signup/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        signUpDto
                )))
                .andExpect(status().isOk())
                .andExpect(content().string("David Lee 고객 회원 가입 성공"));

        verify(customerService).signUp(any(SignUpDto.class));
    }
}
