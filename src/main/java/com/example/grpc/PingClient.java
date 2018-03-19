package com.example.grpc;

import io.grpc.*;

/*
 * A client for testing Ping
 */
public class PingClient
{
    public static void main( String[] args ) throws Exception
    {
      // Set locolhost port 8080
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
        .usePlaintext(true)
        .build();

      CommunicationServiceGrpc.CommunicationServiceBlockingStub stub = CommunicationServiceGrpc.newBlockingStub(channel);

      // TODO: Testing code for new .proto file
      CommunicationServiceOuterClass.Ping request =
      CommunicationServiceOuterClass.Ping.newBuilder()
          .setRequest(true)
          .build();

      // Finally, make the call using the stub
      //CommunicationServiceOuterClass.TransferDataResponse response =
      //  stub.communication(request);
      CommunicationServiceOuterClass.Ping response =
        stub.pingHandler(request);

      System.out.println(response);

      // A Channel is shutdown before stopping process.
      channel.shutdownNow();
    }
}
