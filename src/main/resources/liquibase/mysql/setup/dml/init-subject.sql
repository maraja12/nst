insert into subject(name, espb, department_id)
values("subj1", 5, (select (id) from department WHERE name="department1"));
insert into subject(name, espb, department_id)
values("subj2", 5, (select (id) from department WHERE name="department2"));