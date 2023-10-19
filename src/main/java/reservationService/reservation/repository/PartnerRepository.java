package reservationService.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservationService.reservation.domain.model.Partner;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    boolean existsByEmail(String email);
    Optional<Partner> findByEmail(String email);
}
