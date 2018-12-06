-- VI CHINH
INSERT OR REPLACE INTO `Vi` (`id`, `name`) VALUES (0, 'Vi chinh');

-- CATEGORY BAN DAU
INSERT OR REPLACE INTO `category` (`id`, `type`, `name`, `icon_URL`)

	--INCOME
	VALUES (1, 0, 'Salary', 'ic_salary'),
	VALUES (2, 0, 'Thu nap then', 'ic_cash'),
	
	--OUTCOME
	VALUES (3, 1, 'Food', 'ic_food'),
	VALUES (4, 1, 'Phuong tien', 'ic_car'),
	VALUES (5, 1, 'Ca phe', 'ic_coffee'),
	VALUES (6, 1, 'Home', 'ic_home');