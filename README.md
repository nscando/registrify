# Registrify

## Descripción

Registrify es una aplicación JavaFX de código abierto que simplifica el proceso de registro y autenticación de usuarios. La aplicación utiliza una interfaz de usuario moderna y atractiva, proporcionando una experiencia de usuario fluida y fácil de usar. Al combinar las bibliotecas de JavaFX y el soporte de bases de datos flexibles, Registrify ofrece una solución sólida y escalable para desarrolladores que buscan implementar sistemas de registro y autenticación en sus aplicaciones.

## Características principales

- Interfaz de usuario atractiva y moderna basada en JavaFX
- Soporte para múltiples bases de datos para almacenar y gestionar información de usuarios
- Mecanismos de seguridad para proteger la información de los usuarios
- Estructura de código modular y fácil de entender para facilitar la personalización
- Documentación detallada para guiar a los desarrolladores a través del proceso de implementación

## Requisitos

- Java 11 o superior
- JavaFX 11 o superior
- Maven

## Instalación

1. Clonar el repositorio:

git clone https://github.com/yourusername/registrify.git


2. Cambiar al directorio del proyecto:

   cd registrify


3. Compilar y empaquetar el proyecto con Maven:

mvn clean package


4. Ejecutar la aplicación:

java --module-path path/to/javafx-sdk-11/lib --add-modules javafx.controls,javafx.fxml -jar target/registrify-1.0-SNAPSHOT.jar


Asegúrese de reemplazar `path/to/javafx-sdk-11/lib` con la ruta correcta a la carpeta `lib` de su instalación de JavaFX.

## Documentación

Para obtener información detallada sobre cómo utilizar Registrify y personalizarlo para sus necesidades, consulte la [documentación](./docs).

## Contribuir

Las contribuciones son bienvenidas. Siéntase libre de abrir un problema o enviar una solicitud de extracción (pull request) para mejorar o agregar nuevas funcionalidades a Registrify.

## Licencia

Registrify está licenciado bajo la licencia MIT. Consulte el archivo [LICENSE](./LICENSE) para obtener más información.

Recuerde reemplazar yourusername en la URL del repositorio (https://github.com/yourusername/registrify.git) con su nombre de usuario de GitHub.