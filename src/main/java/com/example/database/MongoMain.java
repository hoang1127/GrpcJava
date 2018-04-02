package com.example.database;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class MongoMain {

	public static void main(String[] args) throws Exception {
		try {
	
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("cmpe275");
			DBCollection collection = db.getCollection("project1");
			System.out.println("Connected successfully");


			Scanner input = new Scanner(System.in);
			// Wait for input
			while(true){
				System.out.println("Enter DB command : i: Insert ; ; s: Show ; c: Create ; d: Delete; u: Update x: Exit:   ");
				// Get Keyboard input
				String inputKey = input.nextLine();
				System.out.print("You entered : ");
				System.out.println(inputKey);
				if(inputKey.equals("i")){
					String json = "{ 'name' : '20140101_0100' , " +
                        			"'data' : 'KABR,72659,Aberdeen: Aberdeen Regional Airport,SD,US,45.44333,-98.41306,395.9,1,NWS/FAA,ACTIVE,2,National Weather Service,,,,;' , " + "}";
              
        			DBObject dbObj = (DBObject)JSON.parse(json);
            		collection.insert(dbObj);
					System.out.println("Insert a Document into collection");
				}else if(inputKey.equals("c")){
					System.out.println("Create new collection");
				}else if(inputKey.equals("d")){
					DBObject docD = collection.findOne();
					System.out.println(docD);
					if(docD.equals(null)){
						System.out.println("Collection is empty.");
					}else{

						collection.remove(docD);
						System.out.println("Delete One Document");

					}
					
				}else if(inputKey.equals("s")){
					DBCursor cursor = collection.find();
					while(cursor.hasNext()){
						DBObject objData = cursor.next();
						System.out.println(objData);
					}
					System.out.println(collection.find());
				
				}else if(inputKey.equals("u")){
					System.out.println("Update data");
				}else if(inputKey.equals("x")){
					System.out.println("Exit");
					break;
				}
			}

		}catch(Exception e) {
			System.out.println(e);
		}

		System.out.println("Server is stop");
	}
	private static DBCollection createCollection(DB db, String collectionName){
		DBCollection collection = db.getCollection(collectionName);
		System.out.println("Connected successfully");
		return collection;
	}
	
	private static void insertJSON(BufferedReader bufReader, DBCollection dbcollect) throws IOException {
		System.out.println("Insert JSON: ");
		dbcollect.insert((DBObject)JSON.parse(bufReader.readLine()));
	}
	private static void insert(BufferedReader bufReader, DBCollection dbcollect) throws IOException {
		System.out.println("Insert Data: ");
		String data_name = bufReader.readLine();
		DBObject dbobj = new BasicDBObject();
		dbobj.put("data", data_name);
		String value_subs = bufReader.readLine();
		dbobj.put("subscriptions", Integer.parseInt(value_subs));
		String strLine = bufReader.readLine();
		String [] strArr = strLine.split(",");
		int i = 0;
		BasicDBList strDBList = new BasicDBList();
		DBObject strDBObj = null;
		while(  i<strArr.length) {
			strDBObj = new BasicDBObject();
			strDBObj.put("name", strArr[i++]);
			strDBList.add(strDBObj);
		}
		// Insert
		dbobj.put("value", strDBList.toArray());
		dbcollect.insert((dbobj));
	}
	private static void delete(BufferedReader bufReader, DBCollection dbcollect) throws IOException {
		DBObject dbobj = new BasicDBObject();
		dbobj.put("name", bufReader.readLine());
		dbcollect.remove(dbobj);
	}
	private static void update(BufferedReader bufReader, DBCollection dbcollect) throws IOException {
		DBObject fromdbobj = new BasicDBObject();
		fromdbobj.put("name", bufReader.readLine());
		DBObject todbobj = new BasicDBObject();
		todbobj.put("name", bufReader.readLine());
		DBObject updatedbobj = new BasicDBObject();
		updatedbobj.put("$set", todbobj);
		dbcollect.update(fromdbobj, updatedbobj);
	}

	private static void selectAll(DBCollection dbcollect) {
		DBCursor dbCursor = dbcollect.find();
		while(dbCursor.hasNext()) {
			System.out.println(dbCursor.next());
		}
	}

}
