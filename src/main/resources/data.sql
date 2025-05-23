delete from user_likes_notes;
delete from notes;
DELETE FROM user_followed;
DELETE FROM user_follower;
delete from users;

-- Usuarios de prueba
INSERT INTO users (user_id, username, password, email, code, rol)
VALUES
    (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'user1', 'user1', 'user1@example.com', 1234, 'PREMIUM'),
    (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 'user2', 'user2', 'user2@example.com', 1234, 'FREE'),
    (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 'user3', 'user3', 'user3@example.com', 1234, 'FREE'),
    (UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), 'user4', 'user4', 'user4@example.com', 1234, 'PREMIUM'),
    (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 'user5', 'user5', 'user5@example.com', 1234, 'FREE');

-- user_follower: owner_id = seguido, follower_id = seguidor
INSERT INTO user_follower (owner_id, follower_id)
VALUES
    (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))), -- user2 sigue a user1
    (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))), -- user3 sigue a user1
    (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', ''))), -- user4 sigue a user2
    (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', ''))); -- user5 sigue a user2
-- user_followed: owner_id = quien sigue, followed_id = seguido
INSERT INTO user_followed (owner_id, followed_id)
VALUES
    (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))), -- user1 sigue a user2
    (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))), -- user1 sigue a user3
    (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', ''))), -- user2 sigue a user4
    (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', ''))); -- user3 sigue a user5

INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end) VALUES
-- Madrid centro (varias notas en la misma ubicación)
('Gran Via', 'Famous shopping street', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 30, '2023-01-11 12:00:00', 40.420000, -3.705000, 'CLASSIC', NULL, NULL),
('Callao Square', 'Popular meeting spot', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 22, '2023-01-12 13:00:00', 40.420000, -3.705000, 'EVENT', '2023-01-12 13:00:00', '2023-01-12 13:00:00'),
('Madrid Bookstore', 'Historic bookstore', 'PUBLIC', 3, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 10, '2023-01-13 14:00:00', 40.420000, -3.705000, 'CULTURAL', NULL, NULL),

-- Madrid centro, otra ubicación
('Puerta del Sol', 'Central square of Madrid', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 18, '2023-01-14 10:00:00', 40.416775, -3.703790, 'EVENT', '2023-01-14 10:00:00', '2023-01-14 10:00:00'),
('Churros Stand', 'Best churros in Madrid', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 12, '2023-01-15 09:00:00', 40.416775, -3.703790, 'FOOD', NULL, NULL),

-- Barcelona centro (varias notas en la misma ubicación)
('Sagrada Familia', 'Gaudí masterpiece', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 50, '2023-02-01 11:00:00', 41.403706, 2.173504, 'EVENT', '2023-02-01 11:00:00', '2023-02-01 11:00:00'),
('Sagrada Familia Tour', 'Guided tour', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 25, '2023-02-01 12:00:00', 41.403706, 2.173504, 'CULTURAL', NULL, NULL),
('Sagrada Familia Cafe', 'Coffee nearby', 'PUBLIC', 3, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 8, '2023-02-01 13:00:00', 41.403706, 2.173504, 'FOOD', NULL, NULL),

-- Barcelona playa
('Barceloneta Beach', 'Sunny beach', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 40, '2023-02-02 14:00:00', 41.378484, 2.192437, 'LANDSCAPE', NULL, NULL),

-- Sevilla
('Plaza de España', 'Landmark square', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 28, '2023-03-01 10:00:00', 37.377102, -5.986400, 'CULTURAL', NULL, NULL),
('Metropol Parasol', 'Modern wooden structure', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 15, '2023-03-01 11:00:00', 37.393611, -5.991944, 'CLASSIC', NULL, NULL),

-- Valencia
('City of Arts', 'Cultural complex', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 34, '2023-04-01 12:00:00', 39.454769, -0.351913, 'CULTURAL', NULL, NULL),
('Malvarrosa Beach', 'Popular beach', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 16, '2023-04-01 13:00:00', 39.478056, -0.331944, 'LANDSCAPE', NULL, NULL),

-- Zaragoza
('Basilica del Pilar', 'Baroque church', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 20, '2023-05-01 14:00:00', 41.656063, -0.877340, 'HISTORICAL', NULL, NULL),

-- Bilbao
('Guggenheim Museum', 'Modern art museum', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 22, '2023-06-01 15:00:00', 43.268661, -2.934997, 'CULTURAL', NULL, NULL),

-- Málaga
('Alcazaba', 'Palatial fortification', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 19, '2023-07-01 16:00:00', 36.721302, -4.418430, 'HISTORICAL', NULL, NULL),
('Picasso Museum', 'Art museum', 'PUBLIC', 3, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 13, '2023-07-01 17:00:00', 36.721302, -4.418430, 'CULTURAL', NULL, NULL),

-- Granada
('Alhambra', 'Famous palace and fortress', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 40, '2023-08-01 18:00:00', 37.176487, -3.588135, 'HISTORICAL', NULL, NULL),

-- Santiago de Compostela
('Cathedral of Santiago', 'Pilgrimage site', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 30, '2023-09-01 19:00:00', 42.880447, -8.545690, 'HISTORICAL', NULL, NULL),

-- Salamanca
('Plaza Mayor', 'Main square', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 18, '2023-10-01 20:00:00', 40.965157, -5.663539, 'CLASSIC', NULL, NULL),

-- Córdoba
('Mezquita', 'Mosque–Cathedral', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 25, '2023-11-01 21:00:00', 37.879900, -4.779380, 'HISTORICAL', NULL, NULL),

-- Alicante
('Santa Barbara Castle', 'Hilltop fortress', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 14, '2023-12-01 22:00:00', 38.345170, -0.481490, 'HISTORICAL', NULL, NULL),

-- Palma de Mallorca
('Cathedral of Mallorca', 'Gothic cathedral', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 20, '2024-01-01 09:00:00', 39.567984, 2.649975, 'CULTURAL', NULL, NULL),

-- San Sebastián
('La Concha Beach', 'Famous urban beach', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 17, '2024-02-01 10:00:00', 43.314000, -1.986000, 'LANDSCAPE', NULL, NULL),

-- Oviedo
('Cathedral of Oviedo', 'Gothic cathedral', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 12, '2024-03-01 11:00:00', 43.362343, -5.844764, 'HISTORICAL', NULL, NULL),

-- León
('Casa Botines', 'Modernist building', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 10, '2024-04-01 12:00:00', 42.599834, -5.570324, 'CLASSIC', NULL, NULL),

-- Toledo
('Alcázar of Toledo', 'Stone fortification', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 16, '2024-05-01 13:00:00', 39.862833, -4.027323, 'HISTORICAL', NULL, NULL),

-- Burgos
('Burgos Cathedral', 'Gothic cathedral', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 18, '2024-06-01 14:00:00', 42.340021, -3.704444, 'CULTURAL', NULL, NULL),

-- Cádiz
('La Caleta Beach', 'Popular city beach', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 13, '2024-07-01 15:00:00', 36.533333, -6.300000, 'LANDSCAPE', NULL, NULL),

-- Murcia
('Cathedral of Murcia', 'Baroque cathedral', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 11, '2024-08-01 16:00:00', 37.983444, -1.129889, 'CULTURAL', NULL, NULL),

-- Valladolid
('Plaza Mayor Valladolid', 'Main square', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 10, '2024-09-01 17:00:00', 41.652950, -4.728388, 'CLASSIC', NULL, NULL),

-- Almería
('Alcazaba of Almería', 'Medieval fortress', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 9, '2024-10-01 18:00:00', 36.840280, -2.467780, 'HISTORICAL', NULL, NULL),

-- Huelva
('Muelle del Tinto', 'Historic pier', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 8, '2024-11-01 19:00:00', 37.255000, -6.959722, 'CLASSIC', NULL, NULL),

-- Lugo
('Roman Walls of Lugo', 'Ancient Roman walls', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 7, '2024-12-01 20:00:00', 43.009444, -7.556111, 'HISTORICAL', NULL, NULL),

-- París, Torre Eiffel (varias notas)
('Eiffel Tower', 'Iconic landmark', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 60, '2023-03-01 10:00:00', 48.858370, 2.294481, 'EVENT', '2023-03-01 10:00:00', '2023-03-01 10:00:00'),
('Eiffel Picnic', 'Picnic under the tower', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 15, '2023-03-01 11:00:00', 48.858370, 2.294481, 'FOOD', NULL, NULL),

-- París, Louvre
('Louvre Museum', 'Famous art museum', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 35, '2023-03-02 12:00:00', 48.860611, 2.337644, 'CULTURAL', NULL, NULL),

-- Roma, Coliseo
('Colosseum', 'Ancient Roman amphitheatre', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 45, '2023-04-01 09:00:00', 41.890210, 12.492231, 'HISTORICAL', NULL, NULL),

-- Berlín, Puerta de Brandeburgo
('Brandenburg Gate', 'Historic city gate', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 20, '2023-05-01 13:00:00', 52.516275, 13.377704, 'HISTORICAL', NULL, NULL),

-- Londres, Big Ben
('Big Ben', 'Famous clock tower', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 32, '2023-06-01 12:00:00', 51.500729, -0.124625, 'CLASSIC', NULL, NULL),

-- Nueva York, Central Park (varias notas)
('Central Park', 'Large city park', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 70, '2023-07-01 15:00:00', 40.785091, -73.968285, 'LANDSCAPE', NULL, NULL),
('Central Park Picnic', 'Family picnic', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 22, '2023-07-01 16:00:00', 40.785091, -73.968285, 'FOOD', NULL, NULL),

-- Nueva York, Times Square
('Times Square', 'The city that never sleeps', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 50, '2023-07-02 20:00:00', 40.758896, -73.985130, 'EVENT', '2023-07-02 20:00:00', '2023-07-02 20:00:00'),

-- Tokio, Shibuya Crossing
('Shibuya Crossing', 'Busiest pedestrian crossing', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 40, '2023-08-01 18:00:00', 35.659487, 139.700044, 'EVENT', '2023-08-01 18:00:00', '2023-08-01 18:00:00'),

-- Tokio, Tokyo Tower
('Tokyo Tower', 'Iconic tower in Tokyo', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 15, '2023-08-02 10:00:00', 35.658581, 139.745433, 'CLASSIC', NULL, NULL),

-- Río de Janeiro, Cristo Redentor
('Christ the Redeemer', 'Famous statue', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 60, '2023-09-01 09:00:00', -22.951916, -43.210487, 'LANDSCAPE', NULL, NULL),

-- Buenos Aires, Obelisco
('Obelisco', 'Iconic monument', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 25, '2023-10-01 13:00:00', -34.603684, -58.381559, 'CLASSIC', NULL, NULL),

-- Sídney, Opera House
('Sydney Opera House', 'Famous performing arts center', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 55, '2023-11-01 17:00:00', -33.856784, 151.215297, 'CULTURAL', NULL, NULL),

-- El Cairo, Pirámides de Giza
('Great Pyramid of Giza', 'Ancient wonder', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 100, '2023-12-01 08:00:00', 29.979235, 31.134202, 'HISTORICAL', NULL, NULL),

-- Moscú, Plaza Roja
('Red Square', 'Historic square', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 30, '2024-01-01 12:00:00', 55.753930, 37.620795, 'EVENT', '2024-01-01 12:00:00', '2024-01-01 12:00:00'),

-- Johannesburgo, Sudáfrica
('Apartheid Museum', 'Museum on South African history', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 20, '2024-02-01 14:00:00', -26.234800, 27.970900, 'CULTURAL', NULL, NULL),

-- Dubái, Burj Khalifa
('Burj Khalifa', 'Tallest building in the world', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 80, '2024-03-01 11:00:00', 25.197197, 55.274376, 'CLASSIC', NULL, NULL),

-- México, Chichén Itzá
('Chichen Itza', 'Mayan pyramid', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 40, '2024-04-01 09:00:00', 20.684284, -88.567783, 'HISTORICAL', NULL, NULL),

-- Canadá, Cataratas del Niágara
('Niagara Falls', 'Spectacular waterfalls', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 70, '2024-05-01 15:00:00', 43.096214, -79.037739, 'LANDSCAPE', NULL, NULL),

-- India, Taj Mahal
('Taj Mahal', 'Marble mausoleum', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 90, '2024-06-01 07:00:00', 27.175015, 78.042155, 'HISTORICAL', NULL, NULL),

-- China, Gran Muralla
('Great Wall of China', 'Ancient wall', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 110, '2024-07-01 10:00:00', 40.431908, 116.570374, 'HISTORICAL', NULL, NULL),

-- Australia, Bondi Beach
('Bondi Beach', 'Popular surfing spot', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 35, '2024-08-01 16:00:00', -33.890842, 151.274292, 'LANDSCAPE', NULL, NULL),

-- Brasil, Pan de Azúcar
('Sugarloaf Mountain', 'Famous peak in Rio', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 28, '2024-09-01 09:00:00', -22.948611, -43.156389, 'LANDSCAPE', NULL, NULL),

-- Chile, Valle de la Luna
('Valle de la Luna', 'Unique desert landscape', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 15, '2024-10-01 18:00:00', -22.921111, -68.244444, 'LANDSCAPE', NULL, NULL);
