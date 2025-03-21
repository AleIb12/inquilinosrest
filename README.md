# 🔒 API de Control de Cerraduras Inteligentes

## 📋 Descripción
Este servicio REST proporciona una integración segura con la API de We-Lock para controlar cerraduras inteligentes. El sistema permite a usuarios autenticados abrir cerraduras remotamente mediante peticiones HTTP seguras.

## ✨ Características
- 🔐 Autenticación segura mediante JWT
- 🔑 Control remoto de cerraduras inteligentes
- 🛡️ Seguridad avanzada contra peticiones falsas o malintencionadas
- 📝 Registro detallado de operaciones
- 🌐 Integración con la API externa de We-Lock

## 🛠️ Tecnologías utilizadas
- ☕ Java 24
- 🍃 Spring Boot 3.4
- 🔒 Spring Security
- 🔖 JWT para autenticación
- 📦 Lombok para reducir código boilerplate
- 🧪 Entorno de pruebas integrado

## 🚀 Instalación y configuración

### Requisitos previos
- Java JDK 24 o superior
- Maven 3.6 o superior
- Credenciales de acceso a la API de We-Lock

### Pasos de instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuusuario/control-cerraduras.git
   cd control-cerraduras
   ```

2. Configurar las propiedades de la aplicación en `src/main/resources/application.properties`:
   ```properties
   # Configuración para API We-Lock
   welock.api.baseUrl=https://document.we-lock.com
   welock.api.clientId=your-client-id
   welock.api.clientSecret=your-client-secret
   
   # Configuración de seguridad
   security.jwt.secret=your-secret-key
   security.jwt.expiration=3600000
   ```

3. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

4. Ejecutar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

## 📌 Uso del API

### 🔑 Autenticación

Para obtener un token JWT, realiza una petición POST al endpoint de autenticación:

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
```

Respuesta exitosa:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin"
}
```

### 🚪 Abrir Cerradura

Para abrir una cerradura, realiza una petición POST incluyendo el token JWT obtenido:

```http
POST /api/lock/open
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "lockId": "id-de-la-cerradura"
}
```

Respuesta exitosa:
```json
{
  "success": true,
  "message": "Cerradura abierta correctamente",
  "timestamp": "2025-03-21T15:30:45.123Z"
}
```

## 🔐 Seguridad

El sistema implementa varias capas de seguridad:
- 🔒 Autenticación mediante JWT
- 🛡️ Validación de tokens en cada petición
- ✅ Verificación de datos obligatorios
- 📝 Registro de usuario y timestamp en cada operación
- 🔍 Control de errores exhaustivo

## 👨‍💻 Desarrollo

### Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── es/
│   │       └── mycityhome/
│   │           └── inquilinos/
│   │               ├── config/        # Configuraciones
│   │               ├── controller/    # Controladores REST
│   │               ├── dto/           # Objetos de transferencia
│   │               ├── security/      # Componentes de seguridad
│   │               └── service/       # Servicios
│   └── resources/
│       └── application.properties     # Propiedades
└── test/                              # Tests
```

## 📄 Licencia
Este proyecto está licenciado bajo la Licencia MIT - ver el archivo LICENSE para más detalles.

## 📚 Recursos adicionales
- [Documentación de We-Lock API](https://document.we-lock.com)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT](https://jwt.io/)