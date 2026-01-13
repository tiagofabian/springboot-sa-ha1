CREATE TABLE IF NOT EXISTS usuarios (
                                        id_usuario SERIAL PRIMARY KEY,
                                        nombre   VARCHAR(50) NOT NULL,
    correo   VARCHAR(50) NOT NULL,
    telefono VARCHAR(12),
    clave    VARCHAR(16) NOT NULL
    );

CREATE TABLE IF NOT EXISTS direcciones (
                                           id_direccion SERIAL PRIMARY KEY,
                                           direccion     VARCHAR(50) NOT NULL,
    ciudad        VARCHAR(20) NOT NULL,
    region        VARCHAR(20) NOT NULL,
    codigo_postal INTEGER,
    usuarios_id_usuario INTEGER NOT NULL,
    CONSTRAINT direcciones_usuarios_fk
    FOREIGN KEY (usuarios_id_usuario)
    REFERENCES usuarios (id_usuario)
    );

-- Un usuario → una dirección (elimina este índice si quieres varias)
CREATE UNIQUE INDEX IF NOT EXISTS direcciones_usuario_unico
    ON direcciones (usuarios_id_usuario);

CREATE TABLE IF NOT EXISTS categorias (
                                          id_categoria SERIAL PRIMARY KEY,
                                          nombre      VARCHAR(30) NOT NULL,
    descripcion VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS productos (
                                         id_producto SERIAL PRIMARY KEY,
                                         nombre      VARCHAR(30) NOT NULL,
    precio      NUMERIC(10,2) NOT NULL,
    stock       INTEGER NOT NULL,
    descripcion VARCHAR(500),
    imagen      BYTEA,
    categorias_id_categoria INTEGER NOT NULL,
    CONSTRAINT productos_categorias_fk
    FOREIGN KEY (categorias_id_categoria)
    REFERENCES categorias (id_categoria)
    );

CREATE TABLE IF NOT EXISTS pedidos (
                                       id_pedido SERIAL PRIMARY KEY,
                                       cantidad INTEGER NOT NULL,
                                       fecha DATE NOT NULL,
                                       total NUMERIC(10,2) NOT NULL,
    productos_id_producto INTEGER NOT NULL,
    usuarios_id_usuario INTEGER NOT NULL,
    CONSTRAINT pedidos_productos_fk
    FOREIGN KEY (productos_id_producto)
    REFERENCES productos (id_producto),
    CONSTRAINT pedidos_usuarios_fk
    FOREIGN KEY (usuarios_id_usuario)
    REFERENCES usuarios (id_usuario)
    );

CREATE TABLE IF NOT EXISTS colecciones (
                                           id_coleccion SERIAL PRIMARY KEY,
                                           nombre      VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS producto_coleccion (
                                                  productos_id_producto   INTEGER NOT NULL,
                                                  colecciones_id_coleccion INTEGER NOT NULL,
                                                  CONSTRAINT producto_coleccion_pk
                                                  PRIMARY KEY (productos_id_producto, colecciones_id_coleccion),
    CONSTRAINT pc_productos_fk
    FOREIGN KEY (productos_id_producto)
    REFERENCES productos (id_producto),
    CONSTRAINT pc_colecciones_fk
    FOREIGN KEY (colecciones_id_coleccion)
    REFERENCES colecciones (id_coleccion)
    );