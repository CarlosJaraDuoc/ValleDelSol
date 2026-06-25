Valle del Sol: Plataforma Inteligente para la Gestión y Prevención de Incendios
Este proyecto consiste en una solución Fullstack basada en microservicios diseñada para la Municipalidad de Valle del Sol. Su objetivo es permitir el reporte ciudadano de incendios, la visualización de focos en tiempo real y la emisión de alertas comunitarias.
Arquitectura del Sistema: La solución utiliza una arquitectura de microservicios donde cada componente es independiente y se comunica vía HTTP REST, utilizando un API Gateway como único punto de entrada.

API Gateway (8080): Punto de entrada único que centraliza la seguridad y redirige el tráfico externo hacia el servicio correcto según el path.

Microservicio de Reportes (8081): Encargado de la gestión del CRUD de incendios, almacenando descripciones, estados y coordenadas geográficas.

Microservicio de Alertas (8082): Servicio crítico para notificaciones comunitarias que implementa un patrón Circuit Breaker manual para garantizar disponibilidad.

Backend For Frontend - BFF (8083): Actúa como agregador de datos (Dashboard) para optimizar la respuesta al cliente, reduciendo la latencia de red.

Microservicio de Mapas (8084): Gestiona el monitoreo geográfico de focos activos y la delimitación de zonas de evacuación o brigadas.

Microservicio de Usuarios (8085): Responsable de la seguridad, autenticación básica y gestión de roles (Ciudadano, Brigadista, Admin).

Frontend (3000): Interfaz de usuario desarrollada en React para el reporte de incidentes y visualización en tiempo real de la situación.

Tecnologías Utilizadas:

    Backend: Java 17, Spring Boot, Spring Data JPA.
    Frontend: React (Vite), Axios.
    Persistencia: Base de datos H2 en memoria para desarrollo.
    Pruebas: JUnit 5, Mockito (Backend) y Jest (Frontend).
    Métricas: JaCoCo para cobertura de código.

Estructura del Repositorio
El proyecto se organiza de la siguiente manera:

    api-gateway/: Configuración de rutas.
    bff-service/: Lógica de agregación para el dashboard.
    reportes-service/: Microservicio principal de persistencia de incendios.
    alertas-service/: Microservicio crítico con resiliencia.
    mapas-service/ y usuarios-service/: Servicios de apoyo.
    frontend/: Código fuente de la aplicación React.

Instrucciones de Ejecución
Para levantar el ecosistema completo, se debe seguir este orden de arranque:

    Microservicios de Datos: Iniciar Reportes, Alertas, Mapas y Usuarios.
    Agregador: Iniciar el BFF Service.
    Puerta de Enlace: Iniciar el API Gateway.
    Cliente: Ejecutar el Frontend (npm run dev).

Pruebas Unitarias y Calidad
El proyecto cumple con una cobertura mínima del 60%.

    Para ejecutar pruebas y generar reportes JaCoCo en cada microservicio: mvn test.
    Para ver el reporte de cobertura: abrir target/site/jacoco/index.html en el navegador.
    Se incluye una prueba de Circuit Breaker en el servicio de Alertas para garantizar que el sistema no colapse si la base de datos falla.

Patrones de Diseño Aplicados:

    Repository Pattern: Para separar la lógica de persistencia.
    Circuit Breaker: Implementado en AlertaService para tolerancia a fallos.
    BFF (Backend For Frontend): Para reducir la latencia en el cliente.
    API Gateway: Para centralizar la seguridad y el enrutamiento
