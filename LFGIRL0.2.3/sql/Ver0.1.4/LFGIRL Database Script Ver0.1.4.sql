-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS mydb;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`groups` ;

CREATE TABLE IF NOT EXISTS `mydb`.`groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `groupname` VARCHAR(100) NOT NULL,
  `description` VARCHAR(45) NULL,
  `address` varchar(100) NULL,
  `latitude` float(10,6) NULL,
  `longitude` float(10,6) NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE INDEX `group_id_UNIQUE` (`group_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users_groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users_groups` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users_groups` (
  `user_id` INT NOT NULL,
  `group_id` INT NOT NULL,
  `is_leader` TINYINT(1) NULL,
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `group_id_idx` (`group_id` ASC),
  PRIMARY KEY (`user_id`, `group_id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `group_id`
    FOREIGN KEY (`group_id`)
    REFERENCES `mydb`.`groups` (`group_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user_info` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user_info` (
  `user_info_id` INT NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `postal_code` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  PRIMARY KEY (`user_info_id`),
  CONSTRAINT `user_info_id`
    FOREIGN KEY (`user_info_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users_roles` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users_roles` (
  `user_role_id` INT NOT NULL,
  `role` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`user_role_id`, `role`),
  CONSTRAINT `user_role_id`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`conversation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`conversation` ;

CREATE TABLE IF NOT EXISTS `mydb`.`conversation` (
  `conversation_id` INT NOT NULL AUTO_INCREMENT,
  `user_start_id` INT NOT NULL,
  `user_recieve_id` INT NOT NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`conversation_id`),
  CONSTRAINT `user_start_id_fk`
    FOREIGN KEY (`user_start_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_recieve_id_fk`
    FOREIGN KEY (`user_recieve_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`conversation_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`conversation_message` ;

CREATE TABLE IF NOT EXISTS `mydb`.`conversation_message` (
  `conversation_message_id` INT NOT NULL AUTO_INCREMENT,
  `message` LONGTEXT NULL,
  `time_stamp` DATETIME NULL,
  `user_id_fk` INT NOT NULL,
  `conversation_id_fk` INT NOT NULL,
  PRIMARY KEY (`conversation_message_id`),
  CONSTRAINT `user_convo_message_id_fk`
    FOREIGN KEY (`user_id_fk`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `convo_id_fk`
    FOREIGN KEY (`conversation_id_fk`)
    REFERENCES `mydb`.`conversation` (`conversation_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`group_locations`
-- -----------------------------------------------------
drop table if exists `mydb`.`group_locations`;


-- -----------------------------------------------------
-- Table `mydb`.`group_messages`
-- -----------------------------------------------------
drop table if exists `mydb`.`group_messages`;

create table if not exists `mydb`.`group_messages` (
	`message_id` INT NOT NULL AUTO_INCREMENT,
	`group_id_fk` INT NOT NULL,
	`sender_user_id` INT NOT NULL,
    `receiver_user_id` INT NOT NULL,
    `message_title` varchar(100) NULL,
    `message_content`  varchar(5000) NULL,
	`message_read_status`  BIT(1) NULL,
	`message_date` varchar(20) NULL,
	PRIMARY KEY (`message_id`),
   CONSTRAINT `group_id_fk_con`
    FOREIGN KEY (`group_id_fk`)
    REFERENCES `mydb`.`groups` (`group_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
engine=InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`secret_keys`
-- -----------------------------------------------------
drop table if exists `mydb`.`secret_keys`;
create table if not exists `mydb`.`secret_keys` (
	`key_name` varchar(30) not null unique,
    `key_value` varchar(100) not null
)
engine=InnoDB;


USE `mydb`;

DELIMITER $$

USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`users_AFTER_INSERT` $$
USE `mydb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`users_AFTER_INSERT` AFTER INSERT ON `users` FOR EACH ROW
BEGIN

INSERT INTO user_info
(user_info_id) VALUES (NEW.user_id);

END$$


USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`users_AFTER_DELETE` $$
USE `mydb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`users_AFTER_DELETE` AFTER DELETE ON `users` FOR EACH ROW
BEGIN

DELETE FROM users_groups
	WHERE users_groups.user_id = OLD.user_id;
    
DELETE FROM user_info 
   WHERE user_info.user_info_id = OLD.user_id;
   -- remove any message that sent to a user, if that user is deleted
DELETE FROM group_messages
   WHERE group_messages.receiver_user_id = OLD.user_id;

END$$


USE `mydb`$$
DROP TRIGGER IF EXISTS `mydb`.`groups_AFTER_DELETE` $$
USE `mydb`$$
CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`groups_AFTER_DELETE` AFTER DELETE ON `groups` FOR EACH ROW
BEGIN

DELETE FROM users_groups
	WHERE users_groups.group_id = OLD.group_id;
    -- Delete all messages belongs to one group if group is deleted
DELETE FROM group_messages 
    WHERE group_messages.group_id_fk = OLD.group_id;

END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
