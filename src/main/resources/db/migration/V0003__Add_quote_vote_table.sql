-- Adds a table for votes on quotes for proper verification and tracking

CREATE TABLE sic_quote_vote
(
  user_name VARCHAR(255) NOT NULL,
  quote_id INT NOT NULL,
  is_up BOOLEAN NOT NULL,
  CONSTRAINT sic_quote_vote_user_name_quote_id_pk PRIMARY KEY (user_name, quote_id),
  CONSTRAINT sic_quote_vote_sic_user_username_fk FOREIGN KEY (user_name) REFERENCES sic_user (username) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT sic_quote_vote_sic_quote_id_fk FOREIGN KEY (quote_id) REFERENCES sic_quote (id) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON TABLE sic_quote_vote IS 'votes on quotes';
COMMENT ON COLUMN sic_quote_vote.is_up IS 'whether upvote (downvote otherwise)';
