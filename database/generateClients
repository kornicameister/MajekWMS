use majekwms;

DELIMITER $$
DROP PROCEDURE IF EXISTS `majekwms`.`generateClients` $$
CREATE PROCEDURE `majekwms`.`generateClients` ()
BEGIN
	declare it,typeIt,addressIt int;
	declare clientPreffix char(6);

    set it = 0;
	set typeIt = 1;
	set addressIt = 57736;
	set clientPreffix = 'Client';

	while it < 100 do

		INSERT INTO `majekwms`.`address`
		(`street`,`postcode`,`city_id`,`updatedOn`,`version`)
		VALUES
		(
			concat('Street number [',it,']'),
			concat('99-',it),
			addressIt,
			'2012-02-02',
			it
		);

		INSERT INTO `majekwms`.`client` 
		(`address_id`,`type_id`,`name`,`company`,`description`,`updatedOn`,`version`)
		VALUES
		(
			(SELECT LAST_INSERT_ID()),
			typeIt,
			concat(clientPreffix,'--',it),
			concat('Company','--',it),
			concat('Test client numero ',it),
			'2012-01-01',
			it
		);

		INSERT INTO `majekwms`.`clientDetails`
		(`client_id`,
		`phone`,
		`fax`,
		`account`,
		`nip`,
		`updatedOn`,
		`version`)
		VALUES
		(
		(SELECT LAST_INSERT_ID()),
			concat_ws('-',666,it,typeIt),
			concat_ws('-',666*2,it,typeIt),
			concat_ws('-',666*3,it,typeIt),
			concat_ws('-',666*4,it,typeIt),
			'2012-01-01',
			it
		);

		set typeIt = typeIt + 1;
		set addressIt = addressIt + 1;

		if typeIt = 3 then
			set typeIt = 1;
		end if;
		if addressIt = 57741 then
			set addressIt = 57736;
		end if;
		set it = it+1;
	end while;
END $$
DELIMITER ;
