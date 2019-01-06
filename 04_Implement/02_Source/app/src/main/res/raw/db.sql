CREATE TABLE IF NOT EXISTS category
(
	id integer primary key autoincrement,
	type int,
	name nvarchar(255),
	icon_url varchar(255)
);

CREATE TABLE IF NOT EXISTS Vi
(
	id integer primary key autoincrement,
	name nvarchar(255)
);

CREATE TABLE IF NOT EXISTS giaodich
(
	id integer primary key autoincrement,

	category_id smallint,
	thoi_gian varchar(10),
	note nvarchar(255),
	so_tien integer,

	from_id integer,
	to_id integer,

	foreign key (from_id) references Vi(id),
	foreign key (to_id) references Vi(id),
	foreign key (category_id) references category(id)
);

CREATE TABLE IF NOT EXISTS ngan_sach 
(
	id integer primary key autoincrement,
	name varchar,
	ngay_bd varchar(10),
	ngay_kt varchar(10),
	so_tien integer
);
