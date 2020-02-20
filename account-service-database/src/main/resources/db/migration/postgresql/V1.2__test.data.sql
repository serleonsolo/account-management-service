-- Active
INSERT INTO account(account_name, status, balance, active_from)
VALUES ('First', 0, 10.0, '1970-01-01T00:00:00Z')
ON CONFLICT (id)
    DO NOTHING;

--Suspended
INSERT INTO account(account_name, status, balance, active_from)
VALUES ('Second', 1, 20.0, '1970-01-01T00:00:00Z')
ON CONFLICT (id)
    DO NOTHING;

--Disconnected
INSERT INTO account(account_name, status, balance, active_from, active_to)
VALUES ('Third', 2, 30.0, '1970-01-01T00:00:00Z', '2019-01-01T00:00:00Z')
ON CONFLICT (id)
    DO NOTHING;