-- -----------------------------------------------------
-- Schema tlg
-- -----------------------------------------------------
-- Database for transport and logistics company web applcaion.

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `tlg`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`city` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `longitude` FLOAT(10,6) NOT NULL,
  `latitude` FLOAT(10,6) NOT NULL,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `avatar` VARCHAR(255) ,
  `user_role` VARCHAR(45) NOT NULL,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `avatar_UNIQUE` (`avatar` ASC) VISIBLE,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`driver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`driver` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED ,
  `personal_num` VARCHAR(16) UNIQUE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `city_id` INT UNSIGNED NOT NULL,
  `vehicle_id` INT UNSIGNED ,
  `order_id` INT UNSIGNED ,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `personal_num_UNIQUE` (`personal_num` ASC) VISIBLE,
  PRIMARY KEY (`id`)
--   INDEX `fk_driver_city_idx` (`city_id` ASC) VISIBLE,
--   INDEX `fk_driver_user_idx` (`user_id` ASC) VISIBLE,
--   INDEX `fk_driver_vehicle_idx` (`vehicle_id` ASC) VISIBLE,
--   CONSTRAINT `cur_city_id_driver`
--     FOREIGN KEY (`city_id`)
--     REFERENCES `tlg`.`city` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_driver_user`
--     FOREIGN KEY (`user_id`)
--     REFERENCES `tlg`.`user` (`id`)
--     ON DELETE SET NULL
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_driver_vehicle`
--     FOREIGN KEY (`vehicle_id`)
--     REFERENCES `tlg`.`vehicle` (`id`)
--     ON DELETE SET NULL
--     ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`vehicle` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `lic_plate_num` VARCHAR(7) NOT NULL,
  `load_capacity` DECIMAL(3,1) UNSIGNED NOT NULL,
  `pass_seats_num` TINYINT(5) UNSIGNED NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `city_id` INT UNSIGNED NOT NULL,
  `driver_id` INT UNSIGNED ,
  `order_id` INT UNSIGNED ,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE INDEX `lic_plate_num_UNIQUE` (`lic_plate_num` ASC ) VISIBLE,
PRIMARY KEY (`id`)
--   INDEX `fk_vehicle_city_idx` (`city_id` ASC) VISIBLE,
--   INDEX `fk_vehicle_driver_idx` (`driver_id` ASC) VISIBLE,
--   CONSTRAINT `fk_vehicle_city`
--     FOREIGN KEY (`city_id`)
--     REFERENCES `tlg`.`city` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_vehicle_driver`
--     FOREIGN KEY (`driver_id`)
--     REFERENCES `tlg`.`driver` (`id`)
--     ON DELETE SET NULL
--     ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`carriage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`carriage` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `unique_number` VARCHAR (16) UNIQUE NOT NULL,
  `carriage_status` VARCHAR(45) NOT NULL,
  `customer_name` VARCHAR(255) NOT NULL,
  `initiate_date` DATETIME NOT NULL,
  `finish_date` DATETIME ,
  `estimated_lead_time_hours` INT UNSIGNED,
  `vehicle_id` INT UNSIGNED,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   UNIQUE INDEX `order_unique_number_UNIQUE` (`unique_number` ASC) VISIBLE,
--    UNIQUE INDEX `vehicle_id_UNIQUE` (`vehicle_id` ASC) VISIBLE,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`cargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`cargo` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` INT UNSIGNED NOT NULL,
  `number` VARCHAR(16) UNIQUE NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) ,
  `weight` INT UNSIGNED NOT NULL COMMENT 'kg',
  `status` VARCHAR(45) NOT NULL,
  `departure_waypoint_id` INT UNSIGNED NOT NULL,
  `destination_waypoint_id` INT UNSIGNED NOT NULL,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--   INDEX `fk_cargo_order_idx` (`order_id` ASC) VISIBLE,
  UNIQUE INDEX `cargo_number_UNIQUE` (`number` ASC) VISIBLE,
  PRIMARY KEY (`id`)
--   CONSTRAINT `fk_cargo_order`
--     FOREIGN KEY (`order_id`)
--     REFERENCES `tlg`.`carriage` (`id`)
--     ON DELETE CASCADE
--     ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`route_point`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`waypoint` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` INT UNSIGNED NOT NULL,
  `city_id` INT UNSIGNED NOT NULL,
  `posInRoute` INT UNSIGNED NOT NULL,
  `is_visited` TINYINT(1) UNSIGNED NOT NULL,
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--   INDEX `fk_route_point_order_idx` (`order_id` ASC) VISIBLE,
--   INDEX `fk_route_point_city_idx` (`city_id` ASC) VISIBLE,
  PRIMARY KEY (`id`)
--   CONSTRAINT `order_id_route`
--     FOREIGN KEY (`order_id`)
--     REFERENCES `tlg`.`carriage` (`id`)
--     ON DELETE CASCADE
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_route_point_city`
--     FOREIGN KEY (`city_id`)
--     REFERENCES `tlg`.`city` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tlg`.`shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tlg`.`shift` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `driver_id` INT UNSIGNED NOT NULL,
  `shift_start` DATETIME NOT NULL,
  `shift_end` DATETIME ,
--   INDEX `fk_shift_driver_idx` (`driver_id` ASC) VISIBLE,
  PRIMARY KEY (`id`)
--   CONSTRAINT `fk_shift_driver`
--     FOREIGN KEY (`driver_id`)
--     REFERENCES `tlg`.`driver` (`id`)
--     ON DELETE CASCADE
--     ON UPDATE NO ACTION
) ENGINE = InnoDB;

