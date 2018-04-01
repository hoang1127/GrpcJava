package com.example.grpc;

import io.grpc.stub.StreamObserver;
import io.grpc.*;
import com.google.protobuf.ByteString;

// TODO: should create new class to handle db and remove all these import
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCursor;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CommunicationServiceImpl extends CommunicationServiceGrpc.CommunicationServiceImplBase {
  @Override
  public void communication(CommunicationServiceOuterClass.TransferDataRequest request,
        StreamObserver<CommunicationServiceOuterClass.TransferDataResponse> responseObserver) {
  // CommunicationRequest has toString auto-generated.
    System.out.println(request);

    // Execute the request
    System.out.println("Receive request to get data from " + request.getFromtimestamp() + " to " +
                      request.getTotimestamp());

    System.out.println("Go to database to get all the requested file.....");

    // TODO: Database code here
    // TODO: currently, data return is a string representation of mongo json object
    // TODO: need to convert this to either raw data or other kind of data
    // TODO: depends on the requirement
    // Should create new class instead of putting everything here
    MongoClient mgClient = new MongoClient("localhost", 27017);
    DB db = mgClient.getDB("cmpe275");
    System.out.println("Connected to database successfully");

    // Now get the date from the request
    Date from = new Date(request.getFromtimestamp());
    Date to = new Date(request.getTotimestamp());
    QueryBuilder qb = new QueryBuilder();
    qb.put("date").greaterThanEquals(from).lessThanEquals(to);
    BasicDBObject bdo = new BasicDBObject();
    DBCollection findCollection = db.getCollection("project1");
    DBCursor find = findCollection.find(bdo);
    System.out.println("Number of document found = " + find.count());
    String responseData = "";
    while(find.hasNext()) {
        String data = find.next().toString();
        System.out.println(data);
        responseData += data;
    }


    System.out.println("Finish getting the files");

    System.out.println("Sending data back to client....");
    // TODO: Sending data back to client
    // You must use a builder to construct a new Protobuffer object
    CommunicationServiceOuterClass.TransferDataResponse response = CommunicationServiceOuterClass.TransferDataResponse.newBuilder()
      .setCommunication("Communication setup successfully, " + request.getFromtimestamp() + " " + request.getTotimestamp())
      .setData(responseData)
      .build();

    // Use responseObserver to send a single response back
    responseObserver.onNext(response);

    // Done. Call onCompleted.
    responseObserver.onCompleted();
  }



    private CommunicationServiceOuterClass.Header handlePing (
      CommunicationServiceOuterClass.Header request) {

        // Building Header response
        System.out.println("Building response....");
        String fromIp = request.getToIp();
        String toIp = request.getFromIp();
        String originalIp = request.getOriginalIp();
        int maxHop = request.getMaxHop();
        String token = request.getToken();

        CommunicationServiceOuterClass.Ping ping =
        CommunicationServiceOuterClass.Ping.newBuilder()
            .setRespond(true)
            .build();

        System.out.println("Sending back response to client....");
        CommunicationServiceOuterClass.Header response = CommunicationServiceOuterClass.Header.newBuilder()
            .setFromIp(fromIp)
            .setToIp(toIp)
            .setOriginalIp(originalIp)
            .setMaxHop(maxHop)
            .setPing(ping)
            .setToken(token)
            .build();

        return response;
    }

    private CommunicationServiceOuterClass.Header handleGetRequest (
      CommunicationServiceOuterClass.Header request) {

        // Building Header response
        System.out.println("Building response....");
        String fromIp = request.getToIp();
        String toIp = request.getFromIp();
        String originalIp = request.getOriginalIp();
        int maxHop = request.getMaxHop();
        String token = request.getToken();
        CommunicationServiceOuterClass.GetRequest getRequest = request.getGetRequest();
        String query = getRequest.getGetQuery();

        // TODO: For now, only get data from leader node,
        // as each of our nodes should contain all the data already
        // Connect to database and get some data from it
        System.out.println("Getting data....");
        MongoClient mgClient = new MongoClient("localhost", 27017);
        DB db = mgClient.getDB("cmpe275");
        System.out.println("Connected to database successfully");

        // Now get the date from the request
        Date from = new Date("2018/01/01");
        Date to = new Date("2018/01/07");
        QueryBuilder qb = new QueryBuilder();
        qb.put("date").greaterThanEquals(from).lessThanEquals(to);
        BasicDBObject bdo = new BasicDBObject();
        DBCollection findCollection = db.getCollection("project1");
        DBCursor find = findCollection.find(bdo);
        System.out.println("Number of document found = " + find.count());
        String responseData = "";
        while(find.hasNext()) {
            String data = find.next().toString();
            System.out.println(data);
            responseData += data;
        }

        // Convert data to proto format
        byte b[] = new byte[1024];
        b = responseData.getBytes();
        ByteString bt = ByteString.copyFrom(b);

        CommunicationServiceOuterClass.DatFragment datFragment =
        CommunicationServiceOuterClass.DatFragment.newBuilder()
            .setDataId(1)
            .setFragmentId(1)
            .setData(bt)
            .build();
        CommunicationServiceOuterClass.Data data =
        CommunicationServiceOuterClass.Data.newBuilder()
            .setDatFragment(datFragment)
            .build();

        System.out.println("Sending back response to client....");
        CommunicationServiceOuterClass.Header response = CommunicationServiceOuterClass.Header.newBuilder()
            .setFromIp(fromIp)
            .setToIp(toIp)
            .setOriginalIp(originalIp)
            .setMaxHop(maxHop)
            .setData(data)
            .setToken(token)
            .build();

        return response;
    }

    /*
     * Handle Put Request
     */
    private CommunicationServiceOuterClass.Header handlePutRequest (
      CommunicationServiceOuterClass.Header request) {

        // Building Header response
        System.out.println("Building response....");
        String fromIp = request.getToIp();
        String toIp = request.getFromIp();
        String originalIp = request.getOriginalIp();
        int maxHop = request.getMaxHop();
        String token = request.getToken();

        // TODO: put request stuff, need to be discussed with other team
        CommunicationServiceOuterClass.PutRequest putRequest = request.getPutRequest();
        CommunicationServiceOuterClass.Data putData = putRequest.getData();
        Date date = new Date(putData.getDate());

        // Connect to database and put some data from it
        MongoClient mgClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mgClient.getDatabase("cmpe275");
        System.out.println("Connected to database successfully");

        System.out.println("Insert data to the database...");
        MongoCollection<Document> collection = db.getCollection("project1");
        //DBCollection collection = db.getCollection("project1");
        Document doc = new Document("date", date)
                    .append("data", "This is a test data");
        collection.insertOne(doc);

        // TODO: replicate data to client node in the cluster
        // 1. Connect to each client via IPs + port (shell script or socket)
        // 2. Send the same put data to it
        // 3. Make sure we get response back from each Client
        // 4. go to next step

        // Build response and send back to requetor
        System.out.println("Sending back response to client....");
        CommunicationServiceOuterClass.Header response = CommunicationServiceOuterClass.Header.newBuilder()
            .setFromIp(fromIp)
            .setToIp(toIp)
            .setOriginalIp(originalIp)
            .setMaxHop(maxHop)
            .setErrorCode(200)
            .setToken(token)
            .build();

        return response;
    }

    @Override
    public void pingHandler(CommunicationServiceOuterClass.Ping request,
          StreamObserver<CommunicationServiceOuterClass.Ping> responseObserver) {
            System.out.println(request);

            System.out.println("Receive a ping request");

            //CommunicationServiceOuterClass.Ping response = null;

            CommunicationServiceOuterClass.Ping response =
            CommunicationServiceOuterClass.Ping.newBuilder()
                .setRespond(true)
                .build();

            // Use responseObserver to send a single response back
            responseObserver.onNext(response);

            // Done. Call onCompleted.
            responseObserver.onCompleted();
            System.out.println("Response sended!!!");
    }
    // TODO: the main method, can safely ignoe other similar handler
    /*
     * Message Handler function
     */
     @Override
     public void messageHandler(CommunicationServiceOuterClass.Header request,
           StreamObserver<CommunicationServiceOuterClass.Header> responseObserver) {
     // CommunicationRequest has toString auto-generated.
       System.out.println(request);

       System.out.println("Receive a header request");

       CommunicationServiceOuterClass.Header response = null;
       if (request.hasPing()) {
           System.out.println("Receive a Ping request");
           response = handlePing(request);
       } else if (request.hasGetRequest()) {
           System.out.println("Receive a GetRequest request");
           response = handleGetRequest(request);
       } else if (request.hasPutRequest()) {
           System.out.println("Receive a PutRequest request");
           response = handlePutRequest(request);
       } else {
           System.out.println("Unrecognized request type");
       }

       // Use responseObserver to send a single response back
       responseObserver.onNext(response);

       // Done. Call onCompleted.
       responseObserver.onCompleted();
       System.out.println("Response sended!!!");
     }
}
