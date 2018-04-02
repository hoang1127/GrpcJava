/*
 * Testing method for MongoDB database
 */
package com.example.database;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Date;

public class MongoTest {

	public static void main(String[] args) throws Exception {
		try {
      MongoClient mgClient = new MongoClient("localhost", 27017);
      MongoDatabase db = mgClient.getDatabase("cmpe275");
      System.out.println("Connected successfully");

      MongoCollection<Document> collection = db.getCollection("project1");
      //DBCollection collection = db.getCollection("project1");
      Document doc = new Document("date", new Date("01/01/2018"))
                  .append("data", "This is data for 01/01/2018");
      collection.insertOne(doc);
      doc = new Document("date", new Date("01/02/2018"))
                  .append("data", "This is data for 01/02/2018");
      collection.insertOne(doc);

		}catch(Exception e) {
			System.out.println(e);
		}

		System.out.println("Data inserted successfully");
	}

}
