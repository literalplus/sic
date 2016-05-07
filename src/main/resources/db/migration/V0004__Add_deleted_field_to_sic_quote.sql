-- Add deleted field to sic_quote so that we are protected from vandalism and accidental deletes
ALTER TABLE sic.sic_quote ADD deleted BOOLEAN DEFAULT false NOT NULL;
