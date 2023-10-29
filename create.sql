CREATE TABLE products (
	id int primary key,
    name varchar(255) not null,
    type varchar(255) not null,
    kilograms decimal (3,2) not null,
    description text not null,
    price decimal(10,2) not null,
    image varchar(255) not null,
    on_stock boolean not null,
    is_featured boolean not null
);

CREATE TABLE messages (
	id int primary key auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    message text not null
);

CREATE TABLE reviews (
	id int primary key auto_increment,
    name varchar(255) not null,
    stars int not null,
    message text default null,
    created_at datetime default current_timestamp,
    product_id int not null,
    
    foreign key(product_id) references products(id)
);

CREATE TABLE purchases (
	id int primary key auto_increment,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    address varchar(255) not null,
    city varchar(255) not null,
    message text default null,
    amount int not null,
    total_price decimal(10,2) not null,
    created_at datetime default current_timestamp,
    product_id int,
    
    foreign key(product_id) references products(id)
);