-- schema.sql
-- テーブル定義: users, groups, user_groups, user_roles, group_roles

CREATE TABLE IF NOT EXISTS users (
  user_id VARCHAR(50) PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL,
  gender CHAR(1) NOT NULL,
  email VARCHAR(255),
  phone_number VARCHAR(32),
  postal_cd VARCHAR(16),
  prefecture VARCHAR(64),
  address VARCHAR(255),
  room VARCHAR(255),
  birth_date DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(50),
  updated_by VARCHAR(50),
  version INTEGER DEFAULT 0,
  CONSTRAINT chk_gender CHECK (gender IN ('M','F','O'))
);

CREATE TABLE IF NOT EXISTS groups (
  group_id VARCHAR(50) PRIMARY KEY,
  group_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_groups (
  user_id VARCHAR(50) NOT NULL,
  group_id VARCHAR(50) NOT NULL,
  PRIMARY KEY (user_id, group_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_roles (
  user_id VARCHAR(50) NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (user_id, role_name),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS group_roles (
  group_id VARCHAR(50) NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (group_id, role_name),
  FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE
);

-- Optional: add UNIQUE(email) if desired
-- ALTER TABLE users ADD CONSTRAINT uq_users_email UNIQUE (email);

