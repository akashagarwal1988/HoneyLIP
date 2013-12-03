package database;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Date;

import stix.Record;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class DatabaseConnection {

	public static MongoClient mongoClient;
	 static DB db;
	 static DBCollection collection;
	 
	 public static void main(String []args) throws UnknownHostException{
		 
		 Record record = new Record("IRC");
		 makeConnection();
		 insertRecord(record);
		 
	 }

	public static void makeConnection() throws UnknownHostException {

		mongoClient = new MongoClient();
		db = mongoClient.getDB("HoneyLIP");
		collection = db.getCollection("Records");

	}

	public static void insertRecord(Record record) {
		
		Gson gson = new Gson();
		String json = gson.toJson(record);
		DBObject dbObject = (DBObject) JSON.parse(json);
		collection.insert(dbObject);

	}
	
	public static void queryforModule(String module) throws FileNotFoundException{
		
		BasicDBObject query = new BasicDBObject();
		query.put("moduleName", module);
		DBCursor cursor = collection.find(query);
		
		PrintWriter writer = new PrintWriter("report" + "_for_" + module + "_" + new Date() );
		while(cursor.hasNext()){
		BasicDBObject row = (BasicDBObject) cursor.next();
		String output = row.toString();
			writer.print(output);
			
		}
		
	}
	

}
