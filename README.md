# 🎮 Level Up Gamer - Backend

API REST para e-commerce de productos gamer desarrollada con Spring Boot + MySQL.

## 👥 Autores

- **Giancarlo Ovalle** - Backend Developer
- **Agustín Moya** - Backend Developer

## 🚀 Tecnologías

- **Spring Boot 3.3.5** - Framework Java
- **Spring Data JPA** - ORM para persistencia
- **MySQL 8.4.3** - Base de datos relacional
- **Spring Security** - Autenticación y seguridad
- **Maven** - Gestión de dependencias
- **Hibernate** - ORM
- **Java 21** - Lenguaje de programación

## 📋 Características

### Funcionalidades principales:
- ✅ API REST completa con endpoints CRUD
- ✅ Autenticación de usuarios (login/registro)
- ✅ Gestión de productos (crear, leer, actualizar, eliminar)
- ✅ Sistema de órdenes de compra
- ✅ Validación de correos institucionales (@duocuc.cl)
- ✅ Descuentos automáticos para usuarios DuocUC
- ✅ Estadísticas para panel administrativo
- ✅ CORS configurado para frontend
- ✅ Carga inicial de 13 productos desde DataLoader

### Entidades principales:
- **User** - Usuarios del sistema
- **Product** - Productos del catálogo
- **Order** - Órdenes de compra
- **OrderItem** - Items de cada orden

## 🔧 Requisitos previos

- **Java 21** o superior
- **Maven 3.8+**
- **MySQL 8.x** (Laragon, XAMPP, o instalación independiente)
- **Puerto 8080** disponible

## 📦 Instalación

```bash
# Clonar repositorio
git clone https://github.com/Speedwaton/Level-up-gamer-backend.git
cd Level-up-gamer-backend

# Compilar proyecto
mvn clean install
```

## 🗄️ Configuración de Base de Datos

### 1. Crear base de datos en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS levelupgamer;
USE levelupgamer;
```

### 2. Importar estructura (opcional):

```bash
# Ejecutar el script revisa.sql incluido en el proyecto
mysql -u root -p levelupgamer < revisa.sql
```

### 3. Configurar credenciales:

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/levelupgamer?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

## ▶️ Ejecución

### Opción 1: Maven
```bash
mvn spring-boot:run
```

### Opción 2: JAR compilado
```bash
mvn clean package
java -jar target/levelupgamer-0.0.1-SNAPSHOT.jar
```

El servidor estará disponible en: `http://localhost:8080`

## 📡 Endpoints de la API

### Health Check
- `GET /api/v1/health` - Estado del servidor
- `GET /api/v1/info` - Información de la API

### Productos
- `GET /api/v1/products` - Listar todos
- `GET /api/v1/products/{id}` - Obtener por ID
- `GET /api/v1/products/slug/{slug}` - Obtener por slug
- `GET /api/v1/products/featured` - Productos destacados (top 4)
- `GET /api/v1/products/category/{categoria}` - Por categoría
- `GET /api/v1/products/search?q={query}` - Buscar productos
- `POST /api/v1/products` - Crear producto
- `PUT /api/v1/products/{id}` - Actualizar producto
- `DELETE /api/v1/products/{id}` - Eliminar producto

### Usuarios
- `POST /api/v1/users/login` - Iniciar sesión
- `POST /api/v1/users/register` - Registrar usuario
- `GET /api/v1/users` - Listar todos los usuarios
- `GET /api/v1/users/{id}` - Obtener usuario por ID
- `GET /api/v1/users/email/{email}` - Buscar por email
- `GET /api/v1/users/exists?email={email}` - Verificar si existe

### Órdenes
- `POST /api/v1/orders/checkout` - Procesar compra
- `GET /api/v1/orders` - Listar todas las órdenes
- `GET /api/v1/orders/{id}` - Obtener orden por ID
- `GET /api/v1/orders/user/{userId}` - Órdenes de un usuario
- `GET /api/v1/orders/email/{email}` - Órdenes por email
- `PATCH /api/v1/orders/{id}/status?estado={estado}` - Actualizar estado

### Administración
- `GET /api/v1/admin/stats` - Estadísticas del dashboard
- `GET /api/v1/admin/categories` - Lista de categorías

## 📁 Estructura del proyecto

```
src/main/java/com/levelup/gamer/
├── config/              # Configuraciones
│   ├── CorsConfig.java
│   ├── SecurityConfig.java
│   └── DataLoader.java
├── controller/          # Controladores REST
│   ├── ProductController.java
│   ├── UserController.java
│   ├── OrderController.java
│   ├── AdminController.java
│   └── HealthController.java
├── service/            # Lógica de negocio
│   ├── ProductService.java
│   ├── UserService.java
│   ├── OrderService.java
│   └── StatsService.java
├── repository/         # Acceso a datos (JPA)
│   ├── ProductRepository.java
│   ├── UserRepository.java
│   ├── OrderRepository.java
│   └── OrderItemRepository.java
├── model/              # Entidades JPA
│   ├── Product.java
│   ├── User.java
│   ├── Order.java
│   └── OrderItem.java
└── dto/                # Data Transfer Objects
    ├── ProductDTO.java
    ├── UserDTO.java
    ├── LoginRequest.java
    ├── RegisterRequest.java
    └── CheckoutRequest.java
```

## 🗂️ Modelo de Base de Datos

### Tabla `products`
- Catálogo de productos gamer
- 13 productos precargados
- Campos: slug, nombre, categoría, precio, imagen, stock, etc.

### Tabla `users`
- Usuarios registrados
- Descuento automático para correos @duocuc.cl (8%)
- Validación de RUT chileno
- Campo `es_admin` para permisos

### Tabla `orders`
- Órdenes de compra procesadas
- Estados: PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO
- Relación con User (opcional)

### Tabla `order_items`
- Items individuales de cada orden
- Relación con Order y Product

## 🔒 Seguridad

### Spring Security configurado:
- CSRF deshabilitado (para desarrollo)
- Todos los endpoints públicos (permitAll)
- Autenticación básica por email/password

### Validaciones:
- Correos únicos en la base de datos
- RUT chileno válido y único
- Contraseñas mínimo 6 caracteres
- Correos institucionales @duocuc.cl obtienen 8% descuento

## 🧪 Pruebas con Postman

El proyecto incluye una colección completa de Postman:
- Archivo: `Level-Up-Gamer-API.postman_collection.json`
- 20+ requests organizadas por carpetas
- Ejemplos de uso para todos los endpoints

**Importar en Postman:**
1. Abre Postman
2. File → Import
3. Selecciona `Level-Up-Gamer-API.postman_collection.json`

## 📊 Datos de prueba

### Usuario administrador por defecto:
```json
{
  "email": "admin@levelupgamer.com",
  "password": "admin123"
}
```

### Productos precargados:
- PlayStation 5
- PC Gamer ASUS ROG
- Xbox Series S
- Silla Gamer Secretlab
- Mouse Logitech G502
- Auriculares HyperX Cloud II
- Y 7 productos más...

## 🐛 Solución de problemas

### Error de conexión a MySQL:
1. Verifica que MySQL esté corriendo (Laragon/XAMPP)
2. Confirma credenciales en `application.properties`
3. Asegúrate que la base de datos `levelupgamer` exista

### Puerto 8080 ocupado:
Cambia el puerto en `application.properties`:
```properties
server.port=8081
```
(Recuerda actualizar la URL en el frontend)

### Error al iniciar Spring Boot:
```bash
# Limpiar compilaciones anteriores
mvn clean
mvn clean install
mvn spring-boot:run
```

## 📝 Variables de entorno

No se requieren variables de entorno. Toda la configuración está en `application.properties`.

## 📄 Licencia

Proyecto académico - Evaluación 3 - Desarrollo Full Stack II

## 🔗 Enlaces relacionados

- **Frontend:** https://github.com/Speedwaton/Level-up-gamer-frontend
- **Postman Collection:** Incluida en este repositorio

---

**Desarrollado por Giancarlo Ovalle y Agustín Moya - 2025**
