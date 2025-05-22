create table if not exists users
(
    user_id  binary(16)   not null
        primary key,
    code     varchar(255) not null,
    email    varchar(255) not null,
    enabled  bit          not null default 0,
    password varchar(255) not null,
    username varchar(255) not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table if not exists notes
(
    note_type varchar(31)                             not null,
    id        int auto_increment
        primary key,
    content   varchar(255)                            null,
    created   datetime(6)                             not null,
    latitude  double                                  not null,
    likes     int                                     null default 0,
    longitude double                                  not null,
    privacy   enum ('FOLLOWERS', 'PRIVATE', 'PUBLIC') not null,
    rating    int                                     null default 10,
    title     varchar(255)                            not null,
    end       datetime(6)                             null default null,
    start     datetime(6)                             null default null,
    owner_id  binary(16)                              not null,
    constraint FK5n5jgcd6tqt248r97q0yrt3xp
        foreign key (owner_id) references users (user_id)
);

create table if not exists user_likes_notes
(
    id      int auto_increment
        primary key,
    note_id int        not null,
    user_id binary(16) not null,
    constraint FK2ctyq1dresxob2k5ljf2xwxqm
        foreign key (user_id) references users (user_id),
    constraint FK71aaf5hmkyahgp7ow03hvw4tt
        foreign key (note_id) references notes (id)
);

create table if not exists user_saved_notes
(
    id      int auto_increment
        primary key,
    note_id int        not null,
    user_id binary(16) not null,
    constraint FK21cxdp9s6w8w765jg286uwlam
        foreign key (user_id) references users (user_id),
    constraint FKbc3v9iy2yotbcllt1dn578lh1
        foreign key (note_id) references notes (id)
);