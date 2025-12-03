# Documentación de API - LEVEL UP GAMER API

**Versión:** 1.0  
**Fecha:** 02/12/2025

**URL Base:** `http://localhost:8080/api/v1`  
**URL Swagger:** `http://localhost:8080/swagger-ui.html`

---

## 📋 Tabla de Endpoints

| Método HTTP | Ruta del Endpoint | Descripción | Datos de entrada | Respuestas | API PÚBLICA/PRIVADA | Requiere Autenticación | Roles permitidos | Observaciones |
|-------------|-------------------|-------------|------------------|------------|---------------------|------------------------|------------------|---------------|
| **GET** | `/products` | Lista todos los productos de la tienda | N/A | 200: Éxito, 500: Error servidor | Pública | No | N/A | Retorna catálogo completo con stock |
| **GET** | `/products/{id}` | Obtiene detalles de un producto específico | N/A | 200: Éxito, 404: No encontrado | Pública | No | N/A | Incluye specs, relacionados, stock |
| **GET** | `/products/slug/{slug}` | Obtiene producto por slug (URL amigable) | N/A | 200: Éxito, 404: No encontrado | Pública | No | N/A | Usado para páginas de detalle |
| **GET** | `/products/category/{categoria}` | Lista productos por categoría | N/A | 200: Éxito, 500: Error servidor | Pública | No | N/A | Categorías: Consolas, Mouse, Teclado, etc. |
| **GET** | `/products/search?q={query}` | Busca productos por nombre | query: "PlayStation" | 200: Éxito, 500: Error servidor | Pública | No | N/A | Búsqueda case-insensitive |
| **GET** | `/products/featured` | Obtiene productos destacados (primeros 4) | N/A | 200: Éxito, 500: Error servidor | Pública | No | N/A | Para página de inicio |
| **POST** | `/products` | Crea un nuevo producto | {nombre, slug, categoria, precio, imagen, stock...} | 201: Creado, 400: Datos inválidos | Privada | Sí | Admin | Slug debe ser único |
| **PUT** | `/products/{id}` | Actualiza información de un producto | {nombre, precio, stock, descripcion...} | 200: Éxito, 404: No encontrado | Privada | Sí | Admin | Actualización completa |
| **DELETE** | `/products/{id}` | Elimina un producto | N/A | 200: Éxito, 404: No encontrado | Privada | Sí | Admin | Eliminación permanente |
| **POST** | `/users/register` | Registra un nuevo usuario | {run, nombre, apellidos, correo, password, fechaNacimiento...} | 201: Creado, 400: Correo existente | Pública | No | N/A | Valida edad 18+, aplica descuento DuocUC |
| **POST** | `/users/login` | Inicia sesión de usuario | {email, password} | 200: Éxito, 401: Credenciales inválidas | Pública | No | N/A | Retorna datos del usuario y flag admin |
| **GET** | `/users` | Lista todos los usuarios | N/A | 200: Éxito, 500: Error servidor | Privada | Sí | Admin | Solo para panel administrativo |
| **GET** | `/users/{id}` | Obtiene detalles de un usuario | N/A | 200: Éxito, 404: No encontrado | Privada | Sí | Admin | Información completa del usuario |
| **GET** | `/users/email/{email}` | Busca usuario por correo | N/A | 200: Éxito, 404: No encontrado | Privada | Sí | Admin | Usado para validaciones |
| **POST** | `/orders/checkout` | Procesa una orden de compra | {user: {...}, items: [...], subtotal, discount, total} | 200: Éxito, 400: Error validación | Pública | No | N/A | Genera ORDER{timestamp}, aplica descuentos |
| **GET** | `/orders` | Lista todas las órdenes | N/A | 200: Éxito, 500: Error servidor | Privada | Sí | Admin | Para reportes administrativos |
| **GET** | `/orders/{id}` | Obtiene detalles de una orden | N/A | 200: Éxito, 404: No encontrado | Privada | Sí | Admin | Incluye items y estado |
| **GET** | `/orders/order-id/{orderId}` | Busca orden por OrderId | N/A | 200: Éxito, 404: No encontrado | Pública | No | N/A | Para confirmación de compra |
| **GET** | `/orders/user/{userId}` | Obtiene órdenes de un usuario | N/A | 200: Éxito, 500: Error servidor | Privada | Sí | Admin/Usuario | Historial de compras |
| **GET** | `/orders/email/{email}` | Busca órdenes por correo | N/A | 200: Éxito, 500: Error servidor | Pública | No | N/A | Para tracking sin login |
| **GET** | `/orders/status/{estado}` | Filtra órdenes por estado | N/A | 200: Éxito, 500: Error servidor | Privada | Sí | Admin | Estados: PENDIENTE, COMPLETADO, CANCELADO |
| **PATCH** | `/orders/{id}/status` | Actualiza estado de una orden | estado: "COMPLETADO" | 200: Éxito, 404: No encontrado | Privada | Sí | Admin | Gestión de pedidos |
| **GET** | `/admin/stats` | Obtiene estadísticas del sistema | N/A | 200: Éxito, 500: Error servidor | Privada | Sí | Admin | Total usuarios, órdenes, ventas, productos |
| **GET** | `/admin/stats/sales-by-category` | Ventas agrupadas por categoría | N/A | 200: Éxito, 500: Error servidor | Privada | Sí | Admin | Para gráficos de dashboard |
| **GET** | `/health` | Verifica estado del servidor | N/A | 200: {ok: true, message: "Backend funcionando"} | Pública | No | N/A | Health check para monitoreo |
| **GET** | `/health/db` | Verifica conexión a base de datos | N/A | 200: Éxito, 500: Error conexión | Pública | No | N/A | Valida MySQL disponible |

---

## 🔐 Autenticación

Actualmente la API usa **sesiones simuladas** con header `X-User-Email`:

```http
X-User-Email: usuario@duocuc.cl
```

**Credenciales Admin:**
- Email: `admin@levelupgamer.com`
- Password: `admin123`

---

## 📦 Modelos de Datos

### Product
```json
{
  "id": 1,
  "slug": "playstation-5",
  "nombre": "PlayStation 5",
  "categoria": "Consolas",
  "precio": 549990.0,
  "imagen": "/img/ps5-5.png.jpg",
  "resumen": "Gráficos 4K, ray tracing...",
  "descripcion": "La PlayStation 5 ofrece...",
  "specs": ["CPU AMD Zen 2...", "GPU RDNA 2..."],
  "incluye": ["1 consola PlayStation 5", "1 control DualSense..."],
  "relacionados": ["pc-gamer-asus-rog", "xbox-series-s"],
  "stock": 10
}
```

### User
```json
{
  "id": 1,
  "run": "12345678K",
  "nombre": "Juan",
  "apellidos": "Pérez González",
  "correo": "juan@duocuc.cl",
  "password": "***",
  "fechaNacimiento": "2000-01-15",
  "region": "Región Metropolitana",
  "comuna": "Santiago",
  "direccion": "Av. Principal 123",
  "descuento": 20,
  "esAdmin": false
}
```

### Order
```json
{
  "id": 1,
  "orderId": "ORDER1733184123456",
  "fecha": "2025-12-02T21:35:23",
  "subtotal": 1299990.0,
  "descuentoAplicado": 259998.0,
  "total": 1039992.0,
  "estado": "COMPLETADO",
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@duocuc.cl",
  "calle": "Av. Principal 123",
  "departamento": "1A",
  "region": "Región Metropolitana",
  "comuna": "Santiago",
  "items": [
    {
      "id": 1,
      "slug": "pc-gamer-asus-rog",
      "nombre": "PC Gamer ASUS ROG Strix",
      "precio": 1299990.0,
      "cantidad": 1,
      "subtotal": 1299990.0
    }
  ]
}
```

### CheckoutRequest
```json
{
  "user": {
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@duocuc.cl",
    "calle": "Av. Principal 123",
    "departamento": "1A",
    "region": "Región Metropolitana",
    "comuna": "Santiago",
    "indicaciones": "Casa azul, segundo piso"
  },
  "items": [
    {
      "productSlug": "playstation-5",
      "quantity": 1,
      "price": 549990.0
    }
  ],
  "subtotal": 549990.0,
  "discount": 0.0,
  "total": 549990.0
}
```

---

## 🚀 Ejemplos de Uso

### Obtener todos los productos
```bash
curl -X GET http://localhost:8080/api/v1/products
```

### Buscar producto por slug
```bash
curl -X GET http://localhost:8080/api/v1/products/slug/playstation-5
```

### Registrar nuevo usuario
```bash
curl -X POST http://localhost:8080/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "run": "12345678K",
    "nombre": "Juan",
    "apellidos": "Pérez",
    "correo": "juan@duocuc.cl",
    "password": "password123",
    "fechaNacimiento": "2000-01-15",
    "region": "Región Metropolitana",
    "comuna": "Santiago",
    "direccion": "Av. Principal 123"
  }'
```

### Realizar checkout
```bash
curl -X POST http://localhost:8080/api/v1/orders/checkout \
  -H "Content-Type: application/json" \
  -d '{
    "user": {
      "nombre": "Juan",
      "apellido": "Pérez",
      "email": "juan@duocuc.cl",
      "calle": "Av. Principal 123",
      "region": "Región Metropolitana",
      "comuna": "Santiago"
    },
    "items": [
      {
        "productSlug": "playstation-5",
        "quantity": 1,
        "price": 549990.0
      }
    ],
    "subtotal": 549990.0,
    "discount": 0.0,
    "total": 549990.0
  }'
```

### Obtener estadísticas (Admin)
```bash
curl -X GET http://localhost:8080/api/v1/admin/stats
```

---

## 🛠️ Configuración

### Base de Datos (MySQL)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/levelupgamer?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

### CORS
Permitido para:
- `http://localhost:3000`
- `http://localhost:3100`
- `http://localhost:5173`

---

## 📝 Notas Importantes

1. **Descuento DuocUC:** Usuarios con correo `@duocuc.cl` o `@profesor.duoc.cl` reciben 20% de descuento automático
2. **Validación de edad:** El registro requiere ser mayor de 18 años
3. **Stock:** Los productos tienen control de stock pero no se descuenta automáticamente al comprar
4. **Estados de orden:** PENDIENTE, COMPLETADO, CANCELADO
5. **OrderId:** Se genera automáticamente con formato `ORDER{timestamp}`
6. **Slug:** Debe ser único para cada producto (validación al crear)

---

## 🐛 Códigos de Error Comunes

- **400 Bad Request:** Datos inválidos o incompletos
- **404 Not Found:** Recurso no encontrado
- **500 Internal Server Error:** Error en el servidor o base de datos

---

## 📧 Contacto

- **Desarrolladores:** Giancarlo Ovalle, Agustín Moya
- **Email:** gianovalle21@gmail.com, agumoya889@gmail.com
- **GitHub:** https://github.com/AguMoya889/LEVEL-UP-GAMER-REACT
