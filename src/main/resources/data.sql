CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    description varchar(50) NOT NULL,
    price DECIMAL NOT NULL,
    quantity_in_stock INTEGER NOT NULL,
    wholesale_product BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS discount_card (
    id SERIAL PRIMARY KEY,
    number INTEGER NOT NULL UNIQUE,
    amount SMALLINT CHECK (amount >= 0 AND amount <= 100)
);

SELECT * FROM discount_card WHERE id=? LIMIT 1;
SELECT * FROM discount_card;
INSERT INTO discount_card (number, amount) VALUES (?, ?);
UPDATE discount_card SET amount=? WHERE id=?;
DELETE FROM discount_card WHERE id=?;

SELECT * FROM product WHERE id=? LIMIT 1;
SELECT * FROM product;
INSERT INTO product (description, price, quantity_in_stock, wholesale_product) VALUES (?, ?, ?, ?);
UPDATE product SET description=?, price=?, quantity_in_stock=?, wholesale_product=? WHERE id=?;
DELETE FROM product WHERE id=?;