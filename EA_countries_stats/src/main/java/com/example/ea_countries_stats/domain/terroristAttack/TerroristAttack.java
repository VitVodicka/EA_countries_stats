package com.example.ea_countries_stats.domain.terroristAttack;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
