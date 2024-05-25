create database Relaciones;
use Relaciones;

create table Mujeres(
    codigo int primary key,
    nombre varchar(15),
	v1   decimal(3,2),
	v2   decimal(3,2),
	v3   decimal(3,2),
	v4   decimal(3,2),
	v5   decimal(3,2));
	
create table Hombres(
    codigo int primary key,
    nombre varchar(15),
	v1   decimal(3,2),
	v2   decimal(3,2),
	v3   decimal(3,2),
	v4   decimal(3,2),
	v5   decimal(3,2));

create table Emparejamientos(
    codigoH int,
	codigoM int,
	primary key(codigoH,codigoM),
	grado   decimal(3,2));
	
insert into Mujeres values (100,'Mujer1',0.74,0.31,0.18,0.22,0.20);
insert into Mujeres values (110,'Mujer2',0.42,0.51,0.68,0.94,0.68);
insert into Mujeres values (120,'Mujer3',0.32,0.46,0.78,0.45,0.92);
insert into Mujeres values (130,'Mujer4',0.86,0.73,0.59,0.01,0.79);
insert into Mujeres values (140,'Mujer5',0.46,0.68,0.35,0.77,0.96);
insert into Mujeres values (150,'Mujer6',0.64,1.00,0.32,0.21,0.92);
insert into Mujeres values (160,'Mujer7',0.13,0.69,0.56,0.70,0.81);
insert into Mujeres values (170,'Mujer8',0.04,0.14,0.02,0.09,0.65);
insert into Mujeres values (180,'Mujer9',0.11,0.33,0.76,0.61,0.51);
insert into Mujeres values (190,'Mujer10',0.67,0.15,0.95,0.62,0.69);
insert into Hombres values (200,'Hombre1',0.29,0.32,0.79,0.03,0.27);
insert into Hombres values (210,'Hombre2',0.20,0.61,0.21,0.21,0.86);
insert into Hombres values (220,'Hombre3',0.75,0.75,0.34,0.24,1.00);
insert into Hombres values (230,'Hombre4',0.86,0.55,0.75,0.40,0.00);
insert into Hombres values (240,'Hombre5',0.61,0.89,0.60,1.00,0.04);
insert into Hombres values (250,'Hombre6',0.09,0.32,0.81,0.96,0.02);
insert into Hombres values (260,'Hombre7',0.39,0.81,0.01,0.33,0.57);
insert into Hombres values (270,'Hombre8',0.34,0.60,0.00,0.54,0.12);
insert into Hombres values (280,'Hombre9',0.49,0.41,0.06,0.12,0.52);
insert into Hombres values (290,'Hombre10',0.28,0.63,0.16,0.41,0.90);