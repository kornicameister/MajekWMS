use majekwms;

DELIMITER $$
DROP PROCEDURE IF EXISTS `majekwms`.`copyCities` $$
CREATE PROCEDURE `majekwms`.`copyCities` ()
BEGIN
	declare done int default 0;
	declare cityName varchar(20);

	declare cityCursor cursor for select city_name FROM cities;
	declare continue handler for sqlstate '02000' set done=1;

	open cityCursor;
	repeat
		fetch cityCursor into cityName;
		insert into city(name,updatedOn,version) values(cityName,'2012-01-01',6);
	until done end repeat;
	close cityCursor;
END $$
DELIMITER ;
