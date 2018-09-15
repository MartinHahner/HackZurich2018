-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CFDB_F1198208_9FFA_4A00_954B_3D101432D3CD
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `meal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meal` ;

CREATE TABLE IF NOT EXISTS `meal` (
  `idmeal` INT NOT NULL AUTO_INCREMENT,
  `when` DATETIME NULL,
  `location_lat` DECIMAL(10) NULL,
  `location_lng` DECIMAL(10) NULL,
  `title` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `zip` VARCHAR(45) NULL,
  `max_people` INT NULL,
  `co2_score` VARCHAR(45) NULL,
  `ingredients` VARCHAR(500) NULL,
  `cooked_by` INT NULL,
  PRIMARY KEY (`idmeal`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `participate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `participate` ;

CREATE TABLE IF NOT EXISTS `participate` (
  `idmeal` INT NOT NULL,
  `iduser` INT NOT NULL,
  `status` TINYINT NULL,
  PRIMARY KEY (`idmeal`, `iduser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `review`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `review` ;

CREATE TABLE IF NOT EXISTS `review` (
  `idreview` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(300) NULL,
  `stars` DECIMAL(10) NULL,
  `tip` DECIMAL(10) NULL,
  PRIMARY KEY (`idreview`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `user` (`iduser`, `firstname`, `lastname`) VALUES (1, 'Sandro', 'Panighetti');
INSERT INTO `user` (`iduser`, `firstname`, `lastname`) VALUES (2, 'John', 'Smith');
INSERT INTO `user` (`iduser`, `firstname`, `lastname`) VALUES (3, 'Paul', 'Muller');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meal`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `meal` (`idmeal`, `when`, `location_lat`, `location_lng`, `title`, `description`, `address`, `city`, `zip`, `max_people`, `co2_score`, `ingredients`, `cooked_by`) VALUES (1, '2018-09-15', 0, 0, 'Spaghetti carbonara', 'Just some very good spaghetti', 'Technopark 1', 'Zurich', '8400', 3, '10', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `participate`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `participate` (`idmeal`, `iduser`, `status`) VALUES (1, 1, 0);
INSERT INTO `participate` (`idmeal`, `iduser`, `status`) VALUES (1, 2, 1);

COMMIT;

