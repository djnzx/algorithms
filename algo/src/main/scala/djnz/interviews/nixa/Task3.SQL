create table if not exists department(
    id integer,
    name varchar,
    location varchar
);

INSERT INTO department (id, name, location) VALUES (1, 'Executive', 'Kyiv');
INSERT INTO department (id, name, location) VALUES (2, 'Prod', 'Dnepr');
INSERT INTO department (id, name, location) VALUES (3, 'Res', 'Kyiv');
INSERT INTO department (id, name, location) VALUES (4, 'Tech', 'Texas');
INSERT INTO department (id, name, location) VALUES (5, 'Manag', 'Texas');

create table if not exists employee(
    id integer,
    name varchar,
    salary integer,
    dept_id integer
);

INSERT INTO employee (id, name, salary, dept_id) VALUES (4, 'Max', 450, 1);
INSERT INTO employee (id, name, salary, dept_id) VALUES (1, 'Jim', 500, 1);
INSERT INTO employee (id, name, salary, dept_id) VALUES (3, 'Bob', 400, 4);
INSERT INTO employee (id, name, salary, dept_id) VALUES (5, 'Serg', 700, 4);
INSERT INTO employee (id, name, salary, dept_id) VALUES (2, 'Alex', 600, 2);

select
    d.name,
    CASE when cnt IS NULL THEN 0 ELSE cnt END cnt0
from
    department d
left outer join
    (select count(*) as cnt, dept_id from employee group by 2) CNTG on (d.id = CNTG.dept_id)
order by
    cnt0 desc, d.name

