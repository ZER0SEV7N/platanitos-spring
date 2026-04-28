drop database if exists platanitos;
create database platanitos;

use platanitos;

create table color (
    id int auto_increment primary key,
    color char(10) not null
);

create table talla (
    id int auto_increment primary key,
    talla char(3) not null
);

create table categoria (
    id int auto_increment primary key,
    categoria char(30) not null
);

create table usuario (
    id int auto_increment primary key,
    nombre char(50) not null,
    apellido char(50) not null,
    email char(100) unique,
    contraseña char(255) not null
);

create table producto (
    id int primary key auto_increment,
    producto char(100) not null,
    descripcion text,
    precio decimal(10, 2) not null,
    stock int not null,
    idcolor int references color(id),
    idtalla int references talla(id),
    idcategoria int references categoria(id),
    estado boolean default true
);

create table carrito (
    id int auto_increment primary key,
    idusuario int references usuario(id),
    idproducto int references producto(id),
    cantidad int not null
);

insert color values 
(null, 'rojo'),
(null, 'azul'),
(null, 'verde'),
(null, 'amarillo'),
(null, 'negro'),
(null, 'gris'),
(null, 'blanco'),
(null, 'rosado');

insert talla values
(null, 'xs'),
(null, 's'),
(null, 'm'),
(null, 'l'),
(null, 'xl');

insert categoria values
(null, 'zapatos'),
(null, 'ropa'),
(null, 'pantalon'),
(null, 'sandalias'),
(null, 'ropa deportiva'),
(null, 'mochilas');

insert producto values
(null, "zapatillas urbanas hombre", "la comodidad se une a la forma. la elegante parte superior de malla te mantiene fresco, mientras que la unidad max air en el talón aporta elasticidad a tus pasos.", 399.00, 50, 2, 5, 1, true ),
(null, "polo training hombre boxed sports", "sumérgete en la energía y el estilo con nuestro polo training hombre boxed sports updated de under armour.", 59.90, 50, 5, 2, 2, true);
