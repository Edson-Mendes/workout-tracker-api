-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Insere as roles da aplicação.
INSERT INTO tb_role(name) VALUES
    ('user'),
    ('admin');

-- Insere usuário (senha 123456).
-- INSERT INTO tb_user(email, password) VALUES
--     ('jane.doe@email.com', '$2a$10$2nkWFNOg.dQwNQeB99/Fd.o.0zwCBBG0WDxbfhY2X5k2eLHx4BA9q');

-- Insere a role do usuário acima
-- INSERT INTO tb_user_roles(user_id, role_id) VALUES
--     (1, 1);

-- Insere workout para o usuário acima
-- INSERT INTO tb_workout(name, status, user_id) VALUES
--     ('Leg day', 'ONGOING', 1);

-- Insere exercises para o workout acima
-- INSERT INTO tb_exercise(name, sets, weight, workout_id) VALUES
--     ('Agachamento livre', 4, 30, 1),
--     ('Cadeira extensora', 4, 60, 1);

-- INSERT INTO tb_weight_history(value, created_at, exercise_id) VALUES
--     (30, '2025-10-07 13:43:45.169', 1),
--     (60, '2025-10-08 13:43:45.169', 2);