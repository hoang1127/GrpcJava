from pymongo import MongoClient
from time import time
import logging
import string
import subprocess
import time
import datetime

def insert_bulk_mongo(db, data):
    for d in data:
        print d
        values = d.split()
        dateTimeObject = datetime.datetime.strptime(values[1], '%Y%m%d/%H%M')
        print dateTimeObject
        pattern = '%Y%m%d/%H%M'
        time_t = int(time.mktime(time.strptime(values[1], pattern))) * 1000
        print time_t
        input_data = {
            "STN":values[0],
            "timestamp":time_t,
            "MNET":values[2],
            "SLAT":values[3],
            "SLON":values[4],
            "SELV":values[5],
            "TMPF":values[6],
            "SKNT":values[7],
            "DRCT":values[8],
            "GUST":values[9],
            "PMSL":values[10],
            "ALTI":values[11],
            "DWPF":values[12],
            "RELH":values[13],
            "WTHR":values[14],
            "P24I":values[15]
        }
        #print input_data
        result = db.mesowest.insert_one(input_data)
        print 'One post: {0}'.format(result.inserted_id)

def find_mongo(db, startDate, endDate):
    value = []
    try:
        pattern = '%Y%m%d/%H%M'
        startDateTS = int(time.mktime(time.strptime(startDate, pattern))) * 1000
        endDateTS = int(time.mktime(time.strptime(endDate, pattern))) * 1000
        result=db.mesowest.find({"timestamp":{"$lt": endDateTS, "$gte": startDateTS}})
        for document in result:
            print(document)
            value.append(document)
        return value
    except Exception as e:
        logging.warning(e)

def test():
    client = MongoClient('localhost', 27017)
    db = client.pymongo_test

    #data = db.mesowest()
    insert_bulk_mongo(db, )

if __name__ == '__main__':
    fpath = './mesowesteasy.out'
    buffer = []
    with open(fpath) as f:
        for line in f:
            buffer.append(line.rstrip('\n').lstrip(' '))
    print buffer
    client = MongoClient('localhost', 27017)
    db = client.pymongo_test
    insert_bulk_mongo(db, buffer)
    find_mongo(db, '20180316/2100', '20180316/2200')
    #test()
