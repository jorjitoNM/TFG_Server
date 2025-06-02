delete from user_followed;
delete from user_follower;
delete from user_likes_notes;
delete from user_saved_notes;
delete from notes;
delete from users;


INSERT INTO users (user_id, code, email, enabled, password, username)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'CODE111', 'alice@example.com', 1,
        '$2a$10$XptfskLsT1l/bRTLRiiCgegjHmO79V3L/Ie6eXo1XzR1cF5W5JQ1O', 'alice'),
       (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 'CODE222', 'bob@example.com', 1,
        '$2a$10$XptfskLsT1l/bRTLRiiCgegjHmO79V3L/Ie6eXo1XzR1cF5W5JQ1O', 'bob'),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 'CODE333', 'charlie@example.com', 1,
        '$2a$10$XptfskLsT1l/bRTLRiiCgegjHmO79V3L/Ie6eXo1XzR1cF5W5JQ1O', 'charlie'),
       (UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), 'CODE444', 'dave@example.com', 1,
        '$2a$10$XptfskLsT1l/bRTLRiiCgegjHmO79V3L/Ie6eXo1XzR1cF5W5JQ1O', 'dave'),
       (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 'CODE555', 'eve@example.com', 1,
        '$2a$10$XptfskLsT1l/bRTLRiiCgegjHmO79V3L/Ie6eXo1XzR1cF5W5JQ1O', 'eve');

-- Alice follows Bob and Charlie
INSERT INTO user_followed (followed_id, owner_id)
VALUES (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')));

-- Bob follows Alice
INSERT INTO user_followed (followed_id, owner_id)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')));

-- Charlie follows Alice and Bob
INSERT INTO user_followed (followed_id, owner_id)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))),
       (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')));

-- Dave follows Alice
INSERT INTO user_followed (followed_id, owner_id)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')));

-- Eve follows Alice and Charlie
INSERT INTO user_followed (followed_id, owner_id)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', ''))),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')));

-- Also populate the user_followers and user_following tables for consistency
-- (These would typically be maintained by application logic or triggers)
INSERT INTO user_follower (owner_id, follower_id)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))),
       (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))),
       (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', ''))),
       (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', ''))),
       (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')));


INSERT INTO notes (id,note_type, content, created, latitude, longitude, privacy, title, owner_id, likes,end,start, rating)
VALUES (1,'CLASSIC', 'Just visited the Eiffel Tower! Amazing view from the top.', '2023-05-15 10:30:00', 48.8584, 2.2945,
        'PUBLIC', 'Eiffel Tower Visit', UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 3, null,null,1),
       (2,'CLASSIC', 'Working on a new project. Can''t wait to share it with everyone!', '2023-05-16 14:15:00', 37.7749,
        -122.4194, 'FOLLOWERS', 'New Project', UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 2, null,null,1),
       (3,'CLASSIC', 'Personal thoughts about life and the universe.', '2023-05-17 22:45:00', 34.0522, -118.2437, 'PRIVATE',
        'Private Journal', UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 0, null,null,1),
       (4,'EVENT', 'Tech conference next month. Register now!', '2023-05-18 09:00:00', 37.7833, -122.4167, 'PUBLIC',
        'Tech Conference', UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 5, '2023-06-15 09:00:00',
        '2023-06-17 18:00:00',1),
       (5,'CLASSIC', 'Check out this cool café I found in downtown!', '2023-05-19 12:30:00', 40.7128, -74.0060, 'PUBLIC',
        'Hidden Café', UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 1, null,null,1),
       (6,'CLASSIC', 'My secret fishing spot. Shhh!', '2023-05-20 07:00:00', 45.4215, -75.6972, 'FOLLOWERS', 'Fishing Spot',
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 1, null,null,1),
       (7,'EVENT', 'Birthday party at my place!', '2023-05-21 18:00:00', 51.5074, -0.1278, 'FOLLOWERS',
        'Birthday Party', UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), 2, '2023-06-10 19:00:00',
        '2023-06-11 02:00:00',1),
       (8,'CLASSIC', 'Just finished reading an amazing book!', '2023-05-22 21:15:00', 35.6762, 139.6503, 'PUBLIC',
        'Book Recommendation', UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 0, null,null,1);

INSERT INTO notes (id,title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end) VALUES
-- Madrid centro (varias notas en la misma ubicación)
(9,'Gran Via', 'Famous shopping street', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 30, '2023-01-11 12:00:00', 40.420000, -3.705000, 'CLASSIC', NULL, NULL),
(10,'Callao Square', 'Popular meeting spot', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 22, '2023-01-12 13:00:00', 40.420000, -3.705000, 'EVENT', '2023-01-12 13:00:00', '2023-01-12 13:00:00'),
(11,'Madrid Bookstore', 'Historic bookstore', 'PUBLIC', 3, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 10, '2023-01-13 14:00:00', 40.420000, -3.705000, 'CULTURAL', NULL, NULL),

-- Madrid centro, otra ubicación
(12,'Puerta del Sol', 'Central square of Madrid', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 18, '2023-01-14 10:00:00', 40.416775, -3.703790, 'EVENT', '2023-01-14 10:00:00', '2023-01-14 10:00:00'),
(13,'Churros Stand', 'Best churros in Madrid', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 12, '2023-01-15 09:00:00', 40.416775, -3.703790, 'FOOD', NULL, NULL),

-- Barcelona centro (varias notas en la misma ubicación)
(14,'Sagrada Familia', 'Gaudí masterpiece', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 50, '2023-02-01 11:00:00', 41.403706, 2.173504, 'EVENT', '2023-02-01 11:00:00', '2023-02-01 11:00:00'),
(15,'Sagrada Familia Tour', 'Guided tour', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 25, '2023-02-01 12:00:00', 41.403706, 2.173504, 'CULTURAL', NULL, NULL),
(16,'Sagrada Familia Cafe', 'Coffee nearby', 'PUBLIC', 3, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 8, '2023-02-01 13:00:00', 41.403706, 2.173504, 'FOOD', NULL, NULL),

-- Barcelona playa
(17,'Barceloneta Beach', 'Sunny beach', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 40, '2023-02-02 14:00:00', 41.378484, 2.192437, 'LANDSCAPE', NULL, NULL),

-- Sevilla
(18,'Plaza de España', 'Landmark square', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 28, '2023-03-01 10:00:00', 37.377102, -5.986400, 'CULTURAL', NULL, NULL),
(19,'Metropol Parasol', 'Modern wooden structure', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 15, '2023-03-01 11:00:00', 37.393611, -5.991944, 'CLASSIC', NULL, NULL),

-- Valencia
(20,'City of Arts', 'Cultural complex', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 34, '2023-04-01 12:00:00', 39.454769, -0.351913, 'CULTURAL', NULL, NULL),
(21,'Malvarrosa Beach', 'Popular beach', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 16, '2023-04-01 13:00:00', 39.478056, -0.331944, 'LANDSCAPE', NULL, NULL),

-- Zaragoza
(22,'Basilica del Pilar', 'Baroque church', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 20, '2023-05-01 14:00:00', 41.656063, -0.877340, 'HISTORICAL', NULL, NULL),

-- Bilbao
(23,'Guggenheim Museum', 'Modern art museum', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 22, '2023-06-01 15:00:00', 43.268661, -2.934997, 'CULTURAL', NULL, NULL),

-- Málaga
(24,'Alcazaba', 'Palatial fortification', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 19, '2023-07-01 16:00:00', 36.721302, -4.418430, 'HISTORICAL', NULL, NULL),
(25,'Picasso Museum', 'Art museum', 'PUBLIC', 3, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 13, '2023-07-01 17:00:00', 36.721302, -4.418430, 'CULTURAL', NULL, NULL),

-- Granada
(26,'Alhambra', 'Famous palace and fortress', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 40, '2023-08-01 18:00:00', 37.176487, -3.588135, 'HISTORICAL', NULL, NULL),

-- Santiago de Compostela
(27,'Cathedral of Santiago', 'Pilgrimage site', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 30, '2023-09-01 19:00:00', 42.880447, -8.545690, 'HISTORICAL', NULL, NULL),

-- Salamanca
(28,'Plaza Mayor', 'Main square', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 18, '2023-10-01 20:00:00', 40.965157, -5.663539, 'CLASSIC', NULL, NULL),

-- Córdoba
(29,'Mezquita', 'Mosque–Cathedral', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 25, '2023-11-01 21:00:00', 37.879900, -4.779380, 'HISTORICAL', NULL, NULL),

-- Alicante
(30,'Santa Barbara Castle', 'Hilltop fortress', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 14, '2023-12-01 22:00:00', 38.345170, -0.481490, 'HISTORICAL', NULL, NULL),

-- Palma de Mallorca
(31,'Cathedral of Mallorca', 'Gothic cathedral', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 20, '2024-01-01 09:00:00', 39.567984, 2.649975, 'CULTURAL', NULL, NULL),

-- San Sebastián
(32,'La Concha Beach', 'Famous urban beach', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 17, '2024-02-01 10:00:00', 43.314000, -1.986000, 'LANDSCAPE', NULL, NULL),

-- Oviedo
(33,'Cathedral of Oviedo', 'Gothic cathedral', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 12, '2024-03-01 11:00:00', 43.362343, -5.844764, 'HISTORICAL', NULL, NULL),

-- León
(34,'Casa Botines', 'Modernist building', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 10, '2024-04-01 12:00:00', 42.599834, -5.570324, 'CLASSIC', NULL, NULL),

-- Toledo
(35,'Alcázar of Toledo', 'Stone fortification', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 16, '2024-05-01 13:00:00', 39.862833, -4.027323, 'HISTORICAL', NULL, NULL),

-- Burgos
(36,'Burgos Cathedral', 'Gothic cathedral', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 18, '2024-06-01 14:00:00', 42.340021, -3.704444, 'CULTURAL', NULL, NULL),

-- Cádiz
(37,'La Caleta Beach', 'Popular city beach', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 13, '2024-07-01 15:00:00', 36.533333, -6.300000, 'LANDSCAPE', NULL, NULL),

-- Murcia
(38,'Cathedral of Murcia', 'Baroque cathedral', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 11, '2024-08-01 16:00:00', 37.983444, -1.129889, 'CULTURAL', NULL, NULL),

-- Valladolid
(39,'Plaza Mayor Valladolid', 'Main square', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 10, '2024-09-01 17:00:00', 41.652950, -4.728388, 'CLASSIC', NULL, NULL),

-- Almería
(40,'Alcazaba of Almería', 'Medieval fortress', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 9, '2024-10-01 18:00:00', 36.840280, -2.467780, 'HISTORICAL', NULL, NULL),

-- Huelva
(41,'Muelle del Tinto', 'Historic pier', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 8, '2024-11-01 19:00:00', 37.255000, -6.959722, 'CLASSIC', NULL, NULL),

-- Lugo
(42,'Roman Walls of Lugo', 'Ancient Roman walls', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 7, '2024-12-01 20:00:00', 43.009444, -7.556111, 'HISTORICAL', NULL, NULL),

-- París, Torre Eiffel (varias notas)
(43,'Eiffel Tower', 'Iconic landmark', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 60, '2023-03-01 10:00:00', 48.858370, 2.294481, 'EVENT', '2023-03-01 10:00:00', '2023-03-01 10:00:00'),
(44,'Eiffel Picnic', 'Picnic under the tower', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 15, '2023-03-01 11:00:00', 48.858370, 2.294481, 'FOOD', NULL, NULL),

-- París, Louvre
(45,'Louvre Museum', 'Famous art museum', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 35, '2023-03-02 12:00:00', 48.860611, 2.337644, 'CULTURAL', NULL, NULL),

-- Roma, Coliseo
(46,'Colosseum', 'Ancient Roman amphitheatre', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 45, '2023-04-01 09:00:00', 41.890210, 12.492231, 'HISTORICAL', NULL, NULL),

-- Berlín, Puerta de Brandeburgo
(47,'Brandenburg Gate', 'Historic city gate', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 20, '2023-05-01 13:00:00', 52.516275, 13.377704, 'HISTORICAL', NULL, NULL),

-- Londres, Big Ben
(48,'Big Ben', 'Famous clock tower', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 32, '2023-06-01 12:00:00', 51.500729, -0.124625, 'CLASSIC', NULL, NULL),

-- Nueva York, Central Park (varias notas)
(49,'Central Park', 'Large city park', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 70, '2023-07-01 15:00:00', 40.785091, -73.968285, 'LANDSCAPE', NULL, NULL),
(50,'Central Park Picnic', 'Family picnic', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 22, '2023-07-01 16:00:00', 40.785091, -73.968285, 'FOOD', NULL, NULL),

-- Nueva York, Times Square
(51,'Times Square', 'The city that never sleeps', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 50, '2023-07-02 20:00:00', 40.758896, -73.985130, 'EVENT', '2023-07-02 20:00:00', '2023-07-02 20:00:00'),

-- Tokio, Shibuya Crossing
(52,'Shibuya Crossing', 'Busiest pedestrian crossing', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 40, '2023-08-01 18:00:00', 35.659487, 139.700044, 'EVENT', '2023-08-01 18:00:00', '2023-08-01 18:00:00'),

-- Tokio, Tokyo Tower
(53,'Tokyo Tower', 'Iconic tower in Tokyo', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 15, '2023-08-02 10:00:00', 35.658581, 139.745433, 'CLASSIC', NULL, NULL),

-- Río de Janeiro, Cristo Redentor
(54,'Christ the Redeemer', 'Famous statue', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 60, '2023-09-01 09:00:00', -22.951916, -43.210487, 'LANDSCAPE', NULL, NULL),

-- Buenos Aires, Obelisco
(55,'Obelisco', 'Iconic monument', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 25, '2023-10-01 13:00:00', -34.603684, -58.381559, 'CLASSIC', NULL, NULL),

-- Sídney, Opera House
(56,'Sydney Opera House', 'Famous performing arts center', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 55, '2023-11-01 17:00:00', -33.856784, 151.215297, 'CULTURAL', NULL, NULL),

-- El Cairo, Pirámides de Giza
(57,'Great Pyramid of Giza', 'Ancient wonder', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 100, '2023-12-01 08:00:00', 29.979235, 31.134202, 'HISTORICAL', NULL, NULL),

-- Moscú, Plaza Roja
(58,'Red Square', 'Historic square', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 30, '2024-01-01 12:00:00', 55.753930, 37.620795, 'EVENT', '2024-01-01 12:00:00', '2024-01-01 12:00:00'),

-- Johannesburgo, Sudáfrica
(59,'Apartheid Museum', 'Museum on South African history', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 20, '2024-02-01 14:00:00', -26.234800, 27.970900, 'CULTURAL', NULL, NULL),

-- Dubái, Burj Khalifa
(60,'Burj Khalifa', 'Tallest building in the world', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 80, '2024-03-01 11:00:00', 25.197197, 55.274376, 'CLASSIC', NULL, NULL),

-- México, Chichén Itzá
(61,'Chichen Itza', 'Mayan pyramid', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 40, '2024-04-01 09:00:00', 20.684284, -88.567783, 'HISTORICAL', NULL, NULL),

-- Canadá, Cataratas del Niágara
(62,'Niagara Falls', 'Spectacular waterfalls', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 70, '2024-05-01 15:00:00', 43.096214, -79.037739, 'LANDSCAPE', NULL, NULL),

-- India, Taj Mahal
(63,'Taj Mahal', 'Marble mausoleum', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 90, '2024-06-01 07:00:00', 27.175015, 78.042155, 'HISTORICAL', NULL, NULL),

-- China, Gran Muralla
(64,'Great Wall of China', 'Ancient wall', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 110, '2024-07-01 10:00:00', 40.431908, 116.570374, 'HISTORICAL', NULL, NULL),

-- Australia, Bondi Beach
(65,'Bondi Beach', 'Popular surfing spot', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 35, '2024-08-01 16:00:00', -33.890842, 151.274292, 'LANDSCAPE', NULL, NULL),

-- Brasil, Pan de Azúcar
(66,'Sugarloaf Mountain', 'Famous peak in Rio', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 28, '2024-09-01 09:00:00', -22.948611, -43.156389, 'LANDSCAPE', NULL, NULL),

-- Chile, Valle de la Luna
(67,'Valle de la Luna', 'Unique desert landscape', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 15, '2024-10-01 18:00:00', -22.921111, -68.244444, 'LANDSCAPE', NULL, NULL);

-- Bob likes Alice's Eiffel Tower note and New Project note
INSERT INTO user_likes_notes (note_id, user_id)
VALUES (1, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))),
       (2, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')));

-- Charlie likes Alice's Eiffel Tower note and Bob's Tech Conference note
INSERT INTO user_likes_notes (note_id, user_id)
VALUES (1, UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))),
       (4, UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')));

-- Dave likes Bob's Tech Conference note and Charlie's Hidden Café note
INSERT INTO user_likes_notes (note_id, user_id)
VALUES (4, UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', ''))),
       (5, UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')));

-- Eve likes Alice's Eiffel Tower note, Bob's Tech Conference note, and Dave's Birthday Party note
INSERT INTO user_likes_notes (note_id, user_id)
VALUES (1, UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', ''))),
       (4, UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', ''))),
       (7, UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')));

-- Alice saves Bob's Tech Conference note
INSERT INTO user_saved_notes (note_id, user_id)
VALUES (4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')));

-- Bob saves Alice's Eiffel Tower note
INSERT INTO user_saved_notes (note_id, user_id)
VALUES (1, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')));

-- Charlie saves Alice's Eiffel Tower note and Bob's Tech Conference note
INSERT INTO user_saved_notes (note_id, user_id)
VALUES (1, UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))),
       (4, UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')));

-- Eve saves Charlie's Hidden Café note
INSERT INTO user_saved_notes (note_id, user_id)
VALUES (5, UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')));