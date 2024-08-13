CREATE TABLE activities (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    sector_id INT REFERENCES sector(id)
);

INSERT INTO activities (name, sector_id) VALUES ('Weaving', 1);
INSERT INTO activities (name, sector_id) VALUES ('Dyeing', 1);
INSERT INTO activities (name, sector_id) VALUES ('Finishing', 1);

INSERT INTO activities (name, sector_id) VALUES ('Vehicle Assembly', 2);
INSERT INTO activities (name, sector_id) VALUES ('Parts Manufacturing', 2);
INSERT INTO activities (name, sector_id) VALUES ('Painting and Finishing', 2);

INSERT INTO activities (name, sector_id) VALUES ('Elastomer Production', 3);
INSERT INTO activities (name, sector_id) VALUES ('Plastic Production', 3);
INSERT INTO activities (name, sector_id) VALUES ('Chemical Production', 3);

INSERT INTO activities (name, sector_id) VALUES ('Drug Manufacturing', 4);
INSERT INTO activities (name, sector_id) VALUES ('Packaging', 4);
INSERT INTO activities (name, sector_id) VALUES ('Quality Control', 4);

INSERT INTO activities (name, sector_id) VALUES ('Food Processing', 5);
INSERT INTO activities (name, sector_id) VALUES ('Beverage Production', 5);
INSERT INTO activities (name, sector_id) VALUES ('Agriculture', 5);

INSERT INTO activities (name, sector_id) VALUES ('Basic Chemical Production', 6);
INSERT INTO activities (name, sector_id) VALUES ('Industrial Gas Production', 6);
INSERT INTO activities (name, sector_id) VALUES ('Fertilizer Manufacturing', 6);

INSERT INTO activities (name, sector_id) VALUES ('Steel Production', 7);
INSERT INTO activities (name, sector_id) VALUES ('Heat Treatment', 7);
INSERT INTO activities (name, sector_id) VALUES ('Steel Rolling', 7);

INSERT INTO activities (name, sector_id) VALUES ('Metal Mining', 8);
INSERT INTO activities (name, sector_id) VALUES ('Ore Processing', 8);
INSERT INTO activities (name, sector_id) VALUES ('Metal Casting', 8);

INSERT INTO activities (name, sector_id) VALUES ('Crop Cultivation', 9);
INSERT INTO activities (name, sector_id) VALUES ('Livestock Farming', 9);
INSERT INTO activities (name, sector_id) VALUES ('Dairy Production', 9);

INSERT INTO activities (name, sector_id) VALUES ('Shipbuilding', 10);
INSERT INTO activities (name, sector_id) VALUES ('Ship Repair', 10);
INSERT INTO activities (name, sector_id) VALUES ('Marine Engineering', 10);

INSERT INTO activities (name, sector_id) VALUES ('Circuit Assembly', 11);
INSERT INTO activities (name, sector_id) VALUES ('Device Manufacturing', 11);
INSERT INTO activities (name, sector_id) VALUES ('Quality Testing', 11);