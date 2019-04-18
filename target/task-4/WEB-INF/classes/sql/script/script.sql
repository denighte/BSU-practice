CREATE TABLE IF NOT EXISTS users (
id INTEGER NOT NULL AUTO_INCREMENT,
login NVARCHAR(64) NOT NULL UNIQUE,
password_hash BINARY(512) NOT NULL,
 PRIMARY KEY (id));

