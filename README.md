# Proyecto de Grados: Sistema de Gestión PetShere

**PetShere** es un sistema de gestión que busca facilitar la administración y comunicación entre dueños de mascotas y servicios relacionados, como veterinarias, peluquerías y cuidadores. Este proyecto incluye una arquitectura completa con backend, frontend y base de datos, brindando una solución escalable y eficiente.

## Descripción del Proyecto
PetShere está diseñado para:
- Administrar perfiles de usuarios y mascotas
- Gestionar citas y servicios
- Ofrecer una interfaz amigable tanto para clientes como para administradores
- Mantener un registro seguro y accesible de la información de las mascotas

Este sistema está en desarrollo activo y actualmente se encuentra en la etapa de implementación

---

## Tecnologías Utilizadas

### Backend
- **Lenguaje:** Java
- **Framework:** Spring Boot
- **Base de Datos:** MySQL
- **Arquitectura:** Modelo-Vista-Controlador (MVC)

### Frontend
- **Lenguaje:** JavaScript
- **Framework:** Angular

### Otros
- **Control de Versiones:** Git y GitHub
- **Gestión de Dependencias:** Maven para el backend y npm para el frontend.
- **Contenerización:** Docker para la orquestación y contenerizar el frontend, backend y base de datos.

### Ejecutar proyecto
1. Crear los archivos .env_mysql y backend/.env con las siguientes variables de entorno:
  - **.env-mysql**
  ```bash
  MYSQL_DATABASE=petsheredb

  MYSQL_USER=

  MYSQL_PASSWORD=

  MYSQL_ROOT_PASSWORD=
  ```

  - **backend/.env**
  ```bash
  JWT_SECRET=

  URL_DB=jdbc:mysql://mysql:3306/petsheredb

  USUARIO_DB=(mismo que MYSQL_USER)

  CONTRA_DB=(mismo que MYSQL_PASSWORD)

  GMAIL_PASSWORD=(código de aplicación de SMTP de Gmail)
  ```

  

2. Ejecutar el docker-compose.yaml
```bash
sudo docker compose up --build
```

---
## Autores
| [<img src="https://github.com/sebasflorezo.png" width=115><br><sub>Johan Sebastian Florez Ospina</sub>](https://github.com/sebasflorezo) | [<img src="https://github.com/DevHurt.png" width=115><br><sub>Brayan Alexis Rojas Correa</sub>](https://github.com/DevHurt) | 
|:----------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|

## Licencia

PetShere cuenta con [MIT License](LICENSE)
