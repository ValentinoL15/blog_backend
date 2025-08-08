# Blogg_App

Blogg_App es una aplicación web full-stack diseñada para permitir a los usuarios crear, compartir y leer blogs. Está construida con un backend en **Java** utilizando **Spring Boot** y **Spring Security** con autenticación basada en **JWT** y un sistema de roles (Autor y Lector). El frontend está desarrollado con **Angular 19**, estilizado con **Bootstrap** y **Angular Material**, y la base de datos utiliza **MySQL**. La aplicación está desplegada en **Railway** para un acceso fácil y rápido.

## Características

- **Autenticación segura**: Implementa autenticación basada en JWT con roles de usuario (Autor y Lector).
- **Gestión de blogs**:
  - Los **Autores** pueden crear, editar y eliminar sus propios blogs.
  - Los **Lectores** pueden ver y leer los blogs compartidos por los autores.
- **Interfaz de usuario moderna**: Frontend responsivo y dinámico construido con Angular 19, Bootstrap y Angular Material.
- **Base de datos robusta**: Utiliza MySQL para almacenar usuarios, roles y contenido de los blogs.
- **Despliegue en la nube**: La aplicación está alojada en Railway para un acceso confiable.

## Tecnologías utilizadas

- **Backend**:
  - Java
  - Spring Boot
  - Spring Security (con autenticación JWT)
- **Frontend**:
  - Angular 19
  - Bootstrap
  - Angular Material
- **Base de datos**:
  - MySQL
- **Despliegue**:
  - Railway

## Acceder a la aplicación:

   - La aplicación está desplegada en **Railway** para un acceso en línea. Para acceder, visita el enlace proporcionado: https://blog-frontend-sooty-rho.vercel.app/login


## Uso

1. **Registro e inicio de sesión**:
   - Regístrate como Autor o Lector.
   - Inicia sesión para obtener un token JWT.
2. **Crear y gestionar blogs** (solo Autores):
   - Accede al panel de creación de blogs para escribir y publicar contenido.
   - Edita o elimina tus blogs desde el panel de administración.
3. **Leer blogs** (todos los usuarios):
   - Explora los blogs publicados por los Autores desde la página principal.


## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas contribuir:

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -m "Añade nueva funcionalidad"`).
4. Sube los cambios a tu fork (`git push origin feature/nueva-funcionalidad`).
5. Crea un Pull Request en el repositorio original.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT.

## Contacto

Para preguntas o sugerencias, contáctame en pichilongo1@gmail.com o abre un issue en el repositorio.
