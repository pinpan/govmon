CREATE DATABASE govmon;

CREATE USER 'moni'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'moni'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON govmon.* TO 'moni'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON govmon.* TO 'moni'@'%' WITH GRANT OPTION;

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

CREATE TABLE organization_type \
(                              \
id             INT AUTO_INCREMENT, \
type           varchar(255), \
area           varchar(255), \
PRIMARY KEY (id)             \
) ENGINE = InnoDB;
insert into organization_type (type, area) values ("4", "Obec"); 
insert into organization_type (type, area) values ("1", "Urad");

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


CREATE TABLE financial_report \
(                         \
id             INT AUTO_INCREMENT, \
period           long,             \
organization_id  long,             \
PRIMARY KEY (id)                   \
) ENGINE = InnoDB;
