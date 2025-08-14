CREATE TABLE competitors (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    salary DOUBLE CHECK (salary >= 0),
    country VARCHAR(255) NOT NULL,
    discipline VARCHAR(255) NOT NULL
);