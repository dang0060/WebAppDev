DELIMITER $$
CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`users_AFTER_DELETE` AFTER DELETE ON `users` FOR EACH ROW
BEGIN

DELETE FROM users_groups
	WHERE users_groups.user_id = OLD.user_id;
    
DELETE FROM user_info 
WHERE
    user_info.user_info_id = OLD.user_id;

END$$
DELIMITER ;