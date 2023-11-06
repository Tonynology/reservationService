package reservationService.reservation.domain.model;

import lombok.*;
import reservationService.reservation.domain.dto.AddStoreDto;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String name;

    private String location;

    private String description;

    private double distance;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    public static Store from(AddStoreDto addStoreDto) {
        return Store.builder()
                .name(addStoreDto.getName())
                .location(addStoreDto.getLocation())
                .description(addStoreDto.getDescription())
                .distance(addStoreDto.getDistance())
                .build();
    }


}
