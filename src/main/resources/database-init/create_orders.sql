CREATE TABLE orders
(   id bigint PRIMARY KEY,
    user_id bigint NOT NULL,
    amount decimal(15,2) NOT NULL,
    purchase_date date NOT NULL
 );

 INSERT INTO orders (id, user_id, amount, purchase_date)
 values
     (1, 1, 15.0, now()),
     (2, 1, 22.5, now()),
     (3, 2, 22.5, now()),
     (4, 2, 22.5, now()),
     (5, 2, 22.5, now()),
     (6, 3, 50.5, now()),
     (7, 4, 22.5, now());