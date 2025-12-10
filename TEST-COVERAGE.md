# Documentación de Cobertura de Testing — Backend (Spring Boot)

Fecha: 09/12/2025
Proyecto: Level Up Gamer — Backend
Herramientas: Maven, JUnit 5, Spring Boot Test, JaCoCo

## Alcance

Esta documentación describe cómo se ejecutan las pruebas del backend, cómo se mide la cobertura (líneas, instrucciones y ramas) y qué módulos del sistema están cubiertos. Incluye comandos reproducibles y pautas para aumentar la cobertura.

## Tipos de pruebas

- Pruebas unitarias: Servicios, utilidades (`UserService`, `ProductService`, `OrderService`, `JwtUtil`).
- Pruebas de integración: Controladores REST y seguridad (JWT, filtros, `SecurityConfig`), persistencia JPA con H2/MySQL.
- Pruebas de serialización: Validación de `@JsonIgnore` y ciclos en `Order`, `OrderItem`, `User`.

## Cómo ejecutar pruebas y generar cobertura

- Compilar y ejecutar pruebas:
```powershell
cd "d:\levelup\Level-up-gamer-backend-master"
mvn clean test
```

- Generar reporte de cobertura (JaCoCo) junto a pruebas:
```powershell
mvn clean verify
```

- Ubicación del reporte HTML:
```
./target/site/jacoco/index.html
```

## Configuración sugerida de JaCoCo (pom.xml)

Asegúrate de incluir el plugin JaCoCo en `pom.xml` para medir cobertura:
```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.11</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

Opcional: fallar el build si la cobertura mínima no se cumple:
```xml
<execution>
  <id>check</id>
  <goals>
    <goal>check</goal>
  </goals>
  <configuration>
    <rules>
      <rule>
        <element>CLASS</element>
        <limits>
          <limit>
            <counter>INSTRUCTION</counter>
            <value>COVEREDRATIO</value>
            <minimum>0.60</minimum>
          </limit>
        </limits>
      </rule>
    </rules>
  </configuration>
</execution>
```

## Cobertura esperada por módulo (guía)

- `service/`
  - UserService: autenticación/registro y emisión de JWT.
  - ProductService: CRUD y filtros.
  - OrderService: validación de stock, creación de órdenes y descuento atómico.
- `controller/`
  - UserController, ProductController, OrderController: endpoints principales y manejo de errores.
- `config/`
  - SecurityConfig: reglas de seguridad y CORS; filtro JWT (`JwtAuthenticationFilter`).
- `util/`
  - JwtUtil: generación y validación de tokens.
- `model/`
  - Serialización: evitar ciclos con `@JsonIgnore`.

## Mapeo de endpoints a pruebas (ejemplos)

- `POST /api/v1/auth/login` → prueba de integración: credenciales válidas devuelven 200 y token; inválidas 401.
- `GET /api/v1/products` → prueba de integración: listado con paginación y filtros `q` y `categoryId`.
- `POST /api/v1/products` (ADMIN) → prueba de seguridad: 403 sin rol; 201 con rol.
- `POST /api/v1/orders` → prueba de negocio: stock suficiente → 201; stock insuficiente → 400/409; verifica descuento de stock y totales.
- `GET /api/v1/metrics/sales` (ADMIN) → prueba de métricas: calcula ventas y unidades desde órdenes.

## Ejecución rápida en CI/local

```powershell
mvn -T 1C clean verify
```
- `-T 1C` usa un hilo por núcleo para acelerar builds en máquinas multicore.

## Lectura del reporte

Abre `target/site/jacoco/index.html`. Revisa:
- Coverage por paquete y clase (líneas, instrucciones, branches).
- Clases críticas con baja cobertura (prioriza servicios y lógica de negocio).
- Ramas no cubiertas en validaciones (stock, roles, errores).

## Estrategia para aumentar cobertura

1. Aislar lógica de negocio en servicios (facilita unit tests).
2. Mock de repositorios y utilidades donde aplique (Mockito).
3. Tests de caminos de error: credenciales inválidas, stock insuficiente, faltan campos.
4. Tests de seguridad: acceso sin token, token expirado, rol incorrecto.
5. Tests de serialización: evitar ciclos y validar payloads.

## Umbrales recomendados

- Servicios: ≥ 80% instrucciones.
- Controladores: ≥ 70%.
- Utilidades: ≥ 90%.
- Global proyecto: ≥ 70% inicial; subir gradualmente.

## Estado actual (plantilla)

- Ejecuta `mvn clean verify` y adjunta capturas del `index.html` con:
  - Cobertura global
  - Top 5 clases con menor cobertura
  - Acciones planificadas para mejorar

## Anexos

- Configuración de `application-test.properties` para pruebas de integración.
- Seeds/fixtures para productos y usuarios de prueba.
