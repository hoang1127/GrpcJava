package com.example.grpc;

import io.grpc.stub.StreamObserver;

// TODO: should create new class to handle db and remove all these import
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCursor;
import com.mongodb.BasicDBObject;
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
}
