import sys
sys.path.append('./mongodb')

import sched, time
import trollius as asyncio
import grpc
import inner_data_pb2
import inner_data_pb2_grpc
from inner_data_pb2 import PutRequest, Response, MetaData, DatFragment, GetRequest
from mongodb_database import *

from subprocess import call
import sys, os

from client import *

CONST_MEDIA_TYPE_TEXT = 1
CONST_CHUNK_SIZE = 5  # number of lines per payload


class CommunicationService(inner_data_pb2_grpc.CommunicationServiceServicer):

    def __init__(self, role):
        super(CommunicationService, self).__init__()
        self.nodes = ['0.0.0.0:8080', '0.0.0.0:8081','0.0.0.0:8082', '0.0.0.0:8083']
        self.role = role
        self.flag = 0

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

    def ping(self, request, context):
        print "Receive ping request"

        fromSender = request.fromSender
        toReceiver = request.toReceiver
        originalSender = request.originalSender
        # print(fromSender,toReceiver,originalSender)

        response = inner_data_pb2.Response()
        response.msg = 'Ping Success'
        #status_code = data_pb2.StatusCode()
        status_code = 1
        response.Code = status_code

        # print "Send Ping response"
        return response

    def putHandler(self, request_iterator, context):
        if self.role == 'leader':
            for i in self.nodes:
                host = i.split(':')[0]
                port = int(i.split(':')[1])
                cli = Client(host, port)
                cli.stub.putHandler(request_iterator)

        print "Receive put request"
        buffer = []
        for request in request_iterator:
            print request
            fromSender = request.fromSender
            toReceiver = request.toReceiver
            originalSender = request.originalSender
            putRequest = request.putRequest
            metaData = putRequest.metaData
            DatFragment = putRequest.datFragment
            buffer.append(DatFragment.data)

        # Save buffer to my mongo database
        print "Saving buffer to mongodb...."
        client = MongoClient('localhost', 27017)
        db = client.pymongo_test
        insert_bulk_mongo(db, buffer)
        print "Finish saving buffer to mongodb"

        # Save buffer to File
        #timestamp_utc = DatFragment.timestamp_utc
        file_name = 'timestamp_utc' + '.txt'
        with open(file_name, 'w') as file:
            file.write(str(buffer))

        # Call comand to send file to clsuter
        #call(['runClient.sh', '1 -write - ' + file_name])
        os.system('sh ../ProjectCluster/client.sh 1 -write -' + file_name)

        # Reponse to server
        response = inner_data_pb2.Response()
        status_code = 1
        response.Code = status_code
        response.msg = 'Put Request success'
        print response
        return response

    def getHandler(self, request, context):
        print "Receive get request"
        print request

        # Get info from local mongodb
        getRequest = request.getRequest
        queryParams = getRequest.queryParams
        from_utc = queryParams.from_utc
        to_utc = queryParams.to_utc

        from_utc_format = from_utc.replace('-','').replace(' ','/').replace(':','')[:-2]
        to_utc_format = to_utc.replace('-','').replace(' ','/').replace(':','')[:-2]

        client = MongoClient('localhost', 27017)
        db = client.pymongo_test
        #insert_bulk_mongo(db, buffer)
        result = find_mongo(db, from_utc_format, to_utc_format)
        print "Result return from mongodb"
        print result
        for line in result:
            print "Line in mongodb"
            print line
            response=Response(
                Code=1,
                metaData=MetaData(uuid='14829'),
                datFragment=DatFragment(data=str(line).encode())
            )
            yield response
        
    def askVote(self, request, context):
        print "Receive ask vote request"
        if self.flag == 0:
            self.flag = 1
            res = inner_data_pb2.Response()
            res.msg = 'ok'
            return res
        
        else: 
            res = inner_data_pb2.Response()
            res.msg = 'wait'
            return res

    def setLeader(self, request, context):
        print "Receive set leader request"
        print request
        self.role = 'leader'

        with open('/Users/huynh/Documents/workspace/275final/GrpcJava/inner_python_grpc/info.txt','w') as f:
            f.write(request.fromSender)

        res = inner_data_pb2.Response()
        res.msg = 'success'
        return res

        

