package com.example.grpc;

import io.grpc.*;

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

      CommunicationServiceOuterClass.GetRequest getRequest =
      CommunicationServiceOuterClass.GetRequest.newBuilder()
          .setGetQuery("This is a test query")
          .build();

      CommunicationServiceOuterClass.Header request =
      CommunicationServiceOuterClass.Header.newBuilder()
          .setFromIp("Sender IP")
          .setToIp("Receiver IP")
          .setOriginalIp("Original IP")
          .setMaxHop(5)
          .setGetRequest(getRequest)
          .setToken("1234567890")
          .build();


      // Finally, make the call using the stub
      //CommunicationServiceOuterClass.TransferDataResponse response =
      //  stub.communication(request);
      CommunicationServiceOuterClass.Header response =
        stub.messageHandler(request);

      System.out.println(response);

      // A Channel is shutdown before stopping process.
      channel.shutdownNow();
    }
}
