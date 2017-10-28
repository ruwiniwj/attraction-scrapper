package com.tripadvisor.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import com.mongodb.util.JSON;
import com.tripadvisor.Bean.AttractionBean;

public class DatabaseWriter {
    private MongoClient client = new MongoClient("localhost", 27017);
    //        MongoDatabase database = client.getDatabase("AttractionDetails");
//        MongoCollection collection = database.getCollection("Attraction");
    private DB db = client.getDB("AttractionDetails");
    private DBCollection collection = db.getCollection("Attraction");


    public void writeToDatabase(AttractionBean attractionBean) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DBObject dbObject = (DBObject) JSON.parse(gson.toJson(attractionBean));
        collection.insert(dbObject);
//        collection.insertOne(dbObject);
        System.out.println("Write to mongo");
    }

//    public void updateAttribute(String attractionName, String attributeName, String value) {
//        BasicDBObject newDocument = new BasicDBObject();
//        newDocument.append("$set", new BasicDBObject().append(attributeName, value));
//        BasicDBObject searchQuery = new BasicDBObject().append("attractionName", attractionName);
//        collection.update(searchQuery, newDocument);
//    }
}
