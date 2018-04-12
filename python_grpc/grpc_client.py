# Haoji Liu
# This is the grpc client, that will read a data file, chunk it into several messages, and send it to grpc server
# Haoji Liu
import uuid
import grpc
import data_pb2_grpc
from data_pb2 import Request, Response, PingRequest, PutRequest, GetRequest, DatFragment, MetaData, QueryParams

CONST_MEDIA_TYPE_TEXT = 1
CONST_CHUNK_SIZE = 5  # number of lines per payload

def preprocess(fpath):
  """read file and chunkify it to be small batch for grpc transport"""
  timestamp_utc = '2017-08-08'
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
        print res
        putRequest=PutRequest(
            metaData=MetaData(uuid='14829', mediaType=CONST_MEDIA_TYPE_TEXT),
            datFragment=DatFragment(timestamp_utc=timestamp_utc, data=res.encode()))
        request = Request(
            fromSender='some put sender',
            toReceiver='some put receiver',
            putRequest=putRequest
        )
        yield request
      else:
        buffer.append(line)
      cnt = cnt - 1



class Client():
  def __init__(self, host='0.0.0.0', port=3000):
    self.channel = grpc.insecure_channel('%s:%d' % (host, port))
    self.stub = data_pb2_grpc.CommunicationServiceStub(self.channel)

  def ping(self, data):
    req = Request(
      fromSender='some ping sender',
      toReceiver='some ping receiver',
      ping=PingRequest(msg='this is a sample ping request'))
    resp = self.stub.Ping(req)
    return resp.msg

  def put(self, fpath):
    my_uuid = str(uuid.uuid1())
    timestamp_utc = '2017-08-08'
    req = preprocess(fpath)
    #for timestamp_utc, raw in preprocess(fpath):

    #req = Request(
    #    fromSender='some put sender',
    #    toReceiver='some put receiver',
    #    putRequest=PutRequest(
    #        metaData=MetaData(uuid=my_uuid, mediaType=CONST_MEDIA_TYPE_TEXT),
    #        datFragment=DatFragment(timestamp_utc=timestamp_utc, data=raw.encode()))
    #)

    resp = self.stub.PutHandler(req)
    print(resp.msg)
    return True

  def get(self):
    req = Request(
      fromSender='some put sender',
      toReceiver='some put receiver',
      getRequest=GetRequest(
          metaData=MetaData(uuid='14829'),
          queryParams=QueryParams(from_utc='2017-01-01',to_utc='2017-01-02'))
      )
    resp = self.stub.MessageHandler(req)
    return resp.datFragment.data

def test():
  client = Client()
  print(client.ping('hello'))
  print(client.put('./20140101_0100.txt'))
  print(client.get())

if __name__ == '__main__':
  test()
