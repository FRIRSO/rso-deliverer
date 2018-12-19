package si.fri.rso.projekt.deliverer.models;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoOrder {


    private final String DBUser       = "root";
    private final String DBPassword   = "13tpxnxJTwUScc3V";
    private final String DBName       = "db-deliverer";
    private final String DBCollection = "deliverers";

    private MongoClient connectDB() {
        MongoClientURI uri = new MongoClientURI("mongodb://"+ DBUser +":"+ DBPassword +"@gsascluster-shard-00-00-ocnkx.azure.mongodb.net:27017," +
                "gsascluster-shard-00-01-ocnkx.azure.mongodb.net:27017,gsascluster-shard-00-02-ocnkx.azure.mongodb.net:27017/test?" +
                "ssl=true&replicaSet=gsasCluster-shard-0&authSource=admin&retryWrites=true");

        return new MongoClient(uri);
    }

    public List<Deliverer> getAllDeliverers() {
        MongoClient client = connectDB();

        MongoDatabase db = client.getDatabase(DBName);

        MongoCollection<Document> orderCollection = db.getCollection(DBCollection);

        List<Deliverer> results = new ArrayList<>();

        for(Document curr : orderCollection.find()) {

            Deliverer deliverer = new Deliverer(curr.getInteger("delivererID"),
                                    curr.getInteger("queueID"),
                                    curr.getString("name"));

            results.add(deliverer);
        }

        return results;
    }

    public Deliverer getDeliverer(Integer delivererID) {
        MongoClient client = connectDB();
        MongoDatabase db = client.getDatabase(DBName);
        MongoCollection<Document> bc = db.getCollection(DBCollection);

        Bson filter = Filters.eq("delivererID", delivererID);

        Document result = bc.find(filter).first();

        if(result == null) {
            return null;
        }


        return new Deliverer(result.getInteger("delivererID"),
                result.getInteger("queueID"),
                result.getString("name"));
    }
}
