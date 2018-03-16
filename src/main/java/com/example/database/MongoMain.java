package com.example.database;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoMain {

	public static void main(String[] args) throws Exception {
		try {
			MongoClient mgClient = new MongoClient("localhost", 27017);
			DB db = mgClient.getDB("cmpe275");
			System.out.println("Connected successfully");

			DBCollection collect = db.getCollection("project1");
			DBCursor cur = collect.find();

			while(cur.hasNext()) {
				int i=1;
				System.out.println(cur.next());
				i++;
			}

		}catch(Exception e) {
			System.out.println(e);
		}

		System.out.println("Server is done");
	}

}
