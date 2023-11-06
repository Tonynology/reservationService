package reservationService.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservationService.reservation.domain.dto.AddStoreDto;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.ReservationListDto;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.domain.model.Partner;
import reservationService.reservation.domain.model.Reservation;
import reservationService.reservation.domain.model.Store;
import reservationService.reservation.exception.ErrorCode;
import reservationService.reservation.exception.ServiceException;
import reservationService.reservation.repository.PartnerRepository;
import reservationService.reservation.repository.ReservationRepository;
import reservationService.reservation.service.PartnerService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerServiceImpl implements PartnerService{
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationRepository reservationRepository;

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

        // 패스워드 인코딩
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

    /**
     * 점포 등록
     */
    @Override
    @Transactional
    public String registerStore(AddStoreDto addStoreDto) {
        Optional<Partner> optionalPartner = partnerRepository.findByEmail(addStoreDto.getEmail());
        if (optionalPartner.isEmpty()) {
            throw new ServiceException(ErrorCode.PARTNER_INFO_INVALID);
        }

        Partner partner = optionalPartner.get();
        Store store = Store.from(addStoreDto);
        store.setPartner(partner);
        partner.getStoreList().add(store);
        partnerRepository.save(partner);
        return partner.getName();
    }

    /**
     * 예약 목록 확인
     */
    public List<ReservationListDto> getReservationList(String email) {
        Partner partner = partnerRepository.findByEmail(email).get();

        List<Store> stores = partner.getStoreList();
        if (stores.size() == 0) {
            throw new ServiceException(ErrorCode.STORE_NOT_FOUND);
        }
        List<ReservationListDto> reservationListDtos = getReservationByStores(stores);
        reservationListDtos.sort(Comparator.comparing(ReservationListDto::getTime));

        return reservationListDtos;
    }

    /**
     * 매장 예약 정보 조회
     */
    private List<ReservationListDto> getReservationByStores(List<Store> stores) {
        List<ReservationListDto> result = new ArrayList<>();

        for (Store store : stores) {
            List<Reservation> reservationList = store.getReservationList();
            for (Reservation reservation : reservationList) {
                ReservationListDto reservationListDto = new ReservationListDto(
                        reservation.getId(),
                        store.getName(),
                        reservation.getPhoneNumber(),
                        reservation.isValid(),
                        reservation.getTime()
                );
                result.add(reservationListDto);
            }
        }
        return result;
    }

    public String refuseReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        reservation.setRefused(true);
        reservationRepository.save(reservation);
        return "예약이 거절되었습니다.";
    }
}
