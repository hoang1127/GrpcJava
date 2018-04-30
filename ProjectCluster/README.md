#CMPE275 Team Project

#This instruction has been test with MacBook Air, macOS High Sierra 

1. Install MySQL lastest (5.7)

2. Login MySQL as root user: 

        $ mysql -u root -p

3. Create a database called “FileDatabase” and create table ChunkData 

mysql> create database FileDatabase; 

        mysql> CREATE TABLE ChunkData (
                fileName varchar(50),
                chunkID int,
                data longblob,
                file_id varchar(50),
                totalChunks int,
                Primary Key(fileName, chunkID));

3. Install Google Protocol Buffer v3.2

4. Build protobuf files: 

        ./build_protobuf.sh

5. Compile all java code by Apache Ant: 

	ant build 

6. Run server : 

        Add the IP(s) in the node1/2/3/4.conf for each node

	./server.sh config/<node1/2/3/4.conf> 

        Ex. For node 2:   ./server.sh config/node2.conf 

7. Run client: 

	./client.sh <cluster name>

        Ex. For cluster 1: ./client.sh 1


8. Ping to other node in the network with IP:

        ping <node id>
        
9. Push a file from local directory to the cluster. All nodes from this cluster will save the file to their database.

        write <fileName>
        
10. Retrieve file from the database

        read <fileName> 
        
11. Exit the client

        quit


