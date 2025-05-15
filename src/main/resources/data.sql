delete from user_likes_notes;
delete from notes;
delete from users;


-- Insert test users with UUIDs in binary(16) format
INSERT INTO users (user_id, username, password, email, code, rol)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'user1', 'user1', 'user1@example.com', 1234, 'PREMIUM');

INSERT INTO users (user_id, username, password, email, code, rol)
VALUES (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 'user2', 'user2', 'user2@example.com', 1234, 'FREE');

-- Madrid, Spain (Center)
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Puerta del Sol', 'Historic square in the heart of Madrid', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 10, '2023-01-01 12:00:00', 40.416775, -3.703790, 'EVENT', '2023-01-01 12:00:00', '2023-01-01 12:00:00');

-- Notes within 5km of Madrid center
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Retiro Park', 'Beautiful park with a lake', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 15, '2023-01-02 14:30:00', 40.415504, -3.682909, 'CLASSIC', '2023-01-02 14:30:00', '2023-01-02 14:30:00');

INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Royal Palace', 'Official residence of the Spanish Royal Family', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 8, '2023-01-03 10:15:00', 40.418053, -3.714312, 'EVENT', '2023-01-03 10:15:00', '2023-01-03 10:15:00');

INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Mercado de San Miguel', 'Popular food market with various cuisines', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 12, '2023-01-04 18:45:00', 40.415421, -3.709658, 'EVENT', '2023-01-04 18:45:00', '2023-01-04 18:45:00');

INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Prado Museum', 'One of the greatest art museums in the world', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 20, '2023-01-05 11:30:00', 40.413756, -3.692094, 'EVENT', '2023-01-05 11:30:00', '2023-01-05 11:30:00');

-- Notes just outside 5km of Madrid center
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Madrid Rio Park', 'Urban park along the Manzanares River', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 7, '2023-01-06 16:20:00', 40.395857, -3.719488, 'CLASSIC', '2023-01-06 16:20:00', '2023-01-06 16:20:00');

-- Barcelona (far from Madrid)
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Sagrada Familia', 'Famous basilica designed by Antoni Gaudí', 'PUBLIC', 5, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 25, '2023-01-07 09:45:00', 41.403706, 2.173504, 'EVENT', '2023-01-07 09:45:00', '2023-01-07 09:45:00');

-- Valencia (far from Madrid)
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('City of Arts and Sciences', 'Cultural and architectural complex', 'PUBLIC', 5, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 18, '2023-01-08 13:10:00', 39.454769, -0.351913, 'EVENT', '2023-01-08 13:10:00', '2023-01-08 13:10:00');

-- Seville (far from Madrid)
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Plaza de España', 'Landmark square with beautiful architecture', 'PUBLIC', 4, UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 14, '2023-01-09 15:30:00', 37.377102, -5.986400, 'CULTURAL', '2023-01-09 15:30:00', '2023-01-09 15:30:00');

-- Event notes (Madrid area)
INSERT INTO notes (title, content, privacy, rating, owner_id, likes, created, latitude, longitude, note_type, start, end)
VALUES ('Summer Concert', 'Live music event at the park', 'PUBLIC', 4, UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 9, '2023-01-10 20:00:00', 40.419946, -3.699437, 'EVENT', '2023-07-15 19:00:00', '2023-07-15 23:00:00');