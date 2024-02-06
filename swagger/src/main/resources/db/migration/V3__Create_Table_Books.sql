CREATE TABLE IF NOT EXISTS book (
    id bigint NOT NULL,
    author character varying(255) COLLATE pg_catalog."default" NOT NULL,
    launch_date date NOT NULL,
    price real NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT book_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS book_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1
    OWNED BY book.id;

ALTER TABLE ONLY book ALTER COLUMN id SET DEFAULT nextval('book_id_seq'::regclass);