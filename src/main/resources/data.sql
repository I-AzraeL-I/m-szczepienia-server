--voivodeships
INSERT INTO voivodeship (name) VALUES ('Mazowieckie');
INSERT INTO voivodeship (name) VALUES ('Dolnośląskie');


--cities
INSERT INTO city (name, voivodeship_id) VALUES ('Warszawa', 1);
INSERT INTO city (name, voivodeship_id) VALUES ('Wrocław', 2);


--diseases
INSERT INTO disease (name) VALUES ('COVID-19');


--manufacturers
INSERT INTO manufacturer (name) VALUES ('Pfizer/BioNTech');
INSERT INTO manufacturer (name) VALUES ('Moderna');
INSERT INTO manufacturer (name) VALUES ('AstraZeneka');
INSERT INTO manufacturer (name) VALUES ('Johnson & Johnson');


--vaccines
INSERT INTO vaccine (disease_id, manufacturer_id) VALUES (1, 1);
INSERT INTO vaccine (disease_id, manufacturer_id) VALUES (1, 2);
INSERT INTO vaccine (disease_id, manufacturer_id) VALUES (1, 3);
INSERT INTO vaccine (disease_id, manufacturer_id) VALUES (1, 4);


--places with address, vaccines stock and workdays
INSERT INTO address (city_id, street, number) VALUES (1, 'Wołoska', '137');
INSERT INTO place (address_id, name, moderator_id) VALUES (1, 'Centralny Szpital Kliniczny MSWiA', null);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (1, 1, 100);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (1, 2, 100);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (1, 3, 100);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (1, 4, 0);
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (1, 'MONDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (1, 'TUESDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (1, 'WEDNESDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (1, 'THURSDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (1, 'FRIDAY', '08:00', '16:00');

INSERT INTO address (city_id, street, number) VALUES (1, 'Cegłowska', '80');
INSERT INTO place (address_id, name, moderator_id) VALUES (2, 'Szpital Bielański', null);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (2, 1, 100);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (2, 2, 100);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (2, 3, 100);
INSERT INTO place_vaccine (place_id, vaccine_id, quantity) VALUES (2, 4, 100);
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (2, 'MONDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (2, 'TUESDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (2, 'WEDNESDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (2, 'THURSDAY', '08:00', '16:00');
INSERT INTO work_day (place_id, day_of_week, opening_hour, closing_hour) VALUES (2, 'FRIDAY', '08:00', '16:00');