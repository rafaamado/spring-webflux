CREATE TABLE users (
	id serial primary key,
	name varchar(50),
	balance int
);

CREATE TABLE user_transaction(
	id serial primary key,
	user_id bigint,
	amount int,
	transaction_date timestamp,
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) references users(id) on delete cascade
);

insert into users (name, balance) values ('Rafael', 1000), ('Amado', 400);