--
-- Auto-generated by Maven, based on values from src/main/resources/test/spring/test-data.properties
--

-- Used below as category's owner
INSERT INTO users(id, login, role, name, registered_at, activated_at, hash, salt, email) VALUES
	(5, 'test2', 'USER', '@valid_category_name_en@ Category Owner', NOW(), NOW(), '@valid_user_password_hash@', '@valid_user_password_salt@', 'test2@example.org');

-- Used only in WhenUserAddSeries and WhenAdminAddSeries
INSERT INTO categories(name, name_ru, created_at, created_by, updated_at, updated_by) VALUES
	('@valid_category_name_en@', '@valid_category_name_ru@', NOW(), 5, NOW(), 5);
