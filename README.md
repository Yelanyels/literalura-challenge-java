# Literalura - Challenge Java 📚

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

## 📝 Descripción del Proyecto
Este es un catálogo de libros interactivo que consume la API de [Gutendex](https://gutendex.com/) para buscar información sobre obras literarias. El sistema permite almacenar libros y autores en una base de datos PostgreSQL, realizar filtros por idioma y consultar autores vivos en años específicos.

## 🚀 Funcionalidades
- **Búsqueda de libros por título**: Consulta datos directamente desde la API externa.
- **Persistencia de datos**: Guarda libros y autores vinculados en la base de datos local.
- **Listados inteligentes**:
    - Listar todos los libros registrados.
    - Listar autores registrados.
    - Filtrar autores vivos por año (usando Derived Queries).
    - Listar libros por idioma con estadísticas de conteo.

## 🛠️ Tecnologías utilizadas
- **Java 17+**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Jackson (Librería para JSON)**

## ⚙️ Configuración
1. Clonar el repositorio.
2. Configurar las credenciales de PostgreSQL en el archivo `application.properties`.
3. Ejecutar la aplicación mediante `LiteraturaApplication.java`.