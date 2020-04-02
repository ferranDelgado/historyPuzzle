CREATE TABLE card (
    id BIGSERIAL PRIMARY KEY,
    title varchar(250) NOT NULL,
    year integer NOT NULL,
    month integer DEFAULT NULL,
    day integer DEFAULT NULL,
    difficulty integer DEFAULT 0,
    info TEXT,
    wikipedia_link varchar (250) DEFAULT '',
    image_link varchar (250) DEFAULT ''
)