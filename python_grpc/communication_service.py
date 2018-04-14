import grpc

import data_pb2
import data_pb2_grpc
from data_pb2 import PutRequest, Response, MetaData, DatFragment

from subprocess import call
import sys, os

CONST_MEDIA_TYPE_TEXT = 1
CONST_CHUNK_SIZE = 5  # number of lines per payload

class CommunicationService(data_pb2_grpc.CommunicationServiceServicer):

    def preprocess(self, fpath):
        """read file and chunkify it to be small batch for grpc transport"""
        timestamp_utc = '2017-08-08'
        cnt = 10
        buffer = []
        print(fpath)
        with open(fpath) as f:
            for line in f:
                print line
                if not cnt:
                    break

                if len(buffer) == CONST_CHUNK_SIZE:
                    print "Chunk size"
                    res = ''.join(buffer)
                    buffer = []
                    print res
                    response=Response(
                        Code=1,
                        metaData=MetaData(uuid='14829'),
                        datFragment=DatFragment(timestamp_utc=timestamp_utc, data=res.encode())
                    )
                    yield response
                else:
                    buffer.append(line)
                    cnt = cnt - 1

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

        # Save buffer to File
        timestamp_utc = DatFragment.timestamp_utc
        file_name = timestamp_utc + '.txt'
        with open(file_name, 'w') as file:
            file.write(str(buffer))

        # Call comand to send file to clsuter
        #call(['runClient.sh', '1 -write - ' + file_name])
        os.system('runClient.sh 1 -write -' + file_name)
        # Reponse to server
        response = data_pb2.Response()
        status_code = 1
        response.Code = status_code
        response.msg = 'Put Request success'
        print response
        return response

    def GetHandler(self, request, context):
        print "Receive get request"
        print request
        timestamp_utc = '2017-08-08'
        cnt = 10
        buffer = []
        fpath = './20140101_0100.txt'
        print(fpath)
        with open(fpath) as f:
            for line in f:
                print line
                if not cnt:
                    break

                if len(buffer) == CONST_CHUNK_SIZE:
                    print "Chunk size"
                    res = ''.join(buffer)
                    buffer = []
                    print res
                    response=Response(
                        Code=1,
                        metaData=MetaData(uuid='14829'),
                        datFragment=DatFragment(timestamp_utc=timestamp_utc, data=res.encode())
                    )
                    yield response
                else:
                    buffer.append(line)
                    cnt = cnt - 1
        #self.preprocess()
