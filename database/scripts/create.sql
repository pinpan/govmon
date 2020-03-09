CREATE DATABASE govmon;

CREATE USER 'moni'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'moni'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON govmon.* TO 'moni'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON govmon.* TO 'moni'@'%' WITH GRANT OPTION;


DROP TABLE IF EXISTS organization_type;
CREATE TABLE organization_type \
(                              \
    id             INTEGER AUTO_INCREMENT, \
    type           varchar(255), \
    area           varchar(255), \
    PRIMARY KEY (id)             \
) ENGINE = InnoDB;
insert into organization_type (type, area) values ("4", "Obec"); 
insert into organization_type (type, area) values ("1", "Urad");


DROP TABLE IF EXISTS fiscal_period;
CREATE TABLE fiscal_period  \
(                         \
    id             INT AUTO_INCREMENT, \
    fiscal_year                  INT,            \
    load_id                      INTEGER UNIQUE,     \
    fiscal_month                 VARCHAR(25),        \
    is_quarter                    BOOLEAN,            \
    is_year                       BOOLEAN,            \
    balance_sheet_profit_loss    BOOLEAN,            \
    cash_flow_equity_capital     BOOLEAN,            \
    fin_m                         BOOLEAN,            \
    fin_u                         BOOLEAN,            \
    statements_after_corrections BOOLEAN,            \
    clearance_of_accounts        BOOLEAN,            \
    fin_spo                       BOOLEAN,            \
    monitoring                   BOOLEAN,            \
    budget_approved              BOOLEAN,            \
    budget_preparation           BOOLEAN,            \
    ucjed                        BOOLEAN,            \
    transaction_data             BOOLEAN,            \
    label                        VARCHAR(255),       \
    PRIMARY KEY (id)                   \
) ENGINE = InnoDB;


DROP TABLE IF EXISTS balance_statement;
CREATE TABLE balance_statement \
(                    \
    id             INT AUTO_INCREMENT, \
    statement_type         varchar(50), \
    statement_type_id      integer,      \
    name                   varchar(255), \
    dummy_integer          integer,      \
    dummy_string           varchar(255), \
    code                   varchar(255), \
    main_activity          varchar(255), \
    economic_activity      varchar(255), \
    main_activity_prev     varchar(255), \
    economic_activity_prev varchar(255), \
    syn_account            varchar(255), \
    line_number            varchar(255), \
    report_id              long,         \
    PRIMARY KEY (id)                     \
) ENGINE = InnoDB;


DROP TABLE IF EXISTS organization;
CREATE TABLE organization \
(                         \
    id             INT AUTO_INCREMENT, \
    name           varchar(255),       \
    ico            varchar(8) UNIQUE,  \
    short_name     varchar(50),        \
    nuts           varchar(255),       \
    county         varchar(255),       \
    address        varchar(255),       \
    sector         varchar(255),       \
    creation_date  varchar(255),       \
    org_type       long,               \
    PRIMARY KEY (id)                   \
) ENGINE = InnoDB;



DROP TABLE IF EXISTS financial_report;
CREATE TABLE financial_report \
(                         \
    id             INT AUTO_INCREMENT, \
    period           long,             \
    organization_id  long,             \
    PRIMARY KEY (id)                   \
) ENGINE = InnoDB;
