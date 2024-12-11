DROP DATABASE IF EXISTS PetShereDB;	
CREATE DATABASE PetShereDB;
USE PetShereDB;

CREATE TABLE ROLES (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE USUARIOS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    documento VARCHAR(20) NOT NULL UNIQUE,
    primerNombre VARCHAR(50) NOT NULL,
    segundoNombre VARCHAR(50),
    primerApellido VARCHAR(50) NOT NULL,
    segundoApellido VARCHAR(50),
    telefono VARCHAR(20),
    direccion VARCHAR(150),
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(200) NOT NULL,
    salt VARCHAR(200) NOT NULL,
    idRol INT NOT NULL,
    FOREIGN KEY (idRol) REFERENCES ROLES(id) -- ON DELETE CASCADE ON UPDATE CASCADE -- Necesario on delete? 
);

CREATE TABLE MASCOTAS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    raza VARCHAR(50),
    foto BLOB,  -- BLOB = (65,535 bytes, 65KB) MEDIUMBLOB = (16,777,215 bytes, 16MB) LONGBLOB = (4,294,967,295 bytes, 4GB), Y PERMITE GUARDAR CUALQUIER ARCHIVO
    peso DECIMAL(5,2), -- (tamaño en dígitos, # de decimales) MÁXIMO SERÍA (99999,99)
    especie VARCHAR(50),
    preferenciaAlimenticia VARCHAR(200),
    fechaNacimiento DATE,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    idCliente INT NOT NULL,
	FOREIGN KEY (idCliente) REFERENCES USUARIOS(id) -- LO MISMO QUE EN USUARIOS
);

CREATE TABLE RESERVAS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fechaInicio DATETIME NOT NULL,
    fechaFin DATETIME NOT NULL,
    estado ENUM('', '') DEFAULT '',
    idCliente INT NOT NULL,
    idCuidador INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES USUARIOS(id), -- LO MISMO QUE EN USUARIOS
	FOREIGN KEY (idCuidador) REFERENCES USUARIOS(id) -- LO MISMO QUE EN USUARIOS
);

CREATE TABLE SERVICIOS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    tipo VARCHAR(50),
    detalle VARCHAR(50),
    precio INT,
    idReserva INT NOT NULL,
    FOREIGN KEY (idReserva) REFERENCES RESERVAS(id) -- LO MISMO QUE EN USUARIOS
);

CREATE TABLE PAGOS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    tipoPago ENUM('','','') NOT NULL, -- Efectivo, Tarjeta, Transferencia? Más o menos??
    precioTotal INT NOT NULL,
    idReserva INT NOT NULL,
    FOREIGN KEY (idReserva) REFERENCES RESERVAS(id) -- LO MISMO QUE EN USUARIOS
);

-- id, fechaConsulta, descripcion, tratamiento, idMascota, idCuidador
CREATE TABLE HISTORIALES (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fechaConsulta DATE,
    descripcion VARCHAR(300),
    tratameinto VARCHAR(300),
    idMascota INT NOT NULL,
    idCuidador INT NOT NULL,
    FOREIGN KEY (idMascota) REFERENCES MASCOTAS(id), -- LO MISMO QUE EN USUARIOS
    FOREIGN KEY (idCuidador) REFERENCES USUARIOS(id) -- LO MISMO QUE EN USUARIOS
);

CREATE TABLE NOTIFICACIONES (
	id INT AUTO_INCREMENT PRIMARY KEY,
    contenido VARCHAR(100) NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES USUARIOS(id) -- LO MISMO QUE EN USUARIOS
);