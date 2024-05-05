create table department(
    id bigint unsigned not null AUTO_INCREMENT,
    name varchar(100) not null,
    short_name varchar(100) not null,
    manager_id bigint,
    secretary_id bigint,
    primary key (id)
)