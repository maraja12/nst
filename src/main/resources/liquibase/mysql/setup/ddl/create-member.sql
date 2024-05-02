create table member(
    id bigint unsigned not null AUTO_INCREMENT,
    firstname varchar(100) not null,
    lastname varchar(100) not null,
    role varchar(100),
    department_id bigint unsigned,
    academic_title_id bigint unsigned,
    education_title_id bigint unsigned,
    scientific_field_id bigint unsigned,
    primary key(id),
    constraint department_fk2 FOREIGN KEY (department_id) REFERENCES department(id),
    constraint academic_title_fk FOREIGN KEY (academic_title_id) REFERENCES academic_title(id),
    constraint education_title_fk FOREIGN KEY (education_title_id) REFERENCES education_title(id),
    constraint scientific_field_fk FOREIGN KEY (scientific_field_id) REFERENCES scientific_field(id)
)