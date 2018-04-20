package com.example.grpc;

import java.util.Iterator;

import com.example.grpc.CommunicationServiceOuterClass.Response;

import io.grpc.*;
import io.grpc.stub.StreamObserver;

/*
 * A client for testing Ping
 */
public class GetRequestClient
{
    public static void main( String[] args ) throws Exception
    {
      // Set locolhost port 8080
      
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
        .usePlaintext(true)
        .build();

      CommunicationServiceGrpc.CommunicationServiceBlockingStub stub = CommunicationServiceGrpc.newBlockingStub(channel);

      CommunicationServiceOuterClass.MetaData metaData =
      CommunicationServiceOuterClass.MetaData.newBuilder()
          .setUuid("????")
          .setNumOfFragment(1)
          .setMediaType(2)
          .build();
      
      CommunicationServiceOuterClass.QueryParams query =
      CommunicationServiceOuterClass.QueryParams.newBuilder()
      	  .setFromUtc("from this time")
      	  .setToUtc("to this time")
          .build();
      
      CommunicationServiceOuterClass.GetRequest getRequest =
      CommunicationServiceOuterClass.GetRequest.newBuilder()
          .setMetaData(metaData)
          .setQueryParams(query)
          .build();

      CommunicationServiceOuterClass.Request request =
      CommunicationServiceOuterClass.Request.newBuilder()
          .setFromSender("from sender")
          .setToReceiver("to receiver")
          .setOriginalSender("Original sender")
//          .setMaxHop(5)
          .setGetRequest(getRequest)
//          .setToken("1234567890")
          .build();


      // Finally, make the call using the stub
      Iterator<Response> response = stub.getHandler(request);
//      CommunicationServiceOuterClass.Header response =
//        stub.messageHandler(request);

      System.out.println("thiujeisurf: " + response.next());

      // A Channel is shutdown before stopping process.
      channel.shutdownNow();
      
    }
}
