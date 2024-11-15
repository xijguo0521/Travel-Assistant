import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;

public class DBClient {
    private static final String URI = "mongodb://4for4:4for4!@ds117592.mlab.com:17592/4for4";
    private static MongoDatabase dbInstance;

    public static synchronized MongoDatabase getDatabase() {
        if (dbInstance == null) {
            MongoClientURI uri  = new MongoClientURI(URI);
            MongoClient client = new MongoClient(uri);
            dbInstance = client.getDatabase(uri.getDatabase());
        }
        return dbInstance;
    }

    public static Document getUser(MongoCollection<Document> users, String username) throws Exception {
		Document findQuery = new Document("username", username);
		MongoCursor<Document> cursor = users.find(findQuery).iterator();

		Document doc = null;

	 	try {
            while (cursor.hasNext()) {
            	doc = cursor.next();
            }

        } finally {
            cursor.close();
        }

        return doc;
	}

	public static MongoCollection<Document> getCollectionByName(String colName) {
        return DBClient.getDatabase().getCollection(colName);
    }

    public static List<String> getDocuments(MongoCollection<Document> collection, String username) {
        List<String> docList = new ArrayList<>();
        BasicDBObject query = new BasicDBObject("username", new BasicDBObject("$eq", username));
        MongoCursor<Document> cursor = collection.find(query).iterator();

        while (cursor.hasNext()) {
            Document curDoc = cursor.next();
            ArrayList<String> curTrip = (ArrayList<String>) curDoc.get("trip");

            StringBuilder sb = new StringBuilder();

            for (String s : curTrip) {
                sb.append(s);
                if (curTrip.indexOf(s) != curTrip.size() - 1)
                    sb.append("->");
            }

            docList.add(sb.toString());
        }
        return docList;
    }
}
