package com.example.grpc;

import io.grpc.*;

public class Client
{
    public static void main( String[] args ) throws Exception
    {
      // Set locolhost port 8080
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
        .usePlaintext(true)
        .build();

      CommunicationServiceGrpc.CommunicationServiceBlockingStub stub = CommunicationServiceGrpc.newBlockingStub(channel);
      //CommunicationServiceOuterClass.TransferDataRequest request =
      //CommunicationServiceOuterClass.TransferDataRequest.newBuilder()
      //    .setFromtimestamp("2018/01/01")
      //    .setTotimestamp("2018/01/07")
      //    .build();

      // TODO: Testing code for new .proto file
      //CommunicationServiceOuterClass.GetRequest request =
      //CommunicationServiceOuterClass.GetRequest.newBuilder()
      //    .setGetQuery("FROM 2018/01/01 TO 2018/01/07")
      //    .build();

      // TODO: Another test code for new proto file
      /*
      CommunicationServiceOuterClass.Ping ping =
      CommunicationServiceOuterClass.Ping.newBuilder()
          .setRequest(true)
          .build();

      CommunicationServiceOuterClass.Header request =
      CommunicationServiceOuterClass.Header.newBuilder()
          .setFromIp("Sender IP")
          .setToIp("Receiver IP")
          .setOriginalIp("Original IP")
          .setMaxHop(5)
          .setPing(ping)
          .setToken("1234567890")
          .build();
          */

      // Finally, make the call using the stub
      //CommunicationServiceOuterClass.TransferDataResponse response =
      //  stub.communication(request);
      //CommunicationServiceOuterClass.TransferDataResponse response =
        //stub.headerHandler(request);

      //System.out.println(response);

      // A Channel is shutdown before stopping process.
      //channel.shutdownNow();
    }
}
