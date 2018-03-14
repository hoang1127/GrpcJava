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
      CommunicationServiceOuterClass.TransferDataRequest request =
      CommunicationServiceOuterClass.TransferDataRequest.newBuilder()
          .setName("Leader Node ")
          .build();

      // Finally, make the call using the stub
      CommunicationServiceOuterClass.TransferDataResponse response = 
        stub.communication(request);

      System.out.println(response);

      // A Channel is shutdown before stopping process.
      channel.shutdownNow();
    }
}
