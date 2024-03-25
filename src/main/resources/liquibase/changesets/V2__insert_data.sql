insert into users (name, username, password)
values ('John Doe', 'johndoe1@gmail.com', '$2a$10$3kwQMqFU7gll0seEQ1McgOFl.gdNFfRrP.O9Ofzlm.B8EQm2d/sW.'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$1GQ9ZyjB0n0caY9EdBOJdejXhXMpp3lOZCB2cxAAeDOV7gO9DvmLO');


insert into tasks(title, description, status, expiration_date)
values ('Buy cheese', null, 'TODO', '2024-02-08 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2024-02-08 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Asc about meeting', 'TODO','2024-02-08 00:00:00');


insert into users_tasks (task_id, user_id)
values (1,2),
       (2,2),
       (3,2),
       (4,1);


insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');