import grpc

import data_pb2
import data_pb2_grpc
from data_pb2 import PutRequest

class CommunicationService(data_pb2_grpc.CommunicationServiceServicer):

    def Ping(self, request, context):
        print "Receive ping request"

        fromSender = request.fromSender
        toReceiver = request.toReceiver
        originalSender = request.originalSender

        response = data_pb2.Response()
        response.msg = 'Ping Success'
        #status_code = data_pb2.StatusCode()
        status_code = 1
        response.Code = status_code

        print "Send Ping response"
        return response

    def PutHandler(self, request_iterator, context):
        print "Receive put request"
        print request_iterator
        buffer = []
        for request in request_iterator:
            print request
            fromSender = request.fromSender
            toReceiver = request.toReceiver
            originalSender = request.originalSender
            putRequest = request.putRequest
            metaData = putRequest.metaData
            DatFragment = putRequest.datFragment
            buffer.append(DatFragment.data.decode())
        print buffer
