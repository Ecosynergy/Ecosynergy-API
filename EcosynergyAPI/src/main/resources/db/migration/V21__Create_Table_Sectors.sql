CREATE TABLE Sectors (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO Sectors (name) VALUES ('Textile');
INSERT INTO Sectors (name) VALUES ('Automotive');
INSERT INTO Sectors (name) VALUES ('Petrochemical');
INSERT INTO Sectors (name) VALUES ('Pharmaceutical');
INSERT INTO Sectors (name) VALUES ('Food');
INSERT INTO Sectors (name) VALUES ('Basic');
INSERT INTO Sectors (name) VALUES ('Steel');
INSERT INTO Sectors (name) VALUES ('Metallurgical');
INSERT INTO Sectors (name) VALUES ('Agricultural');
INSERT INTO Sectors (name) VALUES ('Naval');
INSERT INTO Sectors (name) VALUES ('Electroelectronic');