INSERT INTO users(name,username,password)
VALUES ('Aydar Rysaev','rysaev@mail.ru','$2a$10$Xw8XGdbt5ce812XXv484se3iFCmvHrHTqszIQSLFOOFkbNPqCuCsm'),
       ('Dima Ivanov','dima@gmail.com','$2a$10$Xw8XGdbt5ce812XXv484se3iFCmvHrHTqszIQSLFOOFkbNPqCuCsm');

INSERT INTO users(name,username,password)
VALUES ('Dima dima','dima@mail.ru','$2a$10$oMANf2khRWRo/L0hmV9qO.zuvZzrVLXkZaGequnHtq/Z.TD.yg5oG');



insert into tasks (title, description, status, expiration_date)
values ('Buy cheese', null, 'TODO', '2024-04-22 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2024-04-20 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2024-04-15 00:00:00');

insert into users_tasks (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');