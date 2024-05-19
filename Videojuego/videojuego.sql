create database VideoJuego;
use VideoJuego;

CREATE TABLE Arma(
  codAr int primary key,
  nombre varchar(20) not null,
  dano int
);

CREATE TABLE Escudo(
  codEs int primary key,
  nombre varchar(20) not null,
  defensa int
);
  
CREATE TABLE personaje(
  id int primary key,
  nombre varchar(20) not null,
  vida int DEFAULT 20
);

insert into Arma values
(1,'arma 1',5),
(2,'arma 2',3), 
(3,'arma 3',4),
(4,'arma 4',8),
(5,'arma 5',7),
(6,'arma 6',2),
(7,'arma 7',10),
(8,'arma 8',6),
(9,'arma 9',9),
(10,'arma 10',1);

insert into Escudo values
(1,'escudo 1',5),
(2,'escudo 2',10), 
(3,'escudo 3',4),
(4,'escudo 4',1),
(5,'escudo 5',7),
(6,'escudo 6',2),
(7,'escudo 7',9),
(8,'escudo 8',6),
(9,'escudo 9',3),
(10,'escudo 10',8);

insert into Personaje (id,nombre) values
(1,'personaje 1'),
(2,'personaje 2'), 
(3,'personaje 3'),
(4,'personaje 4'),
(5,'personaje 5'),
(6,'personaje 6'), 
(7,'personaje 7'),
(8,'personaje 8'),
(9,'personaje 9'),
(10,'personaje 10');