# Haoji Liu
import time
import grpc
import data_pb2
import data_pb2_grpc
import requests
from concurrent import futures
import socket
import fcntl
import struct

import communication_service

#import zmq

def get_ip_address(ifname):
    import os
    f = os.popen('ifconfig ' + ifname + ' | grep "inet "')
    try:
        your_ip=f.read().split(" ")[1]
    except:
        your_ip = '0.0.0.0'
    return your_ip

def run(host, port):
  server = grpc.server(futures.ThreadPoolExecutor(max_workers=1))
  ds = communication_service.CommunicationService()
  data_pb2_grpc.add_CommunicationServiceServicer_to_server(ds, server)
  server.add_insecure_port('%s:%d' % (host, port))
  server.start()

  _ONE_DAY_IN_SECONDS = 60 * 60 * 24
  try:

    print("Server started at...%d" % port)
    while True:
      time.sleep(_ONE_DAY_IN_SECONDS)
  except KeyboardInterrupt:
    server.stop(0)


if __name__ == '__main__':
  # publish server ip to cluster
  my_ip = get_ip_address('en4')
  print "My IP is " + my_ip
  nodes = requests.get('https://cmpe275-spring-18.mybluemix.net/put/' + my_ip)
  run('0.0.0.0', 8080)
