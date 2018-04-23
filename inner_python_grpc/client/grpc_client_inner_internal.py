import sys
sys.path.append('./proto')

import uuid
import grpc, time
import inner_data_pb2_grpc
from inner_data_pb2 import Request, Response, PingRequest, PutRequest, GetRequest, DatFragment, MetaData, QueryParams

CONST_MEDIA_TYPE_TEXT = 1
CONST_CHUNK_SIZE = 2  # number of lines per payload


def preprocess(fpath):
    """read file and chunkify it to be small batch for grpc transport"""
    #timestamp_utc = '2017-08-08 12:00:00'
    cnt = 10
    buffer = []
    with open(fpath) as f:
        for line in f:
            print line
            if not cnt:
                break
            if len(buffer) == CONST_CHUNK_SIZE:
                print "Chunk size"
                res = ''.join(buffer)
                buffer = []
                print "Res is"
                print res
                putRequest=PutRequest(
                    metaData=MetaData(uuid='14829', mediaType=CONST_MEDIA_TYPE_TEXT),
                    datFragment=DatFragment(data=res.encode()))
                request = Request(
                    fromSender='some put sender',
                    toReceiver='some put receiver',
                    putRequest=putRequest
                )
                yield res
            else:
                buffer.append(line)
            cnt = cnt - 1
        if buffer:
            yield ''.join(buffer)

def process_file(fpath):
    buffer = []
    with open(fpath) as f:
        for line in f:
            buffer.append(line.rstrip('\n').lstrip(' '))
    return buffer

def put_iterator(fpath):
    buffer = process_file(fpath)
    for raw in buffer:
        putRequest=PutRequest(
            metaData=MetaData(uuid='14829', mediaType=CONST_MEDIA_TYPE_TEXT),
            datFragment=DatFragment(data=raw.encode()))
        request = Request(
            fromSender='some put sender',
            toReceiver='some put receiver',
            putRequest=putRequest
        )
        yield request

class Client:
    def __init__(self, host='0.0.0.0', port=8080):
        self.host = host
        self.port = port
        self.channel = grpc.insecure_channel('%s:%d' % (host, port))
        self.stub = inner_data_pb2_grpc.CommunicationServiceStub(self.channel)
    

    def ping(self, data):
    
        req = Request(
            fromSender=self.host + ':' + str(self.port),
            toReceiver='0.0.0.0:8080',  # dont need
            ping=PingRequest(msg='this is a sample ping request'))
        resp = self.stub.ping(req)
        return resp.msg


    def put(self, fpath):
        my_uuid = str(uuid.uuid1())
        #req = preprocess(fpath)
        req = process_file(fpath)
        request_iterator = put_iterator(fpath)
        #req = Request(
        #    fromSender='some put sender',
        #    toReceiver='some put receiver',
        #    putRequest=PutRequest(
        #        metaData=MetaData(uuid=my_uuid, mediaType=CONST_MEDIA_TYPE_TEXT),
        #        datFragment=DatFragment(timestamp_utc=timestamp_utc, data=raw.encode()))
        #)

        resp = self.stub.putHandler(request_iterator)
        print(resp.msg)
        #return True

    def get(self):
        req = Request(
        fromSender='some put sender',
        toReceiver='some put receiver',
        getRequest=GetRequest(
            metaData=MetaData(uuid='14829'),
            queryParams=QueryParams(from_utc='2018-03-16 21:00:00',to_utc='2018-03-16 23:00:00'))
        )
        resp = self.stub.getHandler(req)
        print 'Response from server'
        for response in resp:
            print response
            print response.datFragment.data
        #print resp.msg
        #return resp.datFragment.data

def test(elec_timeout, my_ip):
    nodes = ['169.254.115.118:8080', '169.254.15.17:8080']#,'0.0.0.0:8082', '0.0.0.0:8083']
    vote = 0

    while True:
        with open('/Users/huynh/Documents/workspace/275final/GrpcJava/inner_python_grpc/info.txt','r') as f:
            leader = f.read()
        
        host = leader.split(':')[0]
        port = int(leader.split(':')[1])
        client = Client(host, port)

        if elec_timeout < 5:
            try:
                print(client.ping('to leader'))
                elec_timeout = 0
            except grpc.RpcError as err:
                elec_timeout += 1
                print(err.details())
        
        else:
            print('initial leader election')
            
            for i in range(len(nodes)):
                hostT = nodes[i].split(':')[0]
                portT = int(nodes[i].split(':')[1])
                cli = Client(hostT, portT)
                pingReq = PingRequest(msg='vote')
                req = Request(
                    fromSender=my_ip,
                    toReceiver=nodes[i],
                    ping=pingReq)
                try:
                    res = cli.stub.askVote(req, timeout=5)
                    if res.msg == 'ok':
                        vote += 1
                    else:
                        print(res.msg)
                        elec_timeout = 0
                        time.sleep(2)
                        break
                    print(vote)

                except grpc.RpcError as err:
                    print(err.details())
                        
            if vote >= 3:
                for i in range(len(nodes)):
                    hostTT = nodes[i].split(':')[0]
                    portTT = int(nodes[i].split(':')[1])
                    cli = Client(hostTT, portTT)
                    pingReq = PingRequest(msg='im_leader')
                    req = Request(
                        fromSender=my_ip,
                        toReceiver=my_ip,
                        ping=pingReq)

                    try:
                        res = cli.stub.setLeader(req, timeout=5)
                    except grpc.RpcError as err:
                        print(err.details())

                elec_timeout = 0

            vote = 0

        time.sleep(2)

if __name__ == '__main__':
    my_ip = sys.argv[1]
    elec_timeout = 0
    test(elec_timeout, my_ip)
