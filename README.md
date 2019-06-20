# FindUrPlace App

## BackEnd


- Descargar e instalar Docker.
- Importar el proyecto en carpeta de preferencia con comando git clone + URL del repositorio.
- Renombrar src/main/resources/application.properties.dist a src/main/resources/application.properties.
- Modificar archivo application.properties con los parámetros y ApiKeys de Telegram, FourSquare, Mongo y configuración general.
- Acceder por línea de comando a carpeta donde se encuentra el archivo POM.xml: tp-tacs\back.
- Correr la aplicación con comando docker-compose up. Es necesario tener libres los puertos 8080 y 27017.

Nota: 
  Se lanzan 3 contenedores:
    - Mongo.
    - La api en sí, sin tests.
    - Los tests de integración de la api impactando contra el contenedor anterior.
