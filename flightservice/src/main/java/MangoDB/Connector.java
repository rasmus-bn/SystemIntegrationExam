package MangoDB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
public class Connector {
    public static MongoCollection getCollection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("bookings");
        MongoCollection<org.bson.Document> coll = database.getCollection("booking");
        return coll;
    }

}