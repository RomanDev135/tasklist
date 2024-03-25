create schema if not exists tasklist;

create table if not exists users
(
    id bigserial primary key,
    name varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null
    );

create table if not exists tasks
(
    id bigserial primary key,
    title varchar(255) not null,
    description varchar(255) null,
    status varchar(255) not null,
    expiration_date timestamp null
    );

create table if not exists users_tasks
(
    user_id bigint not null,
    task_id bigint not null,
    primary key(user_id, task_id),
    constraint fk_users_tasks_users foreign key (user_id) references users(id) on delete cascade on update no action,
    constraint fk_users_tasks_tasks foreign key (task_id) references tasks(id) on delete cascade on update no action
    );

create table if not exists users_roles
(
    user_id bigint not null,
    role varchar(255) not null,
    primary key(user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users(id) on delete cascade on update no action
    );

-- CREATE TABLE IF NOT EXISTS users_tasks
-- (
--     user_id BIGINT NOT NULL,
--     task_id BIGINT NOT NULL,
--     PRIMARY KEY (user_id, task_id),
--     CONSTRAINT fk_users_tasks_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION,
--     CONSTRAINT fk_users_tasks_tasks FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE ON UPDATE NO ACTION
--     );
--
-- CREATE TABLE IF NOT EXISTS users_roles
-- (
--     user_id BIGINT NOT NULL,
--     role VARCHAR(255) NOT NULL,
--     PRIMARY KEY (user_id, role),
--     CONSTRAINT fk_users_roles_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE NO ACTION
--     );