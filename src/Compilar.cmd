set path=%path%;C:\Program Files\Java\jdk1.8.0_40\bin
REM set path=%path%;C:\Program Files\Java\jdk1.7.0_79\bin
javac Model/*.java Controller/*java
jar cvf Libreria.jar Model/*.class Controller/*.class
javac -cp Libreria.jar View/*.java
jar cvfm Juego_Cuy.jar MANIFEST.MF View/*.class *.txt