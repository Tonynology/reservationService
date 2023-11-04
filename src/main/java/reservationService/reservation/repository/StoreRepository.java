package reservationService.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reservationService.reservation.domain.model.Store;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByName(String name);
    List<Store> findAllByOrderByNameAsc();
    List<Store> findAllByOrderByDistanceAsc();
    List<Store> findAll();
}
