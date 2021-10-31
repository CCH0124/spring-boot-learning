CREATE USER itachi WITH ENCRYPTED PASSWORD '12345678';

CREATE DATABASE cch
    WITH
    OWNER = itachi
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
ALTER USER itachi WITH SUPERUSER;
grant all privileges on database cch  to itachi;

\c cch

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS public.user;
CREATE TABLE IF NOT EXISTS public.user (
    id      uuid DEFAULT uuid_generate_v4() primary key,
    username varchar(20) NOT NULL,
    password varchar(120) NOT NULL,
);
ALTER TABLE public.user OWNER TO itachi;

INSERT INTO public.user (username, password) VALUES ('itachi', '123456'), ('mary', '123456'), ('madara', '12345678');

DROP TABLE IF EXISTS public.persistent_logins;
CREATE TABLE IF NOT EXISTS public.persistent_logins (
    username varchar(64) NOT NULL,
    series varchar(64) NOT NULL primary key,
    token varchar(64) NOT NULL,
    last_used timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);