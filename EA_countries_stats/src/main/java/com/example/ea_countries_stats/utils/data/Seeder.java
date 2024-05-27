package com.example.ea_countries_stats.utils.data;

import com.example.ea_countries_stats.domain.country.CountryService;
import com.example.ea_countries_stats.domain.terroristAttack.TerroristAttackService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Seeder {

    private final CountryService countryService;
    private final TerroristAttackService terroristAttackService;

    public Seeder( CountryService countryService, TerroristAttackService terroristAttackService) {
        this.countryService = countryService;
        this.terroristAttackService = terroristAttackService;
    }

    @PostConstruct
    public void seedDefaultData() {
        /*
        User user1 = new User(1L, "Ivo", "ivo", new ArrayList<>());
        User user2 = new User(2L, "Marie", "mar777", new ArrayList<>());
        userService.createUser(user1);
        userService.createUser(user2);

        Account account1 = new Account(1L, user1, 100.0);
        Account account2 = new Account(2L, user2, 200.0);
        accountService.createAccount(account1);
        accountService.createAccount(account2);

        user1.attachAccount(account2);
        account2.attachUser(user1);*/

        log.info("--- Default data seeded ---");
    }


}
