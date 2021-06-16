INSERT INTO users (
    SELECT * FROM (VALUES ('admin_YIE55O', '$2a$10$QqItJc0jcG.LaOcz3MpCOe/NjadeueciKdMaH9W/i4mvom0o9kGtW', true))
                                            -- password : mk5R4WTM6
    as tmp (username, password, enabled)
  WHERE NOT EXISTS (SELECT 1 FROM users m where m.username = tmp.username)
);

INSERT INTO authorities (
    SELECT * FROM (VALUES ('admin_YIE55O', 'ROLE_ADMIN'))
    as tmp (username, authority)
  WHERE NOT EXISTS (SELECT 1 FROM authorities m where m.username = tmp.username)
);