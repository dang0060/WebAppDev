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
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user_info` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user_info` (
  `user_info_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `postal_code` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  PRIMARY KEY (`user_info_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`admin` ;

CREATE TABLE IF NOT EXISTS `mydb`.`admin` (
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  `is_active` TINYINT(1) NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE INDEX `admin_id_UNIQUE` (`admin_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NULL,
  `user_info_id` INT NULL,
  `admin_id` INT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `user_info_id_idx` (`user_info_id` ASC),
  INDEX `admin_id_idx` (`admin_id` ASC),
  CONSTRAINT `user_info_id`
    FOREIGN KEY (`user_info_id`)
    REFERENCES `mydb`.`user_info` (`user_info_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `admin_id`
    FOREIGN KEY (`admin_id`)
    REFERENCES `mydb`.`admin` (`admin_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`groups` ;

CREATE TABLE IF NOT EXISTS `mydb`.`groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE INDEX `group_id_UNIQUE` (`group_id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users_groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users_groups` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users_groups` (
  `users_groups_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `group_id` INT NULL,
  `is_leader` TINYINT(1) NULL,
  INDEX `user_id_idx` (`user_id` ASC),
  INDEX `group_id_idx` (`group_id` ASC),
  PRIMARY KEY (`users_groups_id`),
  UNIQUE INDEX `users_groups_id_UNIQUE` (`users_groups_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `group_id`
    FOREIGN KEY (`group_id`)
    REFERENCES `mydb`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
