insert into users (name, surname, email, password, address, phone, role, blocked, deleted) values ('Marko', 'Marković', 'email1', '123', 'adresa1', '060000000', 0, false, false);
insert into users (name, surname, email, password, address, phone, role, blocked, deleted) values ('Petar', 'Petrović', 'email2', '1234', 'adresa2', '060000001', 1, false, false);
insert into users (name, surname, email, password, address, phone, role, blocked, deleted) values ('Adam', 'Adamović', 'email3', '12345', 'adresa3', '060000002', 0, false, false);

-- Inserting data into Accommodation table
INSERT INTO Accommodation (title, description, short_description, address, deadline) VALUES ('Luxury Villa', 'A luxurious villa with stunning views.', 'Luxury villa with pool', '123 Main Street', 30);
INSERT INTO Accommodation (title, description, short_description, address, deadline) VALUES  ('Cozy Cabin', 'A charming cabin in the woods.', 'Rustic cabin retreat', '456 Forest Avenue', 15);
INSERT INTO Accommodation (title, description, short_description, address, deadline) VALUES  ('City Apartment', 'Modern apartment in the heart of the city.', 'Downtown apartment', '789 Urban Street', 20);

INSERT INTO Accommodation_Comment (accommodation_id, guest_id, content, date, reported) VALUES (1, 2, 'Great!', '2023-10-01', false);
-- Inserting additional data into AccommodationComment table
INSERT INTO Accommodation_Comment (accommodation_id, guest_id, content, date, reported)
VALUES
    (1, 3, 'Fantastic experience!', '2023-10-03', false),
    (2, 1, 'Lovely cabin, enjoyed every moment.', '2023-09-25', false),
    (3, 3, 'The city apartment was perfect for our stay.', '2023-10-05', false),
    (1, 1, 'Not as expected, needs improvement.', '2023-10-02', true),
    (2, 2, 'Cozy and charming, highly recommend!', '2023-09-28', false);

INSERT INTO Accommodation_Rating (accommodation_id, guest_id, rate, date, reported) VALUES (1, 2, 4.0, '2023-10-01', false);

-- Inserting additional data into AccommodationRating table
INSERT INTO Accommodation_Rating (accommodation_id, guest_id, rate, date, reported)
VALUES
    (1, 3, 5.0, '2023-10-03', false),
    (2, 1, 4.5, '2023-09-25', false),
    (3, 2, 3.8, '2023-10-05', true);










