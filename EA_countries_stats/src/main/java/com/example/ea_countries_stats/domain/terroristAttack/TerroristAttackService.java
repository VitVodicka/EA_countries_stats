package com.example.ea_countries_stats.domain.terroristAttack;


import com.example.ea_countries_stats.domain.country.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TerroristAttackService {
    private List<TerroristAttack> terroristAttackList = new ArrayList<>();

    public void deleteTerroristAttack(long id) {
        terroristAttackList.remove(id);
    }

    public List<TerroristAttack> getAllTeroristAttacks() {
        return terroristAttackList;
    }
}
