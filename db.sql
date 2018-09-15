-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`user` ;

CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`meal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`meal` ;

CREATE TABLE IF NOT EXISTS `mydb`.`meal` (
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
  PRIMARY KEY (`idmeal`),
  INDEX `cooked_by_FK_idx` (`cooked_by` ASC) VISIBLE,
  CONSTRAINT `cooked_by_FK`
    FOREIGN KEY (`cooked_by`)
    REFERENCES `mydb`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`participate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`participate` ;

CREATE TABLE IF NOT EXISTS `mydb`.`participate` (
  `idmeal` INT NOT NULL,
  `iduser` INT NOT NULL,
  PRIMARY KEY (`idmeal`, `iduser`),
  INDEX `fk_user_idx` (`iduser` ASC) VISIBLE,
  CONSTRAINT `fk_meal`
    FOREIGN KEY (`idmeal`)
    REFERENCES `mydb`.`meal` (`idmeal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user`
    FOREIGN KEY (`iduser`)
    REFERENCES `mydb`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `mydb`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`user` (`iduser`, `firstname`, `lastname`) VALUES (1, 'Sandro', 'Panighetti');
INSERT INTO `mydb`.`user` (`iduser`, `firstname`, `lastname`) VALUES (2, 'John', 'Smith');
INSERT INTO `mydb`.`user` (`iduser`, `firstname`, `lastname`) VALUES (3, 'Paul', 'Muller');

COMMIT;


-- -----------------------------------------------------
-- Data for table `mydb`.`meal`
-- -----------------------------------------------------
START TRANSACTION;
USE `mydb`;
INSERT INTO `mydb`.`meal` (`idmeal`, `when`, `location_lat`, `location_lng`, `title`, `description`, `address`, `city`, `zip`, `max_people`, `co2_score`, `ingredients`, `cooked_by`) VALUES (1, '2018.09.15', 0, 0, 'Spaghetti carbonara', 'Just some very good spaghetti', 'Technopark 1', 'Zurich', '8400', 3, '10', NULL, NULL);

COMMIT;

