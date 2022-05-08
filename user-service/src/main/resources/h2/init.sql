CREATE TABLE users (
	id bigint auto_increment,
	name varchar(50),
	balance int,
	PRIMARY KEY (id)
);

CREATE TABLE user_transaction(
	id bigint auto_increment,
	user_id bigint,
	amount int,
	transaction_date timestamp,
	FOREIGN KEY (user_id) references users(id) on delete cascade
);

insert into users (name, balance) values ('Rafael', 1000), ('Amado', 400);