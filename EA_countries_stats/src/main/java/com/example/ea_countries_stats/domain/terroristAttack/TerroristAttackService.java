package com.example.ea_countries_stats.domain.terroristAttack;


import com.example.ea_countries_stats.domain.country.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TerroristAttackService {
    private TerroristAttackRepository attackRepository;


    @Autowired
    public TerroristAttackService(TerroristAttackRepository attackRepository){
        this.attackRepository = attackRepository;
    }
    public void deleteTerroristAttack(long id) {
        attackRepository.deleteById(id);
    }

    public List<TerroristAttack> getAllTeroristAttacks() {
        List<TerroristAttack> terroristAttacks = new ArrayList<>();
        attackRepository.findAll().forEach(terroristAttacks::add);
        return terroristAttacks;
    }
    public TerroristAttack createTerroristAttack(TerroristAttack terroristAttack){
        return attackRepository.save(terroristAttack);
    }
    public Optional<TerroristAttack> getTerroristAttack(Long id) {
        return attackRepository.findById(id);
    }
    public TerroristAttack updateTerroristAttack(Long id, TerroristAttack terroristAttack) {
        terroristAttack.setAttackId(id);
        return attackRepository.save(terroristAttack);
    }
}
