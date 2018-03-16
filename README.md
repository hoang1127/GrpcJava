HOW TO RUN:

1/ Install + run mongodb on default port
2/ Either create mongo data manually or run the code in MongoTest.java, this
code will create 2 test documents:
{ "_id" : { "$oid" : "5aab742ca9fec20449442856"} , "date" : { "$date" : "2018-01-01T08:00:00.000Z"} , "data" : "This is data for 01/01/2018"}
{ "_id" : { "$oid" : "5aab742ca9fec20449442857"} , "date" : { "$date" : "2018-01-02T08:00:00.000Z"} , "data" : "This is data for 01/02/2018"}

$ mvn package exec:java -Dexec.mainClass=com.example.database.MongoTest

3/ Run Grpc Server :
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.App

4/ Run Grpc Client:
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.Client

5/ Client should receive those 2 documents from server:
communication: "Communication setup successfully, 2018/01/01 2018/01/07"
data: "{ \"_id\" : { \"$oid\" : \"5aab742ca9fec20449442856\"} , \"date\" : { \"$date\" : \"2018-01-01T08:00:00.000Z\"} , \"data\" : \"This is data for 01/01/2018\"}{ \"_id\" : { \"$oid\" : \"5aab742ca9fec20449442857\"} , \"date\" : { \"$date\" : \"2018-01-02T08:00:00.000Z\"} , \"data\" : \"This is data for 01/02/2018\"}"

---------------------------------------------------------

TODO LIST:
- Right now, the return result is json string format, need to get the format from
class and convert it to them
- Use and save real data
- Host and port now is hard coded, should make it flexible
- Write classes to handle database operation (can be done after pre-intergration)
- .... and a lot more

---------------------------------------------------------
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
