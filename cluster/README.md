Updated 3/19

HOW TO RUN:

1/ Install + run mongodb on default port

2/ Either create mongo data manually or run the code in MongoTest.java, this
code will create 2 test documents:

{ "_id" : { "$oid" : "5aab742ca9fec20449442856"} , "date" : { "$date" : "2018-01-01T08:00:00.000Z"} , "data" : "This is data for 01/01/2018"}

{ "_id" : { "$oid" : "5aab742ca9fec20449442857"} , "date" : { "$date" : "2018-01-02T08:00:00.000Z"} , "data" : "This is data for 01/02/2018"}

$ mvn package exec:java -Dexec.mainClass=com.example.database.MongoTest -Dexec.cleanupDaemonThreads=false

3/ Run Grpc Server :
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.App

4/ Run Grpc Client:
There are 3 client flavor for you to test
- Generic testing client
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.Client
- Ping client to test Ping
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.PingClient
- GetRequest client to test Get data request
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.GetRequestClient
- PutRequest client to test Get data request
$ mvn package exec:java -Dexec.mainClass=com.example.grpc.PutRequestClient

5/ Client should receive those 2 documents from server:
communication: "Communication setup successfully, 2018/01/01 2018/01/07"data: 

"{ \"_id\" : { \"$oid\" : \"5aab742ca9fec20449442856\"} , \"date\" : { \"$date\" : \"2018-01-01T08:00:00.000Z\"} , \"data\" : \"This is data for 01/01/2018\"}

{ \"_id\" : { \"$oid\" : \"5aab742ca9fec20449442857\"} , \"date\" : { \"$date\" : \"2018-01-02T08:00:00.000Z\"} , \"data\" : \"This is data for 01/02/2018\"}"

6/Test MongoDB

$ mvn package exec:java -Dexec.mainClass=com.example.database.MongoMain -Dexec.cleanupDaemonThreads=false

Enter from Keyboard: i: Insert ; c: Create ; d: Delete; u: Update x: Exit
---------------------------------------------------------

TODO LIST:
- Update proto file with the class one (in Canvas), and update the code to send/receive
using this proto
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
