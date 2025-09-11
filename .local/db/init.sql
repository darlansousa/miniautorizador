CREATE DATABASE IF NOT EXISTS miniautorizador;
USE miniautorizador;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_username (username)
);

CREATE TABLE IF NOT EXISTS cards (
  id BIGINT NOT NULL AUTO_INCREMENT,
  card_number VARCHAR(64) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_cards_card_number (card_number)
);

CREATE TABLE IF NOT EXISTS card_accounts (
  id BIGINT NOT NULL AUTO_INCREMENT,
  balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
  user_id BIGINT NOT NULL,
  card_id BIGINT NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_card_accounts_card_id (card_id),
  KEY idx_card_accounts_user_id (user_id),
  CONSTRAINT fk_card_accounts_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_card_accounts_card
    FOREIGN KEY (card_id) REFERENCES cards(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS authorities (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  role VARCHAR(64) NOT NULL,
  created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_authorities_user_role (user_id, role),
  KEY idx_authorities_user_id (user_id),
  CONSTRAINT fk_authorities_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);


INSERT INTO users (username, password)
SELECT 'admin','admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, password)
SELECT 'user', 'user'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

INSERT INTO authorities (user_id, role)
SELECT u.id, 'ROLE_ADMIN'
FROM users u
WHERE u.username = 'admin'
  AND NOT EXISTS (
    SELECT 1 FROM authorities a
    WHERE a.user_id = u.id AND a.role = 'ROLE_ADMIN'
  );

INSERT INTO authorities (user_id, role)
SELECT u.id, 'ROLE_USER'
FROM users u
WHERE u.username = 'admin'
  AND NOT EXISTS (
    SELECT 1 FROM authorities a
    WHERE a.user_id = u.id AND a.role = 'ROLE_USER'
  );

INSERT INTO authorities (user_id, role)
SELECT u.id, 'ROLE_USER'
FROM users u
WHERE u.username = 'user'
  AND NOT EXISTS (
    SELECT 1 FROM authorities a
    WHERE a.user_id = u.id AND a.role = 'ROLE_USER'
  );
