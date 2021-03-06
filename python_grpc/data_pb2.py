# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: data.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf.internal import enum_type_wrapper
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
from google.protobuf import descriptor_pb2
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='data.proto',
  package='grpcComm',
  syntax='proto3',
  serialized_pb=_b('\n\ndata.proto\x12\x08grpcComm\"\xd3\x01\n\x07Request\x12\x12\n\nfromSender\x18\x01 \x01(\t\x12\x12\n\ntoReceiver\x18\x02 \x01(\t\x12\x16\n\x0eoriginalSender\x18\x03 \x01(\t\x12%\n\x04ping\x18\x06 \x01(\x0b\x32\x15.grpcComm.PingRequestH\x00\x12*\n\nputRequest\x18\x07 \x01(\x0b\x32\x14.grpcComm.PutRequestH\x00\x12*\n\ngetRequest\x18\x08 \x01(\x0b\x32\x14.grpcComm.GetRequestH\x00\x42\t\n\x07payload\"\x8d\x01\n\x08Response\x12\"\n\x04\x43ode\x18\x01 \x01(\x0e\x32\x14.grpcComm.StatusCode\x12\x0b\n\x03msg\x18\x02 \x01(\t\x12$\n\x08metaData\x18\x03 \x01(\x0b\x32\x12.grpcComm.MetaData\x12*\n\x0b\x64\x61tFragment\x18\x04 \x01(\x0b\x32\x15.grpcComm.DatFragment\"\x1a\n\x0bPingRequest\x12\x0b\n\x03msg\x18\x01 \x01(\t\"^\n\nPutRequest\x12$\n\x08metaData\x18\x01 \x01(\x0b\x32\x12.grpcComm.MetaData\x12*\n\x0b\x64\x61tFragment\x18\x02 \x01(\x0b\x32\x15.grpcComm.DatFragment\"^\n\nGetRequest\x12$\n\x08metaData\x18\x01 \x01(\x0b\x32\x12.grpcComm.MetaData\x12*\n\x0bqueryParams\x18\x02 \x01(\x0b\x32\x15.grpcComm.QueryParams\"/\n\x0bQueryParams\x12\x10\n\x08\x66rom_utc\x18\x01 \x01(\t\x12\x0e\n\x06to_utc\x18\x02 \x01(\t\"B\n\x08MetaData\x12\x0c\n\x04uuid\x18\x01 \x01(\t\x12\x15\n\rnumOfFragment\x18\x02 \x01(\x05\x12\x11\n\tmediaType\x18\x03 \x01(\x05\"2\n\x0b\x44\x61tFragment\x12\x15\n\rtimestamp_utc\x18\x01 \x01(\t\x12\x0c\n\x04\x64\x61ta\x18\x02 \x01(\x0c*-\n\nStatusCode\x12\x0b\n\x07Unknown\x10\x00\x12\x06\n\x02Ok\x10\x01\x12\n\n\x06\x46\x61iled\x10\x02\x32\xb3\x01\n\x14\x43ommunicationService\x12\x35\n\nputHandler\x12\x11.grpcComm.Request\x1a\x12.grpcComm.Response(\x01\x12\x35\n\ngetHandler\x12\x11.grpcComm.Request\x1a\x12.grpcComm.Response0\x01\x12-\n\x04ping\x12\x11.grpcComm.Request\x1a\x12.grpcComm.ResponseB#\n\x14\x63om.cmpe275.grpcCommB\tDataProtoP\x01\x62\x06proto3')
)
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

_STATUSCODE = _descriptor.EnumDescriptor(
  name='StatusCode',
  full_name='grpcComm.StatusCode',
  filename=None,
  file=DESCRIPTOR,
  values=[
    _descriptor.EnumValueDescriptor(
      name='Unknown', index=0, number=0,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='Ok', index=1, number=1,
      options=None,
      type=None),
    _descriptor.EnumValueDescriptor(
      name='Failed', index=2, number=2,
      options=None,
      type=None),
  ],
  containing_type=None,
  options=None,
  serialized_start=771,
  serialized_end=816,
)
_sym_db.RegisterEnumDescriptor(_STATUSCODE)

StatusCode = enum_type_wrapper.EnumTypeWrapper(_STATUSCODE)
Unknown = 0
Ok = 1
Failed = 2



_REQUEST = _descriptor.Descriptor(
  name='Request',
  full_name='grpcComm.Request',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='fromSender', full_name='grpcComm.Request.fromSender', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='toReceiver', full_name='grpcComm.Request.toReceiver', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='originalSender', full_name='grpcComm.Request.originalSender', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='ping', full_name='grpcComm.Request.ping', index=3,
      number=6, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='putRequest', full_name='grpcComm.Request.putRequest', index=4,
      number=7, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='getRequest', full_name='grpcComm.Request.getRequest', index=5,
      number=8, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
    _descriptor.OneofDescriptor(
      name='payload', full_name='grpcComm.Request.payload',
      index=0, containing_type=None, fields=[]),
  ],
  serialized_start=25,
  serialized_end=236,
)


_RESPONSE = _descriptor.Descriptor(
  name='Response',
  full_name='grpcComm.Response',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='Code', full_name='grpcComm.Response.Code', index=0,
      number=1, type=14, cpp_type=8, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='msg', full_name='grpcComm.Response.msg', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='metaData', full_name='grpcComm.Response.metaData', index=2,
      number=3, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='datFragment', full_name='grpcComm.Response.datFragment', index=3,
      number=4, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=239,
  serialized_end=380,
)


_PINGREQUEST = _descriptor.Descriptor(
  name='PingRequest',
  full_name='grpcComm.PingRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='msg', full_name='grpcComm.PingRequest.msg', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=382,
  serialized_end=408,
)


_PUTREQUEST = _descriptor.Descriptor(
  name='PutRequest',
  full_name='grpcComm.PutRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='metaData', full_name='grpcComm.PutRequest.metaData', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='datFragment', full_name='grpcComm.PutRequest.datFragment', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=410,
  serialized_end=504,
)


_GETREQUEST = _descriptor.Descriptor(
  name='GetRequest',
  full_name='grpcComm.GetRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='metaData', full_name='grpcComm.GetRequest.metaData', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='queryParams', full_name='grpcComm.GetRequest.queryParams', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=506,
  serialized_end=600,
)


_QUERYPARAMS = _descriptor.Descriptor(
  name='QueryParams',
  full_name='grpcComm.QueryParams',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='from_utc', full_name='grpcComm.QueryParams.from_utc', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='to_utc', full_name='grpcComm.QueryParams.to_utc', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=602,
  serialized_end=649,
)


_METADATA = _descriptor.Descriptor(
  name='MetaData',
  full_name='grpcComm.MetaData',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='uuid', full_name='grpcComm.MetaData.uuid', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='numOfFragment', full_name='grpcComm.MetaData.numOfFragment', index=1,
      number=2, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='mediaType', full_name='grpcComm.MetaData.mediaType', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=651,
  serialized_end=717,
)


_DATFRAGMENT = _descriptor.Descriptor(
  name='DatFragment',
  full_name='grpcComm.DatFragment',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='timestamp_utc', full_name='grpcComm.DatFragment.timestamp_utc', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
    _descriptor.FieldDescriptor(
      name='data', full_name='grpcComm.DatFragment.data', index=1,
      number=2, type=12, cpp_type=9, label=1,
      has_default_value=False, default_value=_b(""),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      options=None),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=719,
  serialized_end=769,
)

_REQUEST.fields_by_name['ping'].message_type = _PINGREQUEST
_REQUEST.fields_by_name['putRequest'].message_type = _PUTREQUEST
_REQUEST.fields_by_name['getRequest'].message_type = _GETREQUEST
_REQUEST.oneofs_by_name['payload'].fields.append(
  _REQUEST.fields_by_name['ping'])
_REQUEST.fields_by_name['ping'].containing_oneof = _REQUEST.oneofs_by_name['payload']
_REQUEST.oneofs_by_name['payload'].fields.append(
  _REQUEST.fields_by_name['putRequest'])
_REQUEST.fields_by_name['putRequest'].containing_oneof = _REQUEST.oneofs_by_name['payload']
_REQUEST.oneofs_by_name['payload'].fields.append(
  _REQUEST.fields_by_name['getRequest'])
_REQUEST.fields_by_name['getRequest'].containing_oneof = _REQUEST.oneofs_by_name['payload']
_RESPONSE.fields_by_name['Code'].enum_type = _STATUSCODE
_RESPONSE.fields_by_name['metaData'].message_type = _METADATA
_RESPONSE.fields_by_name['datFragment'].message_type = _DATFRAGMENT
_PUTREQUEST.fields_by_name['metaData'].message_type = _METADATA
_PUTREQUEST.fields_by_name['datFragment'].message_type = _DATFRAGMENT
_GETREQUEST.fields_by_name['metaData'].message_type = _METADATA
_GETREQUEST.fields_by_name['queryParams'].message_type = _QUERYPARAMS
DESCRIPTOR.message_types_by_name['Request'] = _REQUEST
DESCRIPTOR.message_types_by_name['Response'] = _RESPONSE
DESCRIPTOR.message_types_by_name['PingRequest'] = _PINGREQUEST
DESCRIPTOR.message_types_by_name['PutRequest'] = _PUTREQUEST
DESCRIPTOR.message_types_by_name['GetRequest'] = _GETREQUEST
DESCRIPTOR.message_types_by_name['QueryParams'] = _QUERYPARAMS
DESCRIPTOR.message_types_by_name['MetaData'] = _METADATA
DESCRIPTOR.message_types_by_name['DatFragment'] = _DATFRAGMENT
DESCRIPTOR.enum_types_by_name['StatusCode'] = _STATUSCODE

Request = _reflection.GeneratedProtocolMessageType('Request', (_message.Message,), dict(
  DESCRIPTOR = _REQUEST,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.Request)
  ))
_sym_db.RegisterMessage(Request)

Response = _reflection.GeneratedProtocolMessageType('Response', (_message.Message,), dict(
  DESCRIPTOR = _RESPONSE,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.Response)
  ))
_sym_db.RegisterMessage(Response)

PingRequest = _reflection.GeneratedProtocolMessageType('PingRequest', (_message.Message,), dict(
  DESCRIPTOR = _PINGREQUEST,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.PingRequest)
  ))
_sym_db.RegisterMessage(PingRequest)

PutRequest = _reflection.GeneratedProtocolMessageType('PutRequest', (_message.Message,), dict(
  DESCRIPTOR = _PUTREQUEST,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.PutRequest)
  ))
_sym_db.RegisterMessage(PutRequest)

GetRequest = _reflection.GeneratedProtocolMessageType('GetRequest', (_message.Message,), dict(
  DESCRIPTOR = _GETREQUEST,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.GetRequest)
  ))
_sym_db.RegisterMessage(GetRequest)

QueryParams = _reflection.GeneratedProtocolMessageType('QueryParams', (_message.Message,), dict(
  DESCRIPTOR = _QUERYPARAMS,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.QueryParams)
  ))
_sym_db.RegisterMessage(QueryParams)

MetaData = _reflection.GeneratedProtocolMessageType('MetaData', (_message.Message,), dict(
  DESCRIPTOR = _METADATA,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.MetaData)
  ))
_sym_db.RegisterMessage(MetaData)

DatFragment = _reflection.GeneratedProtocolMessageType('DatFragment', (_message.Message,), dict(
  DESCRIPTOR = _DATFRAGMENT,
  __module__ = 'data_pb2'
  # @@protoc_insertion_point(class_scope:grpcComm.DatFragment)
  ))
_sym_db.RegisterMessage(DatFragment)


DESCRIPTOR.has_options = True
DESCRIPTOR._options = _descriptor._ParseOptions(descriptor_pb2.FileOptions(), _b('\n\024com.cmpe275.grpcCommB\tDataProtoP\001'))
try:
  # THESE ELEMENTS WILL BE DEPRECATED.
  # Please use the generated *_pb2_grpc.py files instead.
  import grpc
  from grpc.framework.common import cardinality
  from grpc.framework.interfaces.face import utilities as face_utilities
  from grpc.beta import implementations as beta_implementations
  from grpc.beta import interfaces as beta_interfaces


  class CommunicationServiceStub(object):

    def __init__(self, channel):
      """Constructor.

      Args:
        channel: A grpc.Channel.
      """
      self.putHandler = channel.stream_unary(
          '/grpcComm.CommunicationService/putHandler',
          request_serializer=Request.SerializeToString,
          response_deserializer=Response.FromString,
          )
      self.getHandler = channel.unary_stream(
          '/grpcComm.CommunicationService/getHandler',
          request_serializer=Request.SerializeToString,
          response_deserializer=Response.FromString,
          )
      self.ping = channel.unary_unary(
          '/grpcComm.CommunicationService/ping',
          request_serializer=Request.SerializeToString,
          response_deserializer=Response.FromString,
          )


  class CommunicationServiceServicer(object):

    def putHandler(self, request_iterator, context):
      context.set_code(grpc.StatusCode.UNIMPLEMENTED)
      context.set_details('Method not implemented!')
      raise NotImplementedError('Method not implemented!')

    def getHandler(self, request, context):
      context.set_code(grpc.StatusCode.UNIMPLEMENTED)
      context.set_details('Method not implemented!')
      raise NotImplementedError('Method not implemented!')

    def ping(self, request, context):
      context.set_code(grpc.StatusCode.UNIMPLEMENTED)
      context.set_details('Method not implemented!')
      raise NotImplementedError('Method not implemented!')


  def add_CommunicationServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
        'putHandler': grpc.stream_unary_rpc_method_handler(
            servicer.putHandler,
            request_deserializer=Request.FromString,
            response_serializer=Response.SerializeToString,
        ),
        'getHandler': grpc.unary_stream_rpc_method_handler(
            servicer.getHandler,
            request_deserializer=Request.FromString,
            response_serializer=Response.SerializeToString,
        ),
        'ping': grpc.unary_unary_rpc_method_handler(
            servicer.ping,
            request_deserializer=Request.FromString,
            response_serializer=Response.SerializeToString,
        ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
        'grpcComm.CommunicationService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))


  class BetaCommunicationServiceServicer(object):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This class was generated
    only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0."""
    def putHandler(self, request_iterator, context):
      context.code(beta_interfaces.StatusCode.UNIMPLEMENTED)
    def getHandler(self, request, context):
      context.code(beta_interfaces.StatusCode.UNIMPLEMENTED)
    def ping(self, request, context):
      context.code(beta_interfaces.StatusCode.UNIMPLEMENTED)


  class BetaCommunicationServiceStub(object):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This class was generated
    only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0."""
    def putHandler(self, request_iterator, timeout, metadata=None, with_call=False, protocol_options=None):
      raise NotImplementedError()
    putHandler.future = None
    def getHandler(self, request, timeout, metadata=None, with_call=False, protocol_options=None):
      raise NotImplementedError()
    def ping(self, request, timeout, metadata=None, with_call=False, protocol_options=None):
      raise NotImplementedError()
    ping.future = None


  def beta_create_CommunicationService_server(servicer, pool=None, pool_size=None, default_timeout=None, maximum_timeout=None):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This function was
    generated only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0"""
    request_deserializers = {
      ('grpcComm.CommunicationService', 'getHandler'): Request.FromString,
      ('grpcComm.CommunicationService', 'ping'): Request.FromString,
      ('grpcComm.CommunicationService', 'putHandler'): Request.FromString,
    }
    response_serializers = {
      ('grpcComm.CommunicationService', 'getHandler'): Response.SerializeToString,
      ('grpcComm.CommunicationService', 'ping'): Response.SerializeToString,
      ('grpcComm.CommunicationService', 'putHandler'): Response.SerializeToString,
    }
    method_implementations = {
      ('grpcComm.CommunicationService', 'getHandler'): face_utilities.unary_stream_inline(servicer.getHandler),
      ('grpcComm.CommunicationService', 'ping'): face_utilities.unary_unary_inline(servicer.ping),
      ('grpcComm.CommunicationService', 'putHandler'): face_utilities.stream_unary_inline(servicer.putHandler),
    }
    server_options = beta_implementations.server_options(request_deserializers=request_deserializers, response_serializers=response_serializers, thread_pool=pool, thread_pool_size=pool_size, default_timeout=default_timeout, maximum_timeout=maximum_timeout)
    return beta_implementations.server(method_implementations, options=server_options)


  def beta_create_CommunicationService_stub(channel, host=None, metadata_transformer=None, pool=None, pool_size=None):
    """The Beta API is deprecated for 0.15.0 and later.

    It is recommended to use the GA API (classes and functions in this
    file not marked beta) for all further purposes. This function was
    generated only to ease transition from grpcio<0.15.0 to grpcio>=0.15.0"""
    request_serializers = {
      ('grpcComm.CommunicationService', 'getHandler'): Request.SerializeToString,
      ('grpcComm.CommunicationService', 'ping'): Request.SerializeToString,
      ('grpcComm.CommunicationService', 'putHandler'): Request.SerializeToString,
    }
    response_deserializers = {
      ('grpcComm.CommunicationService', 'getHandler'): Response.FromString,
      ('grpcComm.CommunicationService', 'ping'): Response.FromString,
      ('grpcComm.CommunicationService', 'putHandler'): Response.FromString,
    }
    cardinalities = {
      'getHandler': cardinality.Cardinality.UNARY_STREAM,
      'ping': cardinality.Cardinality.UNARY_UNARY,
      'putHandler': cardinality.Cardinality.STREAM_UNARY,
    }
    stub_options = beta_implementations.stub_options(host=host, metadata_transformer=metadata_transformer, request_serializers=request_serializers, response_deserializers=response_deserializers, thread_pool=pool, thread_pool_size=pool_size)
    return beta_implementations.dynamic_stub(channel, 'grpcComm.CommunicationService', cardinalities, options=stub_options)
except ImportError:
  pass
# @@protoc_insertion_point(module_scope)
