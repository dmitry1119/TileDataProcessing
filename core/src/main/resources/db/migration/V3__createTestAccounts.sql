INSERT INTO WALLET VALUES();
INSERT INTO ACCOUNT (PASS_PHRASE, WALLET_ID) VALUES('$2a$10$2jnKQKZtrdcrabVY6nHp9OiDgzzx31IhqUkGMpMJu0LwwcdHz6UuC',1);
INSERT INTO CONTACT_DETAILS(FIRST_NAME) VALUES('TESTNAME') ;
INSERT INTO INDIVIDUAL_ACCOUNT(ID,CONTACT_DETAILS_ID) VALUES(1,1);
INSERT INTO ADDRESS(ADDRESS,PRIVATE_KEY,WALLET_ID) VALUES('nmfeFE1523asdfawegdfasdf','privateKey',1);

INSERT INTO WALLET VALUES();
INSERT INTO ACCOUNT (PASS_PHRASE, WALLET_ID) VALUES ('$2a$10$vFAlilLPazG0iIpHLJPbReKRZ/jivkVJVdiEzcI9sAv93DYRCoyGS',2);
INSERT INTO COMPANY_CONTACT_DETAILS(FIRST_NAME) VALUES('TESTNAME') ;
INSERT INTO COMPANY_ACCOUNT(ID,COMPANY_CONTACT_DETAILS_ID) VALUES(2,1);
INSERT INTO ADDRESS(ADDRESS,PRIVATE_KEY,WALLET_ID) VALUES('grtesFE1523asdfawegdfasdf','privateKey',2);