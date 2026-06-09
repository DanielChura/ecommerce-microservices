# Plan: Mejoras de Logging

## 1. application.yml — Logger name más legible

**Archivo:** `microservices/customer-microservice/src/main/resources/application.yml`

Cambiar `%logger{20}` → `%logger{36}` en ambas líneas (console y file):

```yaml
    pattern:
        console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
        file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
```

---

## 2. CustomerService.java — Logging de operaciones

**Archivo:** `microservices/customer-microservice/src/main/java/com/daniel/microservices/customer_microservice/customer/CustomerService.java`

### 2a. Agregar imports y anotación

Agregar `import lombok.extern.slf4j.Slf4j;` y `@Slf4j`:

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
```

### 2b. Log al crear customer

Cambiar:
```java
        var customer = customerMapper.toCustomer(request);
        return customerRepository.save(customer).getId();
```
A:
```java
        var customer = customerMapper.toCustomer(request);
        var savedId = customerRepository.save(customer).getId();
        log.info("Customer created: {}", savedId);
        return savedId;
```

### 2c. Log al actualizar customer

Cambiar:
```java
            customerMapper.updateCustomer(request, customer);
            return customerRepository.save(customer).getId();
```
A:
```java
            customerMapper.updateCustomer(request, customer);
            var updatedId = customerRepository.save(customer).getId();
            log.info("Customer updated: {}", updatedId);
            return updatedId;
```

### 2d. Log al eliminar customer

Cambiar:
```java
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
```
A:
```java
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
        log.info("Customer deleted: {}", id);
```

---

## 3. GlobalExceptionHandler.java — Incluir URI en el error

**Archivo:** `microservices/common-exceptions/src/main/java/com/daniel/common_exceptions/GlobalExceptionHandler.java`

Cambiar línea:
```java
log.error("Error: {}", exception.getMessage());
```
A:
```java
log.error("Unexpected error on {}: {}", request.getRequestURI(), exception.getMessage());
```

---

## Resultado esperado

Después de los cambios, los logs se verán así:

```
2026-06-09 15:00:00 [main] INFO  c.d.m.c.c.CustomerService           - Customer created: 507f1f77bcf86cd799439011
2026-06-09 15:00:05 [http-nio-8091-exec-2] WARN  c.d.m.e.CustomerExceptionHandler - Customer not found: Customer not found with id: 6a27c83157ab6b030eca150a
2026-06-09 15:00:10 [http-nio-8091-exec-3] ERROR c.d.c.GlobalExceptionHandler     - Unexpected error on /api/v1/customers/123: Something went wrong
```

- **INFO**: Solo operaciones de negocio (crear, actualizar, eliminar), sin stack traces
- **WARN**: Errores esperados (404, validación), mensaje limpio sin stack trace
- **ERROR**: Errores inesperados, con la URI del request para facilitar debugging
