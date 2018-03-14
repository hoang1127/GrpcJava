package com.example.grpc;

import io.grpc.stub.StreamObserver;

public class CommunicationServiceImpl extends CommunicationServiceGrpc.CommunicationServiceImplBase {
  @Override
  public void communication(CommunicationServiceOuterClass.TransferDataRequest request,
        StreamObserver<CommunicationServiceOuterClass.TransferDataResponse> responseObserver) {
  // CommunicationRequest has toString auto-generated.
    System.out.println(request);

    // You must use a builder to construct a new Protobuffer object
    CommunicationServiceOuterClass.TransferDataResponse response = CommunicationServiceOuterClass.TransferDataResponse.newBuilder()
      .setCommunication("Communication setup successfully, " + request.getName())
      .build();

    // Use responseObserver to send a single response back
    responseObserver.onNext(response);

    // Done. Call onCompleted.
    responseObserver.onCompleted();
  }
}
