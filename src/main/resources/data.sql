/* =========================
   CATEGORIES
========================= */
INSERT INTO categories (category_name, description) VALUES
    ('Electronics', 'Electronic products'),
    ('Clothing', 'Clothing and accessories'),
    ('Home', 'Home products'),
    ('Sports', 'Sports equipment'),
    ('Books', 'Books and reading material');


/* =========================
   PRODUCTS
========================= */
INSERT INTO products (
    product_name, price, stock, description, image, id_category
) VALUES
      ('Bluetooth Headphones', 29990.00, 50, 'Wireless headphones with microphone', NULL, 1),
      ('Basic T-Shirt', 12990.00, 120, 'Unisex cotton t-shirt', NULL, 2),
      ('LED Lamp', 19990.00, 35, 'LED desk lamp', NULL, 3),
      ('Soccer Ball', 15990.00, 40, 'Official size soccer ball', NULL, 4),
      ('Java Basics Book', 24990.00, 25, 'Introduction to Java and OOP', NULL, 5);


/* =========================
   CUSTOMERS
========================= */
INSERT INTO customers (
    customer_name, email, phone, password
) VALUES
      ('Juan Perez', 'juan@mail.com', '912345678', 'clave123'),
      ('Maria Lopez', 'maria@mail.com', '987654321', 'clave456'),
      ('Carlos Soto', 'carlos@mail.com', NULL, 'clave789'),
      ('Ana Torres', 'ana@mail.com', '934567890', 'clave321'),
      ('Pedro Ramirez', 'pedro@mail.com', '956789012', 'clave654');


/* =========================
   ADDRESSES
========================= */
DELETE FROM addresses;

INSERT INTO addresses (
    address, city, region, zip_code, id_customer
) VALUES
      ('Av. Siempre Viva 123', 'Santiago', 'Metropolitana', 8320000, 1),
      ('Calle Falsa 456', 'Valparaiso', 'Valparaiso', 2340000, 2),
      ('Pasaje Norte 789', 'Concepcion', 'Biobio', 4030000, 3),
      ('Los Robles 321', 'La Serena', 'Coquimbo', 1700000, 4),
      ('Av. Central 654', 'Temuco', 'Araucania', 4780000, 5);


/* =========================
   ORDERS
========================= */
INSERT INTO orders (
    quantity, order_date, total, id_product, id_customer
) VALUES
      (2, CURRENT_DATE, 59980.00, 1, 1),
      (1, CURRENT_DATE, 12990.00, 2, 2),
      (1, CURRENT_DATE, 19990.00, 3, 3),
      (3, CURRENT_DATE, 47970.00, 4, 4),
      (1, CURRENT_DATE, 24990.00, 5, 5);


/* =========================
   COLLECTIONS
========================= */
INSERT INTO collections (
    collection_name, description
) VALUES
      ('Offers', 'Discounted products'),
      ('New Arrivals', 'Recently added products'),
      ('Best Sellers', 'Most popular products'),
      ('Recommended', 'Recommended selection'),
      ('Seasonal', 'Seasonal products');


/* =========================
   PRODUCT ↔ COLLECTION
========================= */
DELETE FROM product_collections;

INSERT INTO product_collections (
    id_product, id_collection
) VALUES
      (1, 1),
      (1, 3),
      (2, 2),
      (3, 4),
      (4, 5);

