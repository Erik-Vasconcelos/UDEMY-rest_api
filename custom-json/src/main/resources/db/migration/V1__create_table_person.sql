CREATE TABLE person (
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    postal_code character varying(9)
);

CREATE SEQUENCE person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY person ALTER COLUMN id SET DEFAULT nextval('person_id_seq'::regclass);