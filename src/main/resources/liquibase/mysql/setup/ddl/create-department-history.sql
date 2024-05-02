create table department_history(
    id bigint unsigned not null AUTO_INCREMENT,
    start_date DATE not null,
    end_date DATE,
    role varchar(100),
    department_id bigint unsigned,
    member_id bigint unsigned,
    primary key(id, department_id),
    constraint department_fk3 FOREIGN KEY (department_id) REFERENCES department(id),
    constraint member_fk2 FOREIGN KEY (member_id) REFERENCES member(id)
)

