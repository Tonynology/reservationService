package reservationService.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservationService.reservation.Type.StoreListType;
import reservationService.reservation.domain.dto.LoginDto;
import reservationService.reservation.domain.dto.MakeReservationDto;
import reservationService.reservation.domain.dto.SignUpDto;
import reservationService.reservation.domain.dto.StoreListResponseDto;
import reservationService.reservation.domain.model.Customer;
import reservationService.reservation.domain.model.Reservation;
import reservationService.reservation.domain.model.Review;
import reservationService.reservation.domain.model.Store;
import reservationService.reservation.exception.ErrorCode;
import reservationService.reservation.exception.ServiceException;
import reservationService.reservation.repository.CustomerRepository;
import reservationService.reservation.repository.StoreRepository;
import reservationService.reservation.service.CustomerService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;

    /**
     * 고객 가입
     */
    @Override
    @Transactional
    public String signUp(SignUpDto signUpDto) {
        boolean exist = customerRepository.existsByEmail(signUpDto.getEmail());
        if (exist) {
            throw new ServiceException(ErrorCode.CUSTOMER_ALREADY_EXIST);
        }
        Customer customer = Customer.from(signUpDto);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        customer.setPassword(encoder.encode(customer.getPassword()));

        customerRepository.save(customer);
        return customer.getName();
    }

    /**
     * 고객 로그인
     */
    @Override
    @Transactional
    public String login(LoginDto loginDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(loginDto.getEmail());
        if (optionalCustomer.isEmpty()) {
            throw new ServiceException(ErrorCode.CUSTOMER_INFO_INVALID);
        }
        Customer customer = optionalCustomer.get();
        if (!passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
            throw new ServiceException(ErrorCode.CUSTOMER_INFO_INVALID);
        }
        return customer.getName();
    }

    /**
     * 매장 예약 요청
     */
    @Override
    @Transactional
    public String reservationStore(MakeReservationDto makeReservationDto) {
        Optional<Store> optionalStore = storeRepository.findByName(makeReservationDto.getStoreName());
        if (optionalStore.isEmpty()) {
            throw new ServiceException(ErrorCode.STORE_NAME_NOT_EXIST);
        }
        Store store = optionalStore.get();

        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(makeReservationDto.getPhoneNumber());
        if (optionalStore.isEmpty()) {
            throw new ServiceException(ErrorCode.CUSTOMER_PHONE_NUMBER_NOT_EXIST);
        }
        Customer customer = optionalCustomer.get();

        createReservation(makeReservationDto, store, customer);

        return store.getName();
    }

    /**
     * 예약 완료
     */
    private void createReservation(MakeReservationDto makeReservationDto, Store store, Customer customer) {
        Reservation reservation = Reservation.builder()
                .customer(customer)
                .store(store)
                .time(makeReservationDto.getReservationTime())
                .phoneNumber(makeReservationDto.getPhoneNumber())
                .isValid(true)
                .isRefused(false)
                .build();

        customer.getReservationList().add(reservation);
        customerRepository.save(customer);
    }

    /**
     * 매장 가나다순, 거리순, 별점순 점포 조회 리스트
     */
    @Override
    @Transactional
    public List<StoreListResponseDto> getStoreList(String listType) {
        List<StoreListResponseDto> storeListResponseDtoList;

        if (listType.equals(StoreListType.NAME.getStoreType())) {
            storeListResponseDtoList = findStoresByName();
        } else if (listType.equals(StoreListType.DISTANCE.getStoreType())) {
            storeListResponseDtoList = findStoresByDistance();
        } else {
            storeListResponseDtoList = findAllStores();
        }

        storeListResponseDtoList.forEach(dto -> dto.setRatingAverage(calculateAverageRating(dto.getName())));

        if (listType.equals(StoreListType.RATING.getStoreType())) {
            Collections.sort(storeListResponseDtoList, Comparator.comparing(StoreListResponseDto::getRatingAverage).reversed());
        }
        return storeListResponseDtoList;

    }
    private double calculateAverageRating(String storeName) {
        Store store = storeRepository.findByName(storeName).get();

        return store.getReviewList().stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

    private List<StoreListResponseDto> findStoresByName() {
        return storeToResponseDto(storeRepository.findAllByOrderByNameAsc());
    }
    private List<StoreListResponseDto> findStoresByDistance() {
        return storeToResponseDto(storeRepository.findAllByOrderByDistanceAsc());
    }
    private List<StoreListResponseDto> findAllStores() {
        return storeToResponseDto(storeRepository.findAll());
    }
    private List<StoreListResponseDto> storeToResponseDto(List<Store> storeList) {
        return storeList.stream().map(x -> StoreListResponseDto.from(x)).collect(Collectors.toList());
    }
}
