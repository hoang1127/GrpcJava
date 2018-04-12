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

      CommunicationServiceOuterClass.PingRequest pingRequest =
      CommunicationServiceOuterClass.PingRequest.newBuilder()
          .setMsg("Ping")
          .build();

      CommunicationServiceOuterClass.Request request =
      CommunicationServiceOuterClass.Request.newBuilder()
          .setPing(pingRequest)
          .build();
      //GrpcServiceGrpc.GrpcServiceBlockingStub stub = GrpcServiceGrpc.newBlockingStub(channel);

      //GrpcServiceOuterClass.PingRequest pingRequest =
      //GrpcServiceOuterClass.PutRequest.newBuilder()
      //    .setMsg("Ping")
      //    .build();

      //GrpcServiceOuterClass.Request request =
      //GrpcServiceOuterClass.Request.newBuilder()
      //    .setPingRequest(pingRequest)
      //    .build();
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
      CommunicationServiceOuterClass.Response response =
        stub.ping(request);
      //GrpcServiceOuterClass.Response response =
      //  stub.MessageHandler(request);

      System.out.println(response);

      // A Channel is shutdown before stopping process.
      channel.shutdownNow();
    }
}
