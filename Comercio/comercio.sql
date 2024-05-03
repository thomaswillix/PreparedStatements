create database Comercio;
use Comercio;

CREATE TABLE Productos (
 Codprod  int PRIMARY KEY,
 Precio   DECIMAL(4,1)
)Engine=InnoDB;

INSERT INTO productos VALUES (10,2.50);
INSERT INTO productos VALUES (20,20);
INSERT INTO productos VALUES (30,150);
INSERT INTO productos VALUES (40,12.5);
INSERT INTO productos VALUES (50,240);
INSERT INTO productos VALUES (60,25.75);
INSERT INTO productos VALUES (70,36.1);
INSERT INTO productos VALUES (80,10.45);
INSERT INTO productos VALUES (90,6);

CREATE TABLE Ventas (
 CodVend varchar(10),
 Codprod  int,
 Vendido  int,
 Ganancia DECIMAL(5,1)
)Engine=InnoDB;


INSERT INTO Ventas VALUES ('c08',80,10,104.5);
INSERT INTO Ventas VALUES ('f22',10,20,50);
INSERT INTO Ventas VALUES ('d18',30,2,300);
INSERT INTO Ventas VALUES ('a01',40,8,100);
INSERT INTO Ventas VALUES ('p05',50,1,240);
INSERT INTO Ventas VALUES ('s01',60,3,77.25);
INSERT INTO Ventas VALUES ('b04',70,5,180.5);
INSERT INTO Ventas VALUES ('a01',40,20,250);
INSERT INTO Ventas VALUES ('b14',90,6,36);