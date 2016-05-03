ALTER TABLE sic_quote ADD creator_name VARCHAR(50) DEFAULT NULL NULL;
ALTER TABLE sic_quote
ADD CONSTRAINT sic_quote_sic_user_username_fk
FOREIGN KEY (creator_name) REFERENCES sic_user (username) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE sic_quote
  MODIFY COLUMN creator_name VARCHAR(50) DEFAULT NULL AFTER text
