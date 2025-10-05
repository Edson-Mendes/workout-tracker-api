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

-- Insere usuário.
INSERT INTO tb_user(email, password) VALUES
    ('user@email.com', '$2a$10$2nkWFNOg.dQwNQeB99/Fd.o.0zwCBBG0WDxbfhY2X5k2eLHx4BA9q');

-- Insere a role do usuário acima
INSERT INTO tb_user_roles(user_id, role_id) VALUES
    (1, 1);