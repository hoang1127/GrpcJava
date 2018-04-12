package com.example.grpc;

import io.grpc.*;
import java.io.File;
import com.google.protobuf.ByteString;
import java.util.Date;
import java.io.RandomAccessFile;

/*
 * A client for testing Put Request
 */
public class PutRequestClient
{
    public static void main( String[] args ) throws Exception
    {
      // Set locolhost port 8080
      //final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
      //  .usePlaintext(true)
      //  .build();

      //CommunicationServiceGrpc.CommunicationServiceBlockingStub stub = CommunicationServiceGrpc.newBlockingStub(channel);

      // Open a test file
      /*
      double size = 0;
      try {
          File file = new File("./20140101_0100.txt");
          try {
              System.out.println(file.getCanonicalPath());
          } catch (Exception e) {
              e.printStackTrace();
          }
          if(file.exists()){
              size = file.length();
              System.out.println("File's size is " + file.length());
          } else {
              System.out.println("File does not exist");
          }

      } catch (Exception e) {
          System.out.println("Exception");
      }
      */
      /*
      // Convert data to proto format
      RandomAccessFile f = new RandomAccessFile("./20140101_0100.txt", "r");
      byte[] b = new byte[(int)f.length()];
      f.readFully(b);
      //String responseData = "test";
      //byte b[] = new byte[1024 * 1024];
      //b = responseData.getBytes();
      ByteString bt = ByteString.copyFrom(b);
      CommunicationServiceOuterClass.DatFragment datFragment =
      CommunicationServiceOuterClass.DatFragment.newBuilder()
          .setDataId(1)
          .setFragmentId(1)
          .setData(bt)
          .build();
      CommunicationServiceOuterClass.Data data =
      CommunicationServiceOuterClass.Data.newBuilder()
          .setDatFragment(datFragment)
          .setDate("2014/01/01")
          .build();

      CommunicationServiceOuterClass.PutRequest putRequest =
      CommunicationServiceOuterClass.PutRequest.newBuilder()
          .setData(data)
          .build();

      CommunicationServiceOuterClass.Header request =
      CommunicationServiceOuterClass.Header.newBuilder()
          .setFromIp("Sender IP")
          .setToIp("Receiver IP")
          .setOriginalIp("Original IP")
          .setMaxHop(5)
          .setPutRequest(putRequest)
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
      */
    }
}
