# FindUrPlace App

## BackEnd


- Es necesario descargar e instalar un cliente MongoDB para la base de datos.
- Importar el proyecto en carpeta de preferencia con comando git clone + URL del repositorio.
- Copiar src/main/resources/application.properties.dist a src/main/resources/application.properties.
- Modificar archivo application.properties con los parámetros y ApiKeys de Telegram, FourSquare, Mongo y configuración general.
- Acceder por línea de comando a carpeta donde se encuentra el archivo POM.xml: tp-tacs\back.
- Finalmente para correr la aplicación, ejecutar comandos:
	mvn clean install
	mvn spring-boot:run
	
	
Old
- Importar el directorio con IntelliJ Idea `'/back'` 
- Configurar el proyecto como Maven
- Copiar `src/main/resources/application.properties.dist` a `src/main/resources/application.properties`
- Modificar archivo `application.properties` con los parámetros y ApiKeys de Telegram, FourSquare, Mongo y configuración general.
- Correr el proyecto (Puerto por defecto `'8080'`)
