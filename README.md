GRPC JAVA

//Create Java Project with Maven
$ mvn archetype:generate -DgroupId=com.example.grpc \
 -DartifactId=grpc-java-server \
 -DarchetypeArtifactId=maven-archetype-quickstart \
 -DinteractiveMode=false
$ cd grpc-java-server

$ mvn package

// Run server
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.App

// Run Clien
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.Client

