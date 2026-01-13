/*Categorias*/
INSERT INTO categorias (nombre, descripcion) VALUES
                                                 ('Electrónica', 'Productos electrónicos'),
                                                 ('Ropa', 'Vestimenta y accesorios'),
                                                 ('Hogar', 'Artículos para el hogar'),
                                                 ('Deportes', 'Artículos deportivos'),
                                                 ('Libros', 'Libros y material de lectura');

/*Productos*/
INSERT INTO productos (
    nombre, precio, stock, descripcion, imagen, categorias_id_categoria
) VALUES
      ('Audífonos Bluetooth', 29990.00, 50, 'Audífonos inalámbricos con micrófono', NULL, 1),
      ('Polera Básica', 12990.00, 120, 'Polera de algodón unisex', NULL, 2),
      ('Lámpara LED', 19990.00, 35, 'Lámpara de escritorio LED', NULL, 3),
      ('Balón de Fútbol', 15990.00, 40, 'Balón tamaño oficial', NULL, 4),
      ('Libro Java Básico', 24990.00, 25, 'Introducción a Java y POO', NULL, 5);



/*Usuarios*/
INSERT INTO usuarios (nombre, correo, telefono, clave) VALUES
                                                           ('Juan Pérez', 'juan@mail.com', '912345678', 'clave123'),
                                                           ('María López', 'maria@mail.com', '987654321', 'clave456'),
                                                           ('Carlos Soto', 'carlos@mail.com', NULL, 'clave789'),
                                                           ('Ana Torres', 'ana@mail.com', '934567890', 'clave321'),
                                                           ('Pedro Ramírez', 'pedro@mail.com', '956789012', 'clave654');


/*DIRECCIONES*/
DELETE FROM direcciones;
INSERT INTO direcciones (
    direccion, ciudad, region, codigo_postal, usuarios_id_usuario
) VALUES
      ('Av. Siempre Viva 123', 'Santiago', 'Región Metropolitana', 8320000, 1),
      ('Calle Falsa 456', 'Valparaíso', 'Valparaíso', 2340000, 2),
      ('Pasaje Norte 789', 'Concepción', 'Biobío', 4030000, 3),
      ('Los Robles 321', 'La Serena', 'Coquimbo', 1700000, 4),
      ('Av. Central 654', 'Temuco', 'Araucanía', 4780000, 5);

/*Pedidos*/
INSERT INTO pedidos (
    cantidad, fecha, total, productos_id_producto, usuarios_id_usuario
) VALUES
      (2, CURRENT_DATE, 59980.00, 1, 1),
      (1, CURRENT_DATE, 12990.00, 2, 2),
      (1, CURRENT_DATE, 19990.00, 3, 3),
      (3, CURRENT_DATE, 47970.00, 4, 4),
      (1, CURRENT_DATE, 24990.00, 5, 5);

/*Colecciones*/
INSERT INTO colecciones (nombre, descripcion) VALUES
                                                  ('Ofertas', 'Productos en descuento'),
                                                  ('Novedades', 'Productos recién llegados'),
                                                  ('Más vendidos', 'Productos más populares'),
                                                  ('Recomendados', 'Selección recomendada'),
                                                  ('Temporada', 'Productos de temporada');

/*Producto ↔ Colección*/
DELETE FROM producto_coleccion;
INSERT INTO producto_coleccion (
    productos_id_producto, colecciones_id_coleccion
) VALUES
      (1, 1),
      (1, 3),
      (2, 2),
      (3, 4),
      (4, 5);
