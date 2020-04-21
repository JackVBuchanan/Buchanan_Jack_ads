### Compile using javac ###

- Go to src dir
- Compile: javac com/company/*.java
- Run: java com/company/Main

### Make executable Jar ###

- Go to src dir
- Compile with: javac com/company/*.java
- Make jar: jar cfm connect4.jar META-INF/MANIFEST.MF com/company/*.class
- Run: java -jar connect4.jar
