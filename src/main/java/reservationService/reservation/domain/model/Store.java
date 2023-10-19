package reservationService.reservation.domain.model;

import lombok.*;
import reservationService.reservation.domain.dto.AddStoreDto;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private String description;

    private double distance;


    public static Store from(AddStoreDto addStoreDto) {
        return Store.builder()
                .name(addStoreDto.getName())
                .location(addStoreDto.getLocation())
                .description(addStoreDto.getDescription())
                .distance(addStoreDto.getDistance())
                .build();
    }


}
