create user raamatukogu password 'raamatukogu';

grant all privileges on all tables in schema public to raamatukogu;
grant all privileges on all sequences in schema public to raamatukogu;

CREATE SEQUENCE teos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table teosed (
	id 		integer PRIMARY KEY DEFAULT nextval('teos_id_seq'::regclass),
	pealkiri	varchar(200) not null,
	aasta		integer);

CREATE SEQUENCE autor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table autorid (
	id 		integer PRIMARY KEY DEFAULT nextval('autor_id_seq'::regclass),
	nimi		varchar(200) not null,
	synniaeg	date,
	surmaaeg	date);

CREATE SEQUENCE autorlus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table autor_teos (
	id 		integer PRIMARY KEY  DEFAULT nextval('autorlus_id_seq'::regclass),
	autor_id	integer not null REFERENCES autorid(id),
	teos_id	integer not null REFERENCES teosed(id)); 

CREATE SEQUENCE lugeja_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table lugejad  (
	id 		integer PRIMARY KEY  DEFAULT nextval('lugeja_id_seq'::regclass),
	nimi		varchar(200) not null,
	synniaeg	date);

CREATE SEQUENCE laenutus_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

create table laenutused (
	id 		integer PRIMARY KEY  DEFAULT nextval('laenutus_id_seq'::regclass),
	lugeja_id	integer not null REFERENCES lugejad(id),
	teos_id		integer not null REFERENCES teosed(id),
	algus timestamp,
	lopp timestamp);

	