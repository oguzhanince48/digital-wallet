create table CURRENCY
(
    ID    BIGINT auto_increment
        primary key,
    IDATE TIMESTAMP            not null,
    UDATE TIMESTAMP,
    CODE  CHARACTER VARYING(5) not null
);

create table CUSTOMER
(
    ID              BIGINT auto_increment
        primary key,
    IDATE           TIMESTAMP             not null,
    UDATE           TIMESTAMP,
    STATUS          INTEGER DEFAULT 0 COMMENT '-1: deleted, 0:passive, 1:active' NOT NULL,
    NAME            CHARACTER VARYING(63) not null,
    SURNAME         CHARACTER VARYING(63) not null,
    IDENTITY_NUMBER CHARACTER VARYING(20) not null
        constraint UC_CUSTOMER_IDENTITY_NUMBER
            unique
);

CREATE TABLE USERS(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    IDATE TIMESTAMP NOT NULL,
    UDATE TIMESTAMP,
    USERNAME CHARACTER VARYING(100) NOT NULL,
    PASSWORD CHARACTER VARYING(255) NOT NULL,
    ROLE VARCHAR(15) NOT NULL,
    CUSTOMER_ID BIGINT NULL
);


CREATE MEMORY TABLE WALLET(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    IDATE TIMESTAMP NOT NULL,
    UDATE TIMESTAMP,
    STATUS INTEGER DEFAULT 0 COMMENT '-1: deleted, 0:passive, 1:active' NOT NULL,
    WALLET_NAME CHARACTER VARYING(100) NOT NULL,
    CUSTOMER_ID BIGINT NOT NULL,
    CURRENCY_ID BIGINT NOT NULL,
    IS_ACTIVE_FOR_SHOPPING BOOLEAN DEFAULT TRUE NOT NULL,
    IS_ACTIVE_FOR_WITHDRAW BOOLEAN DEFAULT TRUE NOT NULL,
    BALANCE DECIMAL(30, 8) NOT NULL,
    USABLE_BALANCE DECIMAL(30, 8) NOT NULL
);

create table if not exists WALLET_TX
(
    ID                 BIGINT auto_increment primary key,
    IDATE              TIMESTAMP             not null,
    UDATE              TIMESTAMP,
    STATUS             INTEGER DEFAULT 0 COMMENT '-1: deleted, 0:passive, 1:active' NOT NULL,
    AMOUNT             DECIMAL(30, 8)       not null,
    TRANSACTION_TYPE   INTEGER              not null,
    OPPOSITE_PARTY_TYPE INTEGER              not null,
    OPPOSITE_PARTY_STATUS  INTEGER               not null,
    WALLET_ID         BIGINT               not null
);


