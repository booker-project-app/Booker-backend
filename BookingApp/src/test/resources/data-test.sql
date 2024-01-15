INSERT INTO users (name, surname, email, password, address, phone, role, reported, blocked, deleted, favourite_accommodations, activation_link, activated, activation_expired, activation_timestamp)
VALUES ('Prvi', 'Gost', 'gost1@gmail.com', '$2a$12$Mn1xmYaoG3YjZ1NsPRwV0eDw5wwGbmkhBSooynq8gldJluzqybXP.', 'adresa1', '060000000', 'GUEST', false, false, false, ARRAY[1, 12, 8], 'activation_link1', true, false, null);

INSERT INTO users (name, surname, email, password, address, phone, role, reported, blocked, deleted, favourite_accommodations, activation_link, activated, activation_expired, activation_timestamp)
VALUES ('Prvi', 'Vlasnik', 'vlasnik2@gmail.com', '$2a$12$Mn1xmYaoG3YjZ1NsPRwV0eDw5wwGbmkhBSooynq8gldJluzqybXP.', 'adresa2', '060000001', 'OWNER', false, false, false, null, 'activation_link2', true, false, null);

INSERT INTO users (name, surname, email, password, address, phone, role, reported, blocked, deleted, favourite_accommodations, activation_link, activated, activation_expired, activation_timestamp)
VALUES ('Drugi', 'Gost', 'gost3@gmail.com', '$2a$12$Mn1xmYaoG3YjZ1NsPRwV0eDw5wwGbmkhBSooynq8gldJluzqybXP.', 'adresa3', '060000002', 'GUEST', false, false, false, ARRAY[5, 6, 7, 8, 9], 'activation_link3', true, false, null);

INSERT INTO users (name, surname, email, password, address, phone, role, reported, blocked, deleted, favourite_accommodations, activation_link, activated, activation_expired, activation_timestamp)
VALUES ('Treci', 'Gost', 'gost123@gmail.com', '$2a$12$Mn1xmYaoG3YjZ1NsPRwV0eDw5wwGbmkhBSooynq8gldJluzqybXP.', 'Adresa 123', '3210087', 'GUEST', false, false, false, ARRAY[1, 2, 9, 3], 'activation_link4', true, false, null);

INSERT INTO users (name, surname, email, password, address, phone, role, reported, blocked, deleted, favourite_accommodations, activation_link, activated, activation_expired, activation_timestamp)
VALUES ('Prvi', 'Admin', 'admin123@gmail.com', '$2a$12$Mn1xmYaoG3YjZ1NsPRwV0eDw5wwGbmkhBSooynq8gldJluzqybXP.', 'Adresa admin', '3210088', 'ADMIN', null, null, null, null, 'activation_link5', true, false, null);

INSERT INTO users (name, surname, email, password, address, phone, role, reported, blocked, deleted, favourite_accommodations, activation_link, activated, activation_expired, activation_timestamp)
VALUES ('Treci', 'Vlasnik', 'mika123@gmail.com', '$2a$12$Mn1xmYaoG3YjZ1NsPRwV0eDw5wwGbmkhBSooynq8gldJluzqybXP.', 'Mikina adresa', '3210089', 'OWNER', false, false, false, null, 'activation_link6', true, false, null);

INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Cozy Cabin', 'A charming cabin in the woods.', 'Rustic cabin retreat', 15, 1, 4, 2, 0, true, true);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES ('Luxury Villa', 'A luxurious villa with stunning views.', 'Luxury villa with pool', 30, 1, 3, 6, 2, true, false);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('City Apartment', 'Modern apartment in the heart of the city.', 'Downtown apartment', 20, 1, 4, 6, 1, true, true);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES ('Example apartment 1', 'A luxurious villa with stunning views.', 'Luxury villa with pool', 3, 1, 5, 2, 2, false, true);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Example apartment 2', 'A charming cabin in the woods.', 'Rustic cabin retreat', 5, 1, 7, 2, 3, true, false);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Example apartment 3', 'Modern apartment in the heart of the city.', 'Downtown apartment', 2, 1, 5, 6, 3, false, false);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES ('Hotel enjoy', 'A luxurious villa with stunning views.', 'Luxury villa with pool', 1, 1, 2, 6, 1, true, false);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Villa Relaxation', 'A charming cabin in the woods.', 'Rustic cabin retreat', 10, 2, 15, 2, 0, true, true);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Flower Apartment', 'Modern apartment in the heart of the city.', 'Downtown apartment', 7, 1, 2, 2, 1, true, true);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES ('The prettiest house', 'A luxurious villa with stunning views.', 'Luxury villa with pool', 14, 1, 3, 2, 2, false, false);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Charming Room', 'A charming cabin in the woods.', 'Rustic cabin retreat', 3, 1, 2, 6, 0, true, true);
INSERT INTO Accommodation (title, description, short_description, deadline, min_capacity, max_capacity, owner_id, type, accepted, manual_accepting) VALUES  ('Lovely villa', 'Modern apartment in the heart of the city.', 'Downtown apartment', 10, 2, 6, 2, 2, false, false);
-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 1
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('wifi', 1, '../../../assets/images/icons8-wifi-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 1, '../../../assets/images/icons8-location-32.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 1,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (1, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (1, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (1, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (1, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES ('2024-10-03', '2024-01-10', 1);
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES ('2024-02-15', '2024-02-28', 1);
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES ('2024-03-20', '2024-04-05', 1);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (1, 100.0, '2024-10-03', '2024-01-10', 0);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (1, 150.0, '2024-01-01', '2024-02-01', 0);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (1, 80.0, '2023-01-01', '2023-02-01', 0);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (1, 100.0, '2024-02-10', '2024-02-14', 0);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (1, 120.0, '2024-02-15', '2024-02-28', 0);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (1, 90.0, '2024-03-20', '2024-04-05', 0);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('London', '456 Forest Avenue', 22.15, 32.15, 1);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 2
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('free cancellation', 2, '../../../assets/images/icons8-calendar-32.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('wifi', 2, '../../../assets/images/icons8-wifi-30.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (2, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (2, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (2, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-01-01', '2024-01-10', 2), ('2024-02-15', '2024-02-28', 2),('2024-03-20', '2024-04-05', 2);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (2, 125.0, '2024-01-01', '2024-01-10', 1);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (2, 120.0, '2024-02-15', '2024-02-28', 1);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES (2, 125.0, '2024-03-20', '2024-04-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('New York', '123 Main Street', 17.36, 71.18, 2);


-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 3
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 3,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 3, '../../../assets/images/icons8-location-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (3, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (3, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (3, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (3, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-05-01', '2024-05-10', 3),('2024-06-15', '2024-06-28', 3),('2024-07-20', '2024-08-05', 3);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (3, 50.0, '2024-05-01', '2024-05-10', 1), (3, 45.0, '2024-06-15', '2024-06-28', 1),(3, 55.0, '2024-07-20', '2024-08-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('Belgrade', '123 Main Street', 52.21, 21.38, 3);


-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 4
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 4, '../../../assets/images/icons8-location-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (4, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (4, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (4, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (4, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-09-01', '2024-09-10', 4),('2024-10-15', '2024-10-28', 4),('2024-11-20', '2024-12-05', 4);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (4, 60.0, '2024-09-01', '2024-09-10', 0),
                                                                         (4, 65.0, '2024-10-15', '2024-10-28', 0),
                                                                         (4, 55.0, '2024-11-20', '2024-12-05', 0);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('Belgrade', '123 Sziostok Street', 17.36, 71.18, 4);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 5
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 5,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (5, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (5, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (5, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (5, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-01-01', '2024-01-10', 5),('2024-02-15', '2024-02-28', 5),('2024-03-20', '2024-04-05', 5);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (5, 100.0, '2024-01-01', '2024-01-10', 0),
                                                                         (5, 90.0, '2024-02-15', '2024-02-28', 0),
                                                                         (5, 95.0, '2024-03-20', '2024-04-05', 0);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('London', '123 Example Address', 36.36, 52.18, 5);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 6
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 6,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('wifi', 6, '../../../assets/images/icons8-wifi-30.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (6, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (6, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (6, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (6, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-05-01', '2024-05-10', 6),('2024-06-15', '2024-06-28', 6),('2024-07-20', '2024-08-05', 6);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (6, 15.0, '2024-05-01', '2024-05-10', 1),
                                                                         (6, 10.0, '2024-06-15', '2024-06-28', 1),
                                                                         (6, 25.0, '2024-07-20', '2024-08-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('New York', '123 My Address', 55.18, 42.00, 6);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 7
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('wifi', 7, '../../../assets/images/icons8-wifi-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 7, '../../../assets/images/icons8-location-32.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 7,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('free cancellation', 7, '../../../assets/images/icons8-calendar-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (7, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (7, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (7, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (7, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-09-01', '2024-09-10', 7),('2024-10-15', '2024-10-28', 7),('2024-11-20', '2024-12-05', 7);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (7, 82.0, '2024-09-01', '2024-09-10', 0),
                                                                         (7, 80.0, '2024-10-15', '2024-10-28', 0),
                                                                         (7, 82.0, '2024-11-20', '2024-12-05', 0);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('Belgrade', '123 Lovely Street', 5.17, 2.18, 7);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 8
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 8, '../../../assets/images/icons8-location-32.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 8,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('free cancellation', 8, '../../../assets/images/icons8-calendar-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (8, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (8, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (8, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (8, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-01-01', '2024-01-10', 8),('2024-02-15', '2024-02-28', 8),('2024-03-20', '2024-04-05', 8);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (8, 250.0, '2024-01-01', '2024-01-10', 1),
                                                                         (8, 130.0, '2024-02-15', '2024-02-28', 1),
                                                                         (8, 200.0, '2024-03-20', '2024-04-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('London', '123 Main Adress', 37.36, 10.18, 8);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 9
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 9, '../../../assets/images/icons8-location-32.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('free cancellation', 9, '../../../assets/images/icons8-calendar-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (9, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (9, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-05-01', '2024-05-10', 9),('2024-06-15', '2024-06-28', 9),('2024-07-20', '2024-08-05', 9);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (9, 50.0, '2024-05-01', '2024-05-10', 1),
                                                                         (9, 55.0, '2024-06-15', '2024-06-28', 1),
                                                                         (9, 57.0, '2024-07-20', '2024-08-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('London', '123 China street', 10.36, 7.77, 9);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 10
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('AC', 10,'../../../assets/images/icons8-ac-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('free cancellation', 10, '../../../assets/images/icons8-calendar-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (10, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (10, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (10, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (10, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-09-01', '2024-09-10', 10),('2024-10-15', '2024-10-28', 10),('2024-11-20', '2024-12-05', 10);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (10, 67.0, '2024-09-01', '2024-09-10', 1),
                                                                         (10, 66.0, '2024-10-15', '2024-10-28', 1),
                                                                         (10, 67.0, '2024-11-20', '2024-12-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('New York', '123 Amazing Avenue', 57.36, 51.18, 10);


-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 11
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('wifi', 11, '../../../assets/images/icons8-wifi-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('good location', 11, '../../../assets/images/icons8-location-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (11, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (11, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (11, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (11, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-01-01', '2024-01-10', 11),('2024-02-15', '2024-02-28', 11),('2024-03-20', '2024-04-05', 11);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (11, 112.0, '2024-01-01', '2024-01-10', 0),
                                                                         (11, 113.0, '2024-02-15', '2024-02-28', 0),
                                                                         (11, 120.0, '2024-03-20', '2024-04-05', 0);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('Belgrade', '123 Fantastic Park', 17.36, 1.18, 11);

-- Insert data into Amenity, Image, Availability, Price and Address tables for Accommodation 12
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('wifi', 12, '../../../assets/images/icons8-wifi-30.png');
INSERT INTO Amenity (name, accommodation_id, image_path)
VALUES ('free cancellation', 12, '../../../assets/images/icons8-calendar-32.png');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (12, '../../assets/images/living-room.jpg', '../../../../../res/drawable/living-room.jpg');
INSERT INTO Image(accommodation_id, path_front, path_mobile) VALUES (12, '../../assets/images/kitchen-2165756_640.jpg', '../../../../../res/drawable/kitchen-2165756_640.jpg');
INSERT INTO Availability (start_date, end_date, accommodation_id) VALUES
                                                                      ('2024-05-01', '2024-05-10', 12),('2024-06-15', '2024-06-28', 12), ('2024-07-20', '2024-08-05', 12);
INSERT INTO Price (accommodation_id, cost, from_date, to_date, type) VALUES
                                                                         (12, 80.0, '2024-05-01', '2024-05-10', 1),
                                                                         (12, 82.0, '2024-06-15', '2024-06-28', 1),
                                                                         (12, 75.0, '2024-07-20', '2024-08-05', 1);
INSERT INTO Address (city, street, latitude, longitude, accommodation_id) VALUES ('London', '456 Festival Boulevard', 23.15, 6.18, 12);

-- Inserting data into AccommodationComment table
INSERT INTO Accommodation_Comment (accommodation_id, guest_id, content, rating, date, reported, deleted, approved)
VALUES (1, 2, 'Great!', 3.8, '2024-10-01', false, false, true);

-- Inserting additional data into AccommodationComment table
INSERT INTO Accommodation_Comment (accommodation_id, guest_id, content, rating, date, reported, deleted, approved)
VALUES
    (1, 3, 'Fantastic experience!', 5.0, '2024-10-03', false, false, true),
    (2, 1, 'Lovely cabin, enjoyed every moment.', 5.0, '2024-09-25', false, false, true),
    (3, 3, 'The city apartment was perfect for our stay.', 4.0, '2024-10-05', false, false, true),
    (1, 1, 'Not as expected, needs improvement.', 3.5, '2024-10-02', true, true, true),
    (2, 2, 'Cozy and charming, highly recommend!', 5.0, '2024-09-28', false, false, true);


INSERT INTO Accommodation_Rating (accommodation_id, guest_id, rate, date, reported) VALUES (1, 2, 4.0, '2024-10-01', false);

-- Inserting additional data into AccommodationRating table
INSERT INTO Accommodation_Rating (accommodation_id, guest_id, rate, date, reported)
VALUES
    (1, 3, 5.0, '2024-10-03', false),
    (2, 1, 4.5, '2024-09-25', false),
    (3, 2, 3.8, '2024-10-05', true);

INSERT INTO Owner_Rating (owner_id, guest_id, rate, date, reported, deleted)
VALUES (2, 1, 4.5, '2024-01-01', false, false);
INSERT INTO Owner_Rating (owner_id, guest_id, rate, date, reported, deleted)
VALUES (2, 3, 3.8, '2024-02-15', false, false);
-- INSERT INTO Owner_Rating (owner_id, guest_id, rate, date, reported, deleted)
-- VALUES (2, 4, 4.9, '2024-03-10', true, false);
INSERT INTO Owner_Rating (owner_id, guest_id, rate, date, reported, deleted)
VALUES (6, 3, 2.5, '2024-04-20', false, false);
INSERT INTO Owner_Rating (owner_id, guest_id, rate, date, reported, deleted)
VALUES (6, 1, 4.0, '2024-05-05', true, true);

INSERT INTO Profile_Picture (path_front, path_mobile,  user_id)
VALUES
    ('../../assets/images/profile_pic.jpg', '../../../../../res/drawable/profile_pic.jpg', 1),
    ('../../assets/images/profile_pic.jpg', '../../../../../res/drawable/profile_pic.jpg', 2),
    ('../../assets/images/profile_pic.jpg', '../../../../../res/drawable/profile_pic.jpg', 3),
    ('../../assets/images/profile_pic.jpg', '../../../../../res/drawable/profile_pic.jpg', 4),
    ('../../assets/images/profile_pic.jpg', '../../../../../res/drawable/profile_pic.jpg', 5),
    ('../../assets/images/profile_pic.jpg', '../../../../../res/drawable/profile_pic.jpg', 6);

-- INSERT INTO Reservation (guest_id, accommodation_id, from_date, to_date, number_of_guests, request_status, reservation_status, deleted, price)
-- VALUES
--     (1, 1, '2023-01-01', '2023-01-05', 1, 'ACCEPTED', 'ACCEPTED', false, 150.0),
--     (1, 2, '2023-02-10', '2023-02-15', 1, 'ACCEPTED', 'CANCELED', false, 120.0),
--     (3, 3, '2023-03-20', '2023-03-25', 4, 'DENIED', 'ACCEPTED', false, 200.0),
--     (4, 4, '2023-03-20', '2023-03-25', 4, 'ACCEPTED', 'CANCELED', false, 200.0),
--     (1, 1, '2024-01-01', '2024-01-05', 1, 'ACCEPTED', 'ACCEPTED', false, 150.0),
--     (1, 1, '2024-02-10', '2024-02-15', 1, 'ACCEPTED', 'CANCELED', false, 120.0),
--     (3, 3, '2024-03-20', '2024-03-25', 4, 'DENIED', 'ACCEPTED', false, 200.0),
--     (4, 4, '2024-03-20', '2024-03-25', 4, 'ACCEPTED', 'CANCELED', false, 200.0);

INSERT INTO Reservation (guest_id, accommodation_id, from_date, to_date, number_of_guests, request_status, reservation_status, deleted, price)
VALUES
    (1, 1, '2023-01-01 12:00:00', '2023-01-05 14:30:00', 1, 'ACCEPTED', 'ACCEPTED', false, 150.0),
    (1, 2, '2023-02-10 10:00:00', '2023-02-15 16:45:00', 1, 'ACCEPTED', 'CANCELED', false, 120.0),
    (3, 3, '2023-03-20 08:30:00', '2023-03-25 18:00:00', 4, 'DENIED', 'ACCEPTED', false, 200.0),
    (4, 4, '2023-03-20 09:15:00', '2023-03-25 17:30:00', 4, 'ACCEPTED', 'CANCELED', false, 200.0),
    (1, 1, '2024-01-01 11:00:00', '2024-01-05 13:45:00', 1, 'ACCEPTED', 'ACCEPTED', false, 150.0),
    (1, 1, '2024-02-10 10:30:00', '2024-02-15 15:15:00', 1, 'ACCEPTED', 'CANCELED', false, 120.0),
    (3, 3, '2024-03-20 08:00:00', '2024-03-25 17:45:00', 4, 'DENIED', 'ACCEPTED', false, 200.0),
    (4, 4, '2024-03-20 09:45:00', '2024-03-25 16:30:00', 4, 'ACCEPTED', 'CANCELED', false, 200.0),
    (1, 1, '2024-01-10 22:41:00', '2024-01-16 02:41:00', 3, 'ACCEPTED', 'ACCEPTED', false, 250.0);


INSERT INTO RESERVATION_REQUEST (guest_id, accommodation_id, from_date, to_date, number_of_guests, status, deleted, price)
VALUES
    (1, 1, '2025-01-01', '2025-01-05', 1, 2, false, 150.0),
    (1, 1, '2023-01-01', '2023-01-05', 1, 2, false, 150.0),
    (1, 3, '2024-02-10', '2024-02-15', 1, 0, false, 120.0),
    (1, 5, '2023-03-20', '2023-03-25', 4, 2, true, 200.0),
    (1, 7, '2024-03-20', '2024-03-25', 4, 1, false, 200.0),
    (1, 1, '2024-02-22', '2024-02-24', 1, 2, false, 240.0),
    (1, 1, '2024-02-21', '2024-02-26', 1, 2, false, 600.0);











-- Example 1: Inserting a new OwnerComment with reported set to false
INSERT INTO Owner_Comment (guest_id, owner_id, content, rating, date, reported, deleted, approved)
VALUES (1, 2, 'This is a comment.', 3.8, '2024-01-01', false, false, true);

-- Example 2: Inserting another OwnerComment with reported set to true
INSERT INTO Owner_Comment (guest_id, owner_id, content, rating, date, reported, deleted, approved)
VALUES (3, 2, 'Another comment.', 4.5, '2024-02-15', true, true, true);

-- Insert data into UserReport table
INSERT INTO User_Report (reported_id, reporter_id, reason, date) VALUES
                                                                     (1, 2, 'Inappropriate behavior', '2024-01-11'),
                                                                     (2, 3, 'Spamming', '2024-01-12'),
                                                                     (3, 1, 'Harassment', '2024-01-13');







