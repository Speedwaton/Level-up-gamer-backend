# Level Up Gamer - Backend

API REST desarrollada con Spring Boot para la plataforma de e-commerce Level Up Gamer.

## Tecnologías

- Java 21
- Spring Boot 3.3.5
- Spring Security con JWT
- MySQL
- Maven

## Requisitos Previos

- Java 21 o superior
- Maven 3.6+
- MySQL 8.0+

## Configuración de Base de Datos

1. Crear la base de datos en MySQL:
```sql
CREATE DATABASE levelupgamer;
```

2. Configurar las credenciales en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/levelupgamer
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

3. (Opcional) Cargar productos iniciales:
```sql
source init-products.sql
```

## Instalación y Ejecución

1. Clonar el repositorio:
```bash
git clone https://github.com/Speedwaton/Level-up-gamer-backend.git
cd Level-up-gamer-backend
```

2. Instalar dependencias y compilar:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

El backend estará disponible en `http://localhost:8080`

## Usuario Administrador

Al iniciar la aplicación por primera vez, se crea automáticamente un usuario administrador:

- **Email:** admin@levelupgamer.cl
- **Contraseña:** admin123
- **RUT:** 12345678

## Endpoints Principales

- `GET /api/v1/products` - Listar productos
- `POST /api/v1/auth/login` - Login de usuarios
- `POST /api/v1/auth/register` - Registro de usuarios
- `POST /api/v1/orders/checkout` - Realizar compra
- `GET /api/v1/admin/users` - Listar usuarios (requiere rol admin)
- `GET /api/v1/admin/stats` - Estadísticas del sistema (requiere rol admin)

## Seguridad

La aplicación utiliza JWT (JSON Web Tokens) para autenticación. Los tokens se generan al hacer login/registro y deben incluirse en el header `Authorization: Bearer {token}` para endpoints protegidos.

## Estructura del Proyecto

```
src/main/java/com/levelup/gamer/
├── config/          # Configuración de seguridad, CORS, DataLoader
├── controller/      # Controladores REST
├── dto/            # Data Transfer Objects
├── filter/         # Filtros de autenticación JWT
├── model/          # Entidades JPA
├── repository/     # Repositorios JPA
├── service/        # Lógica de negocio
└── util/           # Utilidades (JwtUtil)
```
