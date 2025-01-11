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
	usuario VARCHAR(50) NOT NULL UNIQUE,
    documento VARCHAR(20) NOT NULL UNIQUE,
    primerNombre VARCHAR(50) NOT NULL,
    segundoNombre VARCHAR(50),
    primerApellido VARCHAR(50) NOT NULL,
    segundoApellido VARCHAR(50),
    telefono VARCHAR(20),
    direccion VARCHAR(150),
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    salt VARCHAR(200),
    idRol INT NOT NULL,
    FOREIGN KEY (idRol) REFERENCES ROLES(id)
);

CREATE TABLE MASCOTAS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    raza VARCHAR(50),
    foto VARCHAR(50),
    peso DECIMAL(5,2), -- DECIMAL(tamaño en dígitos, # de decimales), MÁXIMO SERÍA (99999,99)
    especie VARCHAR(50),
    preferenciaAlimenticia VARCHAR(200),
    fechaNacimiento DATE,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    idCliente INT NOT NULL,
	FOREIGN KEY (idCliente) REFERENCES USUARIOS(id)
);

CREATE TABLE RESERVAS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fechaInicio DATETIME NOT NULL,
    fechaFin DATETIME NOT NULL,
    estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    idCliente INT NOT NULL,
    idCuidador INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES USUARIOS(id),
	FOREIGN KEY (idCuidador) REFERENCES USUARIOS(id)
);

CREATE TABLE SERVICIOS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    tipo VARCHAR(50),
    detalle VARCHAR(50),
    precio INT,
    idReserva INT NOT NULL,
    FOREIGN KEY (idReserva) REFERENCES RESERVAS(id)
);

CREATE TABLE PAGOS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    tipoPago ENUM('EFECTIVO','TARJETA','TRANSFERENCIA') DEFAULT 'EFECTIVO',
    precioTotal INT NOT NULL,
    idReserva INT NOT NULL,
    FOREIGN KEY (idReserva) REFERENCES RESERVAS(id)
);

CREATE TABLE HISTORIALES (
	id INT AUTO_INCREMENT PRIMARY KEY,
    fechaConsulta DATE,
    descripcion VARCHAR(300),
    tratameinto VARCHAR(300),
    idMascota INT NOT NULL,
    idCuidador INT NOT NULL,
    FOREIGN KEY (idMascota) REFERENCES MASCOTAS(id),
    FOREIGN KEY (idCuidador) REFERENCES USUARIOS(id)
);

CREATE TABLE NOTIFICACIONES (
	id INT AUTO_INCREMENT PRIMARY KEY,
    contenido VARCHAR(100) NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES USUARIOS(id)
);