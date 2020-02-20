-- Table: account

-- DROP TABLE account;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS account
(
    id                  uuid        PRIMARY KEY DEFAULT uuid_generate_v4(),
    account_name        text        NOT NULL,
    status              integer     NOT NULL,
    balance             numeric     NOT NULL,
    active_from         timestamptz NOT NULL DEFAULT now(),
    active_to           timestamptz
) WITH (
      OIDS = false
    );
CREATE INDEX account_idx_status ON account USING btree (status);
CREATE INDEX account_idx_active_to ON account USING btree (active_to) WHERE (active_to IS NOT NULL);
