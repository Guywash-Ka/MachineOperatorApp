


Create table if not exists worker(
  id serial,
  name varchar,
  password varchar,
  nfc varchar,
  technique_id int,
  primary key (id)

);


Create table if not exists agronomist(
                                     id serial,
                                     name varchar,
                                     password varchar,
                                     nfc varchar,
                                     role_id int,
                                     primary key (id)

);


CREATE table if not exists tasks(
    id serial,
    name varchar,
    description varchar,
    cost float,
    worker_id int,
    is_done bool,
    primary key (id),
    foreign key (worker_id) references worker(id)
);






