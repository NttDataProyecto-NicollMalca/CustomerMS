# CustomerMS - Customer Management Service

## Descripción

CustomerMS es un microservicio que gestiona la información de clientes y permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los mismos. Este servicio también está conectado con otro microservicio (`AccountMS`) para verificar si un cliente tiene cuentas activas antes de ser eliminado.

## Requisitos

- **Java 8 o superior**
- **Maven 3.6+**
- **MySQL**
- **Spring Boot**

## Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/CustomerMS.git
cd CustomerMS

### 2. Configurar la base de datos MySQL

CREATE SCHEMA nttdata1;
Configura las propiedades de conexión en el archivo src/main/resources/application.properties:
spring.datasource.username=root
spring.datasource.password=tu_contraseña



