--
-- Auto-generated by Maven, based on values from src/main/resources/test/spring/test-data.properties
--

INSERT INTO users(id, login, role, name, registered_at, activated_at, hash, salt, email) VALUES
	(1, '@valid_user_login@', 'USER', '@valid_user_name@', NOW(), NOW(), '@valid_user_password_hash@', '@valid_user_password_salt@', 'coder@rock.home');
