package com.example.ea_countries_stats.domain.terroristAttack;

import com.example.ea_countries_stats.domain.country.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class TerroristAttack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attackId;

    @NotNull
    private Date date;

    @NotNull
    private String location;

    @NotNull
    private String target;

    @NotNull
    private Integer casualties;

    @NotNull
    private Long countryId;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}
