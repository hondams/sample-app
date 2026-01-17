-- data.sql
-- サンプルデータ: 2 users, 2 groups, roles and associations

-- users
INSERT INTO users (user_id, user_name, gender, email, phone_number, postal_cd, prefecture, address, room, birth_date, created_at, updated_at, created_by, updated_by, version)
VALUES
('u1', '山田 太郎', 'MALE', 'tarou.yamada@example.com', '090-1111-2222', '123-4567', '東京都', '新宿区1-1-1', '101', '1980-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system', 0),
('u2', '鈴木 花子', 'FEMALE', 'hanako.suzuki@example.com', '080-3333-4444', '987-6543', '大阪府', '大阪市2-2-2', '202', '1990-02-02', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'system', 'system', 0);

-- groups
INSERT INTO groups (group_id, group_name) VALUES
('g_admin', 'Administrators'),
('g_users', 'Users');

-- user_groups
INSERT INTO user_groups (user_id, group_id) VALUES
('u1', 'g_admin'),
('u1', 'g_users'),
('u2', 'g_users');

-- user_roles
INSERT INTO user_roles (user_id, role_name) VALUES
('u1', 'ROLE_ADMIN'),
('u1', 'ROLE_USER'),
('u2', 'ROLE_USER');

-- group_roles
INSERT INTO group_roles (group_id, role_name) VALUES
('g_admin', 'ROLE_ADMIN'),
('g_users', 'ROLE_USER');

