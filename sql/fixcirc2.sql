ALTER TABLE user_categs ADD COLUMN max_period INTEGER NULL;
UPDATE user_categs SET max_period = 500;