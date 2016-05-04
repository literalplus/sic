-- Initial database schema for sic
-- Note that this assumes that the database has already been created and selected.
-- For a MySQL/MariaDB server.

-- sic_person table
-- Note: There's no difference in space usage between using VARCHAR(4) and VARCHAR(255)
CREATE TABLE IF NOT EXISTS sic_person
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  creation_date DATETIME NOT NULL,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

-- This adds user and authority tables for use with Spring Security's default JDBC thing

CREATE TABLE sic_user
(
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(60) NOT NULL,
  enabled BOOLEAN DEFAULT 1 NOT NULL
);

CREATE TABLE sic_authority
(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- so that we can use this with Spring's repository API
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT sic_authority_sic_user_username_fk FOREIGN KEY (username) REFERENCES sic_user (username) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX sic_authority_username_authority_uindex ON sic_authority (username, authority);


-- sic_quote table (stores quotes by person)
CREATE TABLE sic_quote
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  person_id INT NOT NULL,
  text TEXT NOT NULL,
  creation_date DATETIME NOT NULL,
  last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  creator_name VARCHAR(255) DEFAULT NULL NULL,
  CONSTRAINT sic_quote_sic_person_id_fk FOREIGN KEY (person_id) REFERENCES sic_person (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT sic_quote_sic_user_creator_name_fk FOREIGN KEY (creator_name) REFERENCES sic_user (username) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE INDEX sic_quote_person_id_index ON sic_quote (person_id);
ALTER TABLE sic_quote COMMENT = 'Stores sic quotes by person';
