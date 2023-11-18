


Create table if not exists worker(
  id serial,
  name varchar,
  password varchar unique ,
  nfc varchar unique ,

  primary key (id)

);


Create table if not exists agronomist(
                                     id serial,
                                     name varchar unique ,
                                     password varchar unique ,
                                     nfc varchar unique ,

                                     primary key (id)

);




CREATE table if not exists templates (
    id serial,
    title varchar unique ,
    primary key (id)
--     foreign key (requiredFields) references task_fields(id)

);
CREATE table if not exists task_fields  (
                                            id serial,
                                            name varchar unique ,

                                            primary key (id)


);

CREATE TABLE if not exists templates_task_fields(
    templates_id int,
    foreign key (templates_id) references templates(id),
    task_fields_id int,
    foreign key (task_fields_id) references task_fields(id),
    primary key (templates_id,task_fields_id)
);


CREATE TABLE if not exists task(
    id serial,
    agronom_id int,
    worker_id int,
    template_id int,
    arr varchar[],
    primary key (id),
    foreign key (agronom_id) references agronomist(id),
    foreign key (worker_id) references worker(id),
    foreign key (template_id) references templates(id)

);

CREATE TABLE if not exists operation(
    id serial,
    name varchar,
    primary key (id)
);
Create table if not exists farm_field(
    id serial,
    name varchar,
    primary key (id)

);

CREATE TABLE if not exists transport (
                                         id serial,
                                         name varchar,
                                         primary key (id)
);

CREATE TABLE if not exists agregat (
                                         id serial,
                                         name varchar,
                                         primary key (id)
);


CREATE TABLE if not exists water (
                                       id serial,
                                       name varchar,
                                       primary key (id)
);






