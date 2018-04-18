# Haoji Liu
# This is the grpc client, that will read a data file, chunk it into several messages, and send it to grpc server
# Haoji Liu
import uuid
import grpc
import data_pb2_grpc
from data_pb2 import Request, Response, PingRequest, PutRequest, GetRequest, DatFragment, MetaData, QueryParams

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


class Client():
  def __init__(self, host='169.254.176.49', port=8080):
    self.channel = grpc.insecure_channel('%s:%d' % (host, port))
    self.stub = data_pb2_grpc.CommunicationServiceStub(self.channel)

  def ping(self, data):
    req = Request(
      fromSender='some ping sender',
      toReceiver='some ping receiver',
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

def test():
  client = Client()
  #print(client.ping('hello'))
  #print(client.put('./mesowesteasy.out'))
  print(client.ping())

if __name__ == '__main__':
  test()
