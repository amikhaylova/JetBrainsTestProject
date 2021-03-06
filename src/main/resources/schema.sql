CREATE TABLE IF NOT EXISTS recipient
(
    address VARCHAR PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS templates
(
    template_id VARCHAR PRIMARY KEY,
    template    VARCHAR
);
CREATE TABLE IF NOT EXISTS template_recipients
(
    id          IDENTITY PRIMARY KEY,
    address     VARCHAR REFERENCES recipient,
    template_id VARCHAR REFERENCES templates
);
CREATE TABLE IF NOT EXISTS repeated_message
(
    id          IDENTITY PRIMARY KEY,
    text     VARCHAR,
    template_id VARCHAR REFERENCES templates
);
CREATE TABLE IF NOT EXISTS variables
(
    id          IDENTITY PRIMARY KEY,
    type     VARCHAR,
    name     VARCHAR,
    template_id VARCHAR REFERENCES templates
);
