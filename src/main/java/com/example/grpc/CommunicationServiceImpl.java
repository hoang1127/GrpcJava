package com.example.grpc;

import io.grpc.stub.StreamObserver;

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
    System.out.println("Finish getting the files");

    System.out.println("Sending back to client....");
    // TODO: Sending data back to client
    // You must use a builder to construct a new Protobuffer object
    CommunicationServiceOuterClass.TransferDataResponse response = CommunicationServiceOuterClass.TransferDataResponse.newBuilder()
      .setCommunication("Communication setup successfully, " + request.getFromtimestamp() + " " + request.getTotimestamp())
      .build();

    // Use responseObserver to send a single response back
    responseObserver.onNext(response);

    // Done. Call onCompleted.
    responseObserver.onCompleted();
  }
}
