# Dev configuration
1. Copy installer/jfxrt.jar in jdk8/jre/lib/ext.
2. Change in pom.xml jdk property to your path installation.
3. Copy ./resource/database to up folder.
4. Run mvn clean package
5. Copy the file ./target/LaBruzezaCajaCliente.exe to <INSTALLATION_FOLDER> with jre and database folder
6. Execute exe file.