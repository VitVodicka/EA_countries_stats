package com.example.ea_countries_stats.domain.terroristAttack;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TerroristAttack {
    @NotNull
    private Long id;

    @NotNull
    private Long attackId;

    @NotNull
    private Date date;

    @NotNull
    private String location;

    @NotNull
    private String target;

    @NotNull
    private Integer casualities;

    @NotNull
    private Long countryId;



}
