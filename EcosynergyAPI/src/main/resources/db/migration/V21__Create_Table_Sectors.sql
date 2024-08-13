CREATE TABLE sectors (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO sectors (name) VALUES ('Textile');
INSERT INTO sectors (name) VALUES ('Automotive');
INSERT INTO sectors (name) VALUES ('Petrochemical');
INSERT INTO sectors (name) VALUES ('Pharmaceutical');
INSERT INTO sectors (name) VALUES ('Food');
INSERT INTO sectors (name) VALUES ('Basic');
INSERT INTO sectors (name) VALUES ('Steel');
INSERT INTO sectors (name) VALUES ('Metallurgical');
INSERT INTO sectors (name) VALUES ('Agricultural');
INSERT INTO sectors (name) VALUES ('Naval');
INSERT INTO sectors (name) VALUES ('Electroelectronic');