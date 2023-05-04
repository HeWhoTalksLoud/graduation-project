create table if not exists users
(
    id         bigserial primary key,
    email      varchar(100) not null,
    first_name varchar(100) not null,
    last_name  varchar(100) not null,
    phone      varchar(50)  not null,
    password   varchar(64)  not null,
    image      varchar(100),
    role       int
);

create table if not exists ads
(
    id                bigserial primary key,
    author_id         bigint       not null,
    title             varchar(150) not null,
    description       varchar,
    price             int          not null,
    image             varchar(100),
    publish_date_time timestamp,
    foreign key (author_id) references users (id) on delete cascade on update no action
);

create table if not exists comments
(
    id                 bigserial primary key,
    author_id          bigint  not null,
    ad_id              bigint  not null,
    creation_date_time timestamp,
    text               varchar not null,
    foreign key (author_id) references users (id) on delete cascade on update no action,
    foreign key (ad_id) references ads (id) on delete cascade on update no action
);