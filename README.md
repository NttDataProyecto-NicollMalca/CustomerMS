# CustomerMS - Customer Management Service

## Descripción

**CustomerMS** es un microservicio que gestiona la información de clientes, permitiendo realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los mismos. Además, este servicio está integrado con otro microservicio (`AccountMS`) para verificar si un cliente tiene cuentas activas antes de ser eliminado.

## Requisitos

- **Java 8 o superior**
- **Maven 3.6+**
- **MySQL**
- **Spring Boot**

## Instalación y Configuración

### 1. Clonar el repositorio

Primero, clona el repositorio desde GitHub:

```bash
git clone https://github.com/tu-usuario/CustomerMS.git
cd CustomerMS
2. Configurar la base de datos MySQL
Crea el esquema de la base de datos en MySQL ejecutando el siguiente comando:

sql
Copiar código
CREATE SCHEMA nttdata1;
3. Configurar las propiedades de conexión
Configura las credenciales de conexión a la base de datos en el archivo src/main/resources/application.properties:

properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/nttdata1
spring.datasource.username=root
spring.datasource.password=tu_contraseña
Asegúrate de reemplazar root y tu_contraseña con tus credenciales de MySQL.
