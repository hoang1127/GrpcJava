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
      final ManagedChannel channel = ManagedChannelBuilder.forTarget("169.254.90.246:3000")
        .usePlaintext(true)
        .build();

      CommunicationServiceGrpc.CommunicationServiceBlockingStub stub = CommunicationServiceGrpc.newBlockingStub(channel);

      CommunicationServiceOuterClass.Ping request =
      CommunicationServiceOuterClass.Ping.newBuilder()
          .setRequest(true)
          .build();

      /*
      CommunicationServiceOuterClass.Header request =
      CommunicationServiceOuterClass.Header.newBuilder()
          .setFromIp("169.254.99.162")
          .setToIp("162.254.19.43")
          .setOriginalIp("169.254.99.162")
          .setMaxHop(5)
          .setPing(ping)
          .setToken("1234567890")
          .build();
          */

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
