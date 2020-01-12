package mongodb;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Connector {
    public static MongoCollection getCollection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("food");
        MongoCollection<org.bson.Document> coll = database.getCollection("booking");
        for (Document d : coll.find()) {
            System.out.println(d);
        }
        return coll;
    }

}