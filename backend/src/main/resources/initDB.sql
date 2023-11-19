


Create table if not exists worker(
  id serial,
  name varchar not null ,
  password varchar unique not null ,
  nfc varchar unique not null ,
  salary float not null ,
  update_time bigint not null ,
  workiing_hours int,

  primary key (id)

);


Create table if not exists agronomist(
                                     id serial,
                                     name varchar unique not null ,
                                     password varchar unique not null ,
                                     nfc varchar unique not null ,
                                     salary float not null ,
                                     update_time bigint not null ,
                                     working_hours int,
                                     primary key (id)

);




CREATE table if not exists templates (
    id serial,
    title varchar unique not null ,
    primary key (id)
--     foreign key (requiredFields) references task_fields(id)

);
CREATE table if not exists task_fields  (
                                            id serial,
                                            name varchar unique not null ,

                                            primary key (id)


);

CREATE TABLE if not exists templates_task_fields(
    templates_id int not null ,
    foreign key (templates_id) references templates(id),
    task_fields_id int not null ,
    foreign key (task_fields_id) references task_fields(id),
    primary key (templates_id,task_fields_id)
);


CREATE TABLE if not exists task(
    id serial,
    agronom_id int not null ,
    worker_id int not null ,
    template_id int not null ,
    importance int not null , --1
    price float not null , --1
    is_done boolean not null , --1
    arr integer[] not null ,
    primary key (id),
    foreign key (agronom_id) references agronomist(id),
    foreign key (worker_id) references worker(id),
    foreign key (template_id) references templates(id)

);

CREATE TABLE if not exists operation(
    id serial,
    name varchar not null ,
    primary key (id)
);
Create table if not exists farm_field(
    id serial,
    name varchar not null ,
--     type int not null ,
--     coordinates varchar not null ,
    primary key (id)

);

CREATE TABLE if not exists transport (
                                         id serial,
                                         name varchar not null ,
                                         fuel int,
                                         coordinate varchar,

                                         primary key (id)
);

CREATE TABLE if not exists agregat (
                                         id serial,
                                         name varchar not null ,
                                         primary key (id)
);


CREATE TABLE if not exists water (
                                       id serial,
                                       name varchar not null ,
                                       primary key (id)
);






