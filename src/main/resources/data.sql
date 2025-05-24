delete from user_followed;
delete from user_followers;
delete from user_following;
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
INSERT INTO user_followers (user_id, follower_id)
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

INSERT INTO user_following (user_id, following_id)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))),
       (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', ''))),
       (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))),
       (UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
        UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', ''))),
       (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')));

INSERT INTO notes (id,note_type, content, created, latitude, longitude, privacy, title, owner_id, likes,end,start)
VALUES (1,'Note', 'Just visited the Eiffel Tower! Amazing view from the top.', '2023-05-15 10:30:00', 48.8584, 2.2945,
        'PUBLIC', 'Eiffel Tower Visit', UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 3, null,null),
       (2,'Note', 'Working on a new project. Can''t wait to share it with everyone!', '2023-05-16 14:15:00', 37.7749,
        -122.4194, 'FOLLOWERS', 'New Project', UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 2, null,null),
       (3,'Note', 'Personal thoughts about life and the universe.', '2023-05-17 22:45:00', 34.0522, -118.2437, 'PRIVATE',
        'Private Journal', UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 0, null,null),
       (4,'EventNote', 'Tech conference next month. Register now!', '2023-05-18 09:00:00', 37.7833, -122.4167, 'PUBLIC',
        'Tech Conference', UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')), 5, '2023-06-15 09:00:00',
        '2023-06-17 18:00:00'),
       (5,'Note', 'Check out this cool café I found in downtown!', '2023-05-19 12:30:00', 40.7128, -74.0060, 'PUBLIC',
        'Hidden Café', UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 1, null,null),
       (6,'Note', 'My secret fishing spot. Shhh!', '2023-05-20 07:00:00', 45.4215, -75.6972, 'FOLLOWERS', 'Fishing Spot',
        UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 1, null,null),
       (7,'EventNote', 'Birthday party at my place!', '2023-05-21 18:00:00', 51.5074, -0.1278, 'FOLLOWERS',
        'Birthday Party', UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')), 2, '2023-06-10 19:00:00',
        '2023-06-11 02:00:00'),
       (8,'Note', 'Just finished reading an amazing book!', '2023-05-22 21:15:00', 35.6762, 139.6503, 'PUBLIC',
        'Book Recommendation', UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 0, null,null);

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