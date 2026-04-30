drop database if exists platanitos;
create database platanitos;

use platanitos;

-- Tabla color
create table color (
    id int auto_increment primary key,
    color char(10) not null
);

-- Tabla Talla
create table talla (
    id int auto_increment primary key,
    talla char(3) not null
);

-- Tabla Categoria
create table categoria (
    id int auto_increment primary key,
    categoria char(30) not null
);

-- Tabla Usuario
create table usuario (
    id int auto_increment primary key,
    nombre char(50) not null,
    apellido char(50) not null,
    email char(100) unique,
    contraseña char(255) not null
);

-- Tabla Producto
create table producto (
    id int primary key auto_increment,
    producto char(100) not null,
    descripcion text,
    idcategoria int references categoria(id),
    estado boolean default true
);

-- Tabla producto_variante
create table producto_variante (
    id int auto_increment primary key,
    idproducto int references producto(id),
    idcolor int references color(id),
    idtalla int references talla(id),
    stock int not null,
    precio decimal(10, 2) not null,
    estado boolean default true
);

-- Tabla carrito
create table carrito (
    id int auto_increment primary key,
    idusuario int references usuario(id),
    fecha datetime default current_timestamp,
    total decimal(10, 2) not null
);

-- Tabla Carrito Detalle
create table carrito_detalle (
    id int auto_increment primary key,
    idcarrito int references carrito(id),
    idproductovariante int references producto_variante(id),
    cantidad int not null,
    subtotal decimal(10, 2) not null
);

-- Inserción de datos
insert color values 
(null, 'rojo'),
(null, 'azul'),
(null, 'verde'),
(null, 'amarillo'),
(null, 'negro'),
(null, 'gris'),
(null, 'blanco'),
(null, 'rosado'),
(null, 'naranja'),
(null, 'morado');

-- Insercion de talla
insert talla values
(null, 'xs'),
(null, 's'),
(null, 'm'),
(null, 'l'),
(null, 'xl'),
(null, 'xxl');

-- Insercion de categoria
insert categoria values
(null, 'zapatos'),
(null, 'ropa'),
(null, 'pantalon'),
(null, 'sandalias'),
(null, 'ropa deportiva'),
(null, 'mochilas'),
(null, 'accesorios');

-- Insercion de productos
insert producto values 
(null, 'Zapatillas Urbanas Street', 'Zapatillas de lona vulcanizada, ideales para caminar por la ciudad.', 1, true),
(null, 'Polo Compression Pro', 'Polo ajustado con tecnología de secado rápido para entrenamiento.', 5, true),
(null, 'Casaca Cortavientos Runner', 'Diseño ultraligero y resistente al agua, perfecto para el clima cambiante.', 2, true);

-- Insercion de variantes de productos
insert producto_variante values
(null, 1, 5, 3, 50, 199.90, true),  -- Negras talla M
(null, 1, 5, 4, 30, 199.90, true),  -- Negras talla L
(null, 1, 2, 3, 15, 199.90, true),  -- Azules talla M

-- Variantes para Polo Compression Pro (Producto 2)
(null, 2, 5, 2, 100, 79.90, true),  -- Negro talla S
(null, 2, 5, 3, 80, 79.90, true),   -- Negro talla M
(null, 2, 1, 4, 25, 79.90, true),   -- Rojo talla L

-- Variantes para Casaca Cortavientos (Producto 3)
(null, 3, 2, 4, 10, 250.00, true),  -- Azul talla L
(null, 3, 5, 4, 5, 250.00, true);   -- Negra talla L
