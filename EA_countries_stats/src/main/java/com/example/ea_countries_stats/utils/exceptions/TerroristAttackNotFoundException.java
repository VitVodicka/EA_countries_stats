package com.example.ea_countries_stats.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TerroristAttackNotFoundException extends RuntimeException {

    public TerroristAttackNotFoundException(Long id) {
        super("Terrorist attack with ID " + id + " not found");
    }
}