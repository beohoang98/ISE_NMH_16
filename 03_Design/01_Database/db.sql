CREATE TABLE IF NOT EXISTS `transaction` 
(
	`id` integer autoincrement,

	`category_id` smallint,
	`thoi_gian` date,
	`ghi_chu` nvarchar(255),
	`so_tien` integer,

	`from` smallint,
	`to` smallint,

	primary key (`id`)
);

CREATE TABLE IF NOT EXISTS `category` 
(
	`id` smallint primary key autoincrement,
	`type` boolean,
	`name` nvarchar(255),
	`icon_url` varchar(255)
);

CREATE TABLE IF NOT EXISTS `Vi` 
(
	`id` smallint primary key autoincrement,
	`name` nvarchar(255)
);

CREATE TABLE IF NOT EXISTS `ngan_sach` 
(
	`id` smallint primary key autoincrement,
	`name` st,
	`ngay_bd` date,
	`ngay_kt` date,
	`so_tien` integer
);

ALTER TABLE `transaction` ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

ALTER TABLE `transaction` ADD CONSTRAINT `trans_income` FOREIGN KEY (`to`) REFERENCES `Vi` (`id`);

ALTER TABLE `transaction` ADD CONSTRAINT `trans_outcome` FOREIGN KEY (`from`) REFERENCES `Vi` (`id`);
