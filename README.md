# FindUrPlace App

## BackEnd


- Es necesario descargar e instalar un cliente MongoDB para la base de datos.
- Importar el proyecto en carpeta de preferencia con comando git clone + URL del repositorio.
- Copiar src/main/resources/application.properties.dist a src/main/resources/application.properties.
- Modificar archivo application.properties con los parámetros y ApiKeys de Telegram, FourSquare, Mongo y configuración general.
- Acceder por línea de comando a carpeta donde se encuentra el archivo POM.xml: tp-tacs\back.
- Parar correr la aplicación con tests es necesario tener levantando el servidor previamente, para lo cual, ejecutar comandos:
	mvn clean install -Dmaven.test.skip=true
	mvn spring-boot:run -Dmaven.test.skip=true
	
