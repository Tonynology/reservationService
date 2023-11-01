package reservationService.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.service.CustomerService;
import reservationService.reservation.service.PartnerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LogInControllerTest {

    @MockBean
    private PartnerService partnerService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void successLogInPartner() throws Exception {
        //given
        given(partnerService.login(any(LoginDto.class)))
                .willReturn("John Doe");

        LoginDto loginDto = LoginDto.builder()
                .email("John@example.com")
                .password("Password1!")
                .build();

        //when
        //then
        mockMvc.perform(post("/login/partner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                loginDto
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("John Doe 점주 로그인 성공"));

        verify(partnerService).login(any(LoginDto.class));
    }

    @Test
    void successLogInCustomer() throws Exception {
        //given
        given(customerService.login(any(LoginDto.class)))
                .willReturn("David Lee");

        LoginDto loginDto = LoginDto.builder()
                .email("David@example.com")
                .password("DavidLee1!")
                .build();

        //when
        //then
        mockMvc.perform(post("/login/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                loginDto
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("David Lee 고객 로그인 성공"));

        verify(customerService).login(any(LoginDto.class));
    }
}
