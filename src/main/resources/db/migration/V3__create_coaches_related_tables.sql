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
        foreign key (coach_id) references coaches (id),
    constraint competitors_coaches___competitor
        foreign key (competitor_id) references competitors (id)
);



