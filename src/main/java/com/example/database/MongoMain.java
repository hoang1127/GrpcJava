package com.example.database;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
public class MongoMain {

	public static void main(String[] args) throws Exception {
		try {
			MongoClient mgClient = new MongoClient("localhost", 27017);
			DB db = mgClient.getDB("cmpe275");
			System.out.println("Connected successfully");

			 MongoCollection<Document> collection = db.getCollection("project1");
			 //DBCollection collection = db.getCollection("project1");
			 Document doc = new Document("date", new Date("01/01/2018"))
		                  .append("data", "This is data for 01/01/2018");
			 collection.insertOne(doc);
			 doc = new Document("date", new Date("01/02/2018"))
		                  .append("data", "This is data for 01/02/2018");
			 collection.insertOne(doc);
			 System.out.println("Collection completed successfully");

		}catch(Exception e) {
			System.out.println(e);
		}

		System.out.println("Server is done");
	}
	private static void insertJSON(BufferedReader bufReader,DBCollection dbcollect) throws IOException {
		System.out.println("Insert JSON: ");
		dbcollect.insert((DBObject)JSON.parse(bufReader.readLine()));
	}
	private static void insert(BufferedReader bufReader,DBCollection dbcollect) throws IOException {
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
