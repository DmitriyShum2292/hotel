DELETE FROM external_api_credentials WHERE id=1;
DELETE FROM external_api_credentials WHERE id=2;
INSERT INTO external_api_credentials (id,key_id,value)
VALUES (1,'HOTELS','hotelKey'),(2,'CITY_MANAGEMENT','cityManagementKey');


