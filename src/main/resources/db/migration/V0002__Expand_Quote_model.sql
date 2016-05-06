ALTER TABLE sic.sic_quote ADD vote_count INT DEFAULT 0 NOT NULL;
ALTER TABLE sic.sic_quote ADD sub_text VARCHAR(255) DEFAULT "" NOT NULL;
ALTER TABLE sic.sic_quote
  MODIFY COLUMN sub_text VARCHAR(255) NOT NULL DEFAULT "" AFTER text,
  MODIFY COLUMN vote_count INT NOT NULL DEFAULT 0 AFTER sub_text;
