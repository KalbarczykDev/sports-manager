CREATE TABLE hibernate_sequences
(
    sequence_name VARCHAR(255) NOT NULL,
    next_val      BIGINT,
    PRIMARY KEY (sequence_name)
);

CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN
);


CREATE TABLE competitors
(
    id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    surname    VARCHAR(255) NOT NULL,
    salary     DOUBLE CHECK (salary >= 0),
    country    VARCHAR(255) NOT NULL,
    discipline VARCHAR(255) NOT NULL
);

create table coaches
(
    id         bigint auto_increment
        primary key,
    name       varchar(255)   not null,
    surname    varchar(255)   not null,
    country    varchar(255)   not null,
    salary     decimal(10, 2) not null,
    discipline varchar(255)   not null
);

create table competitors_coaches
(
    competitor_id bigint not null,
    coach_id      bigint not null,
    constraint competitors_coaches_pk
        primary key (competitor_id, coach_id),
    constraint competitors_coaches___coach
        foreign key (coach_id) references coaches (id)
            on DELETE cascade,
    constraint competitors_coaches___competitor
        foreign key (competitor_id) references competitors (id)
            on DELETE cascade
);



