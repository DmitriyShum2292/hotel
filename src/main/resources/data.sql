CREATE TABLE IF NOT EXISTS external_api_credentials (
    id INT,
    key_id VARCHAR(255),
    value VARCHAR(255),
    primary key (id)
);
DELETE FROM external_api_credentials WHERE id=1;
DELETE FROM external_api_credentials WHERE id=2;
INSERT INTO external_api_credentials (id,key_id,value)
VALUES (1,'HOTELS','hotelKey'),(2,'CITY_MANAGEMENT','cityManagementKey');


