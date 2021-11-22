CREATE ROLE userservice WITH
    LOGIN
    NOSUPERUSER
    CREATEDB
    CREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'pw123';

CREATE DATABASE userservice
WITH
OWNER = userservice
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

\connect userservice ;


CREATE TABLE users (
    id bigint NOT NULL,
    created_date timestamp without time zone NOT NULL,
    email character varying(255),
    modified_date timestamp without time zone NOT NULL,
    name character varying(255),
    user_id character varying(255),
    version integer,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_id UNIQUE (user_id)
);

ALTER TABLE users OWNER to userservice;

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE users_seq OWNER TO userservice;
