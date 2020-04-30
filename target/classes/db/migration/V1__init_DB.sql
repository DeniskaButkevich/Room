create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE IF NOT EXISTS room (
    id int8 not null,
    lamp boolean not null,
    name varchar(255),
    country_id int2,
    primary key (id)
);


CREATE TABLE IF NOT EXISTS sxgeo_country (
  id smallint check (id > 0) NOT NULL,
  iso varchar(2),
  continent varchar(2),
  name_ru varchar(128),
  name_en varchar(128),
  lat decimal(6,2),
  lon decimal(6,2),
  timezone varchar(30),
  PRIMARY KEY (id)
);

alter table if exists room add constraint FKame97tyriq1fgl10c93sdru7l foreign key (country_id) references sxgeo_country;

