### Compile using javac ###

- Go to src dir
- Compile: javac com/company/*.java
- Run: java com/company/Main

### Make new executable Jar ###

- Go to src dir
- Compile with: javac com/company/*.java
- Make jar: jar cfm connect4.jar META-INF/MANIFEST.MF com/company/*.class
- Run: java -jar connect4.jar

### Run existing executable Jar ###

- Go to executable dir
- Run: java -jar connect4.jar

### Source code location ###

- Project source code: Buchanan_Jack_ads/src/com/company/*
- Jar manifest file: Buchanan_Jack_ads/src/META-INF/MANIFEST.MF
- Properties file: Buchanan_Jack_ads/src/properties/config.properties
