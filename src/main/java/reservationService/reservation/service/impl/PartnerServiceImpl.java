package reservationService.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.domain.model.Partner;
import reservationService.reservation.exception.ServiceException;
import reservationService.reservation.repository.PartnerRepository;
import reservationService.reservation.exception.ErrorCode;
import reservationService.reservation.service.PartnerService;

import javax.servlet.http.Part;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService{
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 점주 회원 가입
     */
    @Override
    @Transactional
    public String signUp(SignUpDto signUpDto) {
        boolean exist = this.partnerRepository.existsByEmail(signUpDto.getEmail());
        if (exist) {
            throw new ServiceException(ErrorCode.PARTNER_ALREADY_EXIST);
        }
        Partner partner = Partner.from(signUpDto);

        //패스워드 인코딩 작업 필요!!
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        partner.setPassword(encoder.encode(partner.getPassword()));

        partnerRepository.save(partner);
        return partner.getName();
    }

    /**
     * 점주 로그인
     */
    @Override
    @Transactional
    public String login(LoginDto loginDto) {
        Optional<Partner> optionalPartner = partnerRepository.findByEmail(loginDto.getEmail());
        if (optionalPartner.isEmpty()) {
            throw new ServiceException(ErrorCode.PARTNER_INFO_INVALID);
        }

        Partner partner = optionalPartner.get();
        if (!passwordEncoder.matches(loginDto.getPassword(), partner.getPassword())) {
            throw new ServiceException(ErrorCode.PARTNER_INFO_INVALID);
        }
        return partner.getName();
    }
}
