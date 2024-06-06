INSERT INTO public.country (country_id, country_txt, iso_code) VALUES
                                                                   (1, 'USA', 'US'),
                                                                   (2, 'Czechia', 'CZ');

INSERT INTO public.terrorist_attack (date, location, target, casualties, country_id) VALUES
                                                                                         ('2023-01-15 12:30:00', 'New York', 'Building', 10, 1),
                                                                                         ('2023-02-20 14:45:00', 'Prague', 'Train Station', 5, 2);
