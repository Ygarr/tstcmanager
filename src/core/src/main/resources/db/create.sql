-- SEQ contact
CREATE SEQUENCE IF NOT EXISTS seq_contact;


-- TABLE tbl_contacts
CREATE TABLE IF NOT EXISTS tbl_contacts (
  id           integer              PRIMARY KEY
, name         varchar(64)          NOT NULL
, phone_number varchar(64)          NOT NULL
, CONSTRAINT tbl_contacts_uk_1 UNIQUE (name, phone_number)
);

COMMENT ON TABLE tbl_contacts                 IS 'Contact table.';
COMMENT ON COLUMN tbl_contacts.id             IS 'Contact ID.';
COMMENT ON COLUMN tbl_contacts.name           IS 'Contact name.';
COMMENT ON COLUMN tbl_contacts.phone_number   IS 'Contact phone number.';