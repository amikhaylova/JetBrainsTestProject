CREATE TABLE IF NOT EXISTS recipient (
    address VARCHAR PRIMARY KEY
);
CREATE TABLE IF NOT EXISTS templates(
    template_id VARCHAR PRIMARY KEY,
    template VARCHAR
);
CREATE TABLE IF NOT EXISTS template_recipients (
    id IDENTITY PRIMARY KEY,
    address VARCHAR REFERENCES recipient,
    template_id VARCHAR REFERENCES templates
);
