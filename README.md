# SA-HA1

Aplicación backend desarrollada con **Spring Boot** que modela un sistema básico de comercio electrónico. Incluye gestión de usuarios, direcciones, categorías, productos, pedidos y colecciones, utilizando **PostgreSQL** como base de datos.

---

## 🚀 Tecnologías

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Maven

---

## 📂 Estructura general

* `Application.java`: punto de entrada de la aplicación Spring Boot.
* Scripts SQL para:

  * Creación de tablas
  * Inserción de datos iniciales
* Configuración mediante `application.properties`.

---

## 📘 Documentación de la API (Swagger)

Este proyecto expone su documentación interactiva mediante **Swagger UI**, lo que permite explorar, probar y entender todos los endpoints disponibles de forma visual.

### 🔗 Acceso a Swagger UI

Una vez que la aplicación esté en ejecución, accede a la documentación desde tu navegador en la siguiente URL:

👉 **https://springboot-sa-ha1.onrender.com/swagger-ui/index.html#/**

### 🚀 ¿Qué puedes hacer en Swagger?

- Ver todos los endpoints organizados por feature (auth, customers, products, orders, etc.).
- Revisar los modelos de datos (schemas) utilizados en requests y responses.
- Probar las APIs directamente desde el navegador.
- Validar contratos OpenAPI definidos en los archivos `.yaml`.

> 💡 **Nota:** Asegúrate de que la aplicación esté corriendo (`mvn spring-boot:run`) antes de acceder a Swagger.


## 🗄️ Modelo de datos

El sistema considera las siguientes entidades principales:

* **Usuarios**: información básica del usuario.
* **Direcciones**: relación 1 a 1 con usuarios.
* **Categorías**: clasificación de productos.
* **Productos**: artículos disponibles en el sistema.
* **Pedidos**: compras realizadas por los usuarios.
* **Colecciones**: agrupaciones de productos (muchos a muchos).

---

## 🖼️ Diagrama de la base de datos

[![temporal-pi-public.png](https://i.postimg.cc/qvVhqCQk/temporal-pi-public.png)](https://postimg.cc/zV7XPv9M)

---

## ⚙️ Configuración

La aplicación utiliza una base de datos PostgreSQL externa configurada en `application.properties`.

* `spring.jpa.hibernate.ddl-auto=none`
* Inicialización automática de scripts SQL habilitada.

---

## ▶️ Ejecución

1. Clona el repositorio
2. Configura las credenciales de la base de datos
3. Ejecuta la aplicación:

```bash
mvn spring-boot:run
```

---

## 📌 Notas

* El esquema de la base de datos debe existir antes de iniciar la aplicación.
* Los datos iniciales se cargan automáticamente al arrancar el proyecto.

---

## 👥 Integrantes

- Tiago Alcázar
- Brahim González
- Francis Chávez
- Diego Villagrán
- Belén Almendros
- Héctor Chacón