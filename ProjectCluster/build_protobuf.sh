#!/bin/bash
# To build protobuf classes from the .proto file 
# protobuf 2.4.1.
# Building: 
# 
# Run once when the protobuf structures is updated 
#
project_base="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ -d ${project_base}/generated ]; then
  echo "removing contents of ${project_base}/generated"
  rm -r ${project_base}/generated/*
else
  echo "creating directory ${project_base}/generated"
  mkdir ${project_base}/generated
fi
# Set each protoc path
protoc --proto_path=${project_base}/resources --java_out=${project_base}/generated ${project_base}/resources/pipe.proto
protoc --proto_path=${project_base}/resources --java_out=${project_base}/generated ${project_base}/resources/work.proto
protoc --proto_path=${project_base}/resources --java_out=${project_base}/generated ${project_base}/resources/election.proto
protoc --proto_path=${project_base}/resources --java_out=${project_base}/generated ${project_base}/resources/common.proto
