# reservas-hotel-backend

REST API para el sistema de gestión de habitaciones y reservas de hotel. Proyecto de evaluación final — MitoCode.

## Stack

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 25 |
| Framework | Spring Boot 4.0.6 |
| Build | Maven (mvnw wrapper) |
| Base de datos | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Validación | Bean Validation (jakarta) |
| Utilidades | Lombok |

## Estructura del proyecto

```
src/main/java/com/eval/
├── config/
│   └── WebConfig.java          # CORS para http://localhost:4200
├── controller/
│   ├── RoomController.java     # GET /rooms, POST /rooms, PUT /rooms/{id}
│   └── ReservationController.java # GET /reservations, POST /reservations
├── exception/
│   └── GlobalExceptionHandler.java # Manejo global de errores con mensajes en español
├── model/
│   ├── Room.java               # Entidad: id, number, type, price, available
│   └── Reservation.java        # Entidad: id, customerName, checkInDate, checkOutDate, room (ManyToOne)
├── repository/
│   ├── RoomRepository.java
│   └── ReservationRepository.java
└── service/
    ├── RoomService.java
    └── ReservationService.java
```

## API Endpoints

### Habitaciones — `/rooms`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/rooms` | Lista todas las habitaciones |
| `POST` | `/rooms` | Crea una nueva habitación |
| `PUT` | `/rooms/{id}` | Actualiza disponibilidad (`{ "available": true/false }`) |

### Reservas — `/reservations`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/reservations` | Lista todas las reservas |
| `POST` | `/reservations` | Crea una nueva reserva |

#### Ejemplo — crear reserva

```json
POST /reservations
{
  "customerName": "Juan Pérez",
  "checkInDate": "2026-07-01",
  "checkOutDate": "2026-07-05",
  "room": { "id": 1 }
}
```

## Configuración

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/reservas_db
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

## Levantar el proyecto

```bash
./mvnw spring-boot:run
```

El servidor queda disponible en `http://localhost:8080`.

## Tests

```bash
./mvnw test
```

## Decisiones técnicas

- **CORS** configurado vía `WebConfig.java` para permitir el frontend en `localhost:4200`.
- **`PUT /rooms/{id}`** recibe `@RequestBody Map<String, Boolean>` (no `@RequestParam`) para respetar la convención REST.
- **`Reservation`** referencia a `Room` como entidad anidada (`{ "room": { "id": N } }`), no como ID suelto.
- **Fechas** serializadas con `@JsonFormat(pattern = "yyyy-MM-dd")`.
