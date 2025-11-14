# DogiMax API - Backend Application

Backend desarrollado con Spring Boot 3.5.7 y Java 21, siguiendo una arquitectura DDD (Domain-Driven Design).

## Arquitectura DDD

El proyecto aplica DDD de forma consistente con los siguientes bounded contexts:

### Bounded Contexts

1. **IAM (Identity and Access Management)**: Gestión de usuarios y autenticación
2. **Gestión de Mascotas**: Gestión completa de mascotas

### Estructura de Capas

Cada bounded context sigue la estructura DDD:

- **Domain** (`domain`): Entidades, agregados, comandos, queries y servicios de dominio
- **Application** (`application`): Implementación de servicios de aplicación (command services y query services)
- **Infrastructure** (`infrastructure`): Implementaciones concretas (repositorios JPA, servicios externos)
- **Interfaces** (`interfaces`): Controladores REST, recursos (DTOs) y transformadores (assemblers)

### Módulo Shared

El módulo `shared` contiene código compartido entre bounded contexts:
- Clases base para agregados auditables
- Configuraciones compartidas (seguridad, persistencia, documentación)

## Requisitos Previos

- Java 21 o superior
- Maven 3.6+ o superior
- MySQL 8.0+ o superior
- IDE (IntelliJ IDEA, Eclipse, VS Code) recomendado

## Configuración de la Base de Datos

1. Instala MySQL en tu sistema
2. Crea una base de datos llamada `dogimax_db` (o modifica la configuración en `application.properties`)
3. Configura las credenciales en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dogimax_db?createDatabaseIfNotExist=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

## Ejecución del Proyecto

### Opción 1: Usando Maven Wrapper (Recomendado)

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Opción 2: Usando Maven directamente

```bash
mvn spring-boot:run
```

### Opción 3: Compilar y ejecutar el JAR

```bash
# Compilar
mvn clean package

# Ejecutar
java -jar target/dogimax-api-0.0.1-SNAPSHOT.jar
```

### Opción 4: Ejecutar desde el IDE

1. Abre el proyecto en tu IDE
2. Localiza la clase `Application.java` en `src/main/java/com/dogimax/dogimaxapi/`
3. Ejecuta la clase como aplicación Java

## Configuración de Perfiles

El proyecto soporta perfiles de Spring Boot:

```bash
# Ejecutar con perfil de desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Documentación de la API

Una vez que la aplicación esté ejecutándose, puedes acceder a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/v3/api-docs

## Endpoints Disponibles

### Autenticación (Públicos)
- `POST /api/v1/authentication/sign-up` - Registrar usuario
- `POST /api/v1/authentication/sign-in` - Iniciar sesión

### Usuarios (Protegidos - Requieren autenticación)
- `GET /api/v1/users` - Obtener todos los usuarios
- `GET /api/v1/users/{userId}` - Obtener usuario por ID

### Mascotas (Protegidos - Requieren autenticación)
- `GET /api/v1/mascotas` - Obtener todas las mascotas
- `GET /api/v1/mascotas/{mascotaId}` - Obtener mascota por ID
- `GET /api/v1/mascotas/user/{userId}` - Obtener mascotas por usuario
- `POST /api/v1/mascotas` - Crear nueva mascota
- `PUT /api/v1/mascotas/{mascotaId}` - Actualizar mascota
- `DELETE /api/v1/mascotas/{mascotaId}` - Eliminar (desactivar) mascota

## Autenticación

Los endpoints protegidos requieren un token JWT en el header:

```
Authorization: Bearer <token>
```

Para obtener el token:
1. Registra un usuario en `/api/v1/authentication/sign-up`
2. Inicia sesión en `/api/v1/authentication/sign-in`
3. Usa el token retornado en las peticiones protegidas

## Estructura del Proyecto

```
src/main/java/com/dogimax/dogimaxapi/
├── Application.java                    # Clase principal
├── gestion_de_mascotas/               # Bounded Context: Gestión de Mascotas
│   ├── domain/                        # Capa de Dominio
│   │   ├── model/
│   │   │   ├── aggregates/           # Agregados (Mascota)
│   │   │   ├── commands/             # Comandos (CQRS)
│   │   │   └── queries/              # Queries (CQRS)
│   │   └── services/                 # Servicios de dominio
│   ├── application/                   # Capa de Aplicación
│   │   └── internal/
│   │       ├── commandservices/      # Servicios de comandos
│   │       └── queryservices/        # Servicios de queries
│   ├── infrastructure/                # Capa de Infraestructura
│   │   └── persistence/
│   │       └── jpa/
│   │           └── repositories/     # Repositorios JPA
│   └── interfaces/                    # Capa de Interfaces
│       └── rest/
│           ├── MascotasController.java
│           ├── resources/             # DTOs (Resources)
│           └── transform/             # Assemblers
├── iam/                               # Bounded Context: IAM
│   └── [misma estructura]
└── shared/                            # Módulo compartido
    ├── domain/                        # Entidades y agregados compartidos
    ├── infrastructure/                # Configuraciones compartidas
    └── interfaces/                    # Interfaces compartidas
```

## Verificación del Bounded Context de Gestión de Mascotas

El módulo de gestión de mascotas cumple con los principios DDD:

✅ **Separación de responsabilidades**: Cada capa tiene responsabilidades bien definidas
✅ **CQRS**: Separación clara entre comandos (escritura) y queries (lectura)
✅ **Agregados**: La entidad `Mascota` es un agregado raíz con lógica de dominio
✅ **Repositorios**: Abstracción del acceso a datos mediante interfaces de dominio
✅ **Servicios de aplicación**: Orquestan la lógica de aplicación sin depender de infraestructura
✅ **Independencia**: No tiene dependencias directas con otros bounded contexts

## Troubleshooting

### Error de conexión a la base de datos
- Verifica que MySQL esté ejecutándose
- Revisa las credenciales en `application.properties`
- Asegúrate de que la base de datos exista o que `createDatabaseIfNotExist=true` esté configurado

### Error de compilación
- Limpia y reconstruye el proyecto: `mvn clean install`
- Verifica que tengas Java 21 instalado: `java -version`

### Puerto 8080 ya en uso
- Cambia el puerto en `application.properties`: `server.port=8081`

## Tecnologías Utilizadas

- Spring Boot 3.5.7
- Spring Data JPA
- Spring Security
- MySQL
- JWT (JSON Web Tokens)
- OpenAPI/Swagger
- Lombok
- Maven

## Licencia

Este proyecto es privado.
