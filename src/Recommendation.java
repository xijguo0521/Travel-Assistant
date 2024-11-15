import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.*;

import com.google.common.collect.Multimap;

public class Recommendation {

    private static final int MAX_SPOTS_RECOMMENDED = 3;
    private static List<String> res;

    public static List<String> getRecommendedSpots(String collectionName, String curUsrName) {
        res = new ArrayList<>();

        MongoCollection<Document> trips = DBClient.getCollectionByName(collectionName);

        if (trips.count() <= 5)
            return res;

        BasicDBObject neQuery = new BasicDBObject();
        neQuery.put("username", new BasicDBObject("$ne", curUsrName));
        MongoCursor<Document> cursor = trips.find(neQuery).iterator();

        Map<String, Integer> spotMap = new HashMap<>();

        try {
            while (cursor.hasNext()) {
                Document curDoc = cursor.next();
                System.out.println("print current document: " + curDoc);
                ArrayList<String> curTrip = (ArrayList<String>) curDoc.get("trip");

                for (int i = 1; i < curTrip.size(); i++) {
                    String spot = curTrip.get(i);
                    if (spotMap.containsKey(spot))
                        spotMap.put(spot, spotMap.get(spot) + 1);
                    else
                        spotMap.put(spot, 1);
                }
            }
        }

        finally {
            cursor.close();
        }

        Multimap<Integer, String> multimap = Multimaps.invertFrom(Multimaps.forMap(spotMap), HashMultimap.create());

        ArrayList<Integer> visitNum = new ArrayList<>();

        for (Map.Entry<Integer, Collection<String>> entry : multimap.asMap().entrySet()) {
            visitNum.add(entry.getKey());
        }

        Collections.sort(visitNum);

        int size = Math.min(MAX_SPOTS_RECOMMENDED, spotMap.size());

        int times[] = new int[size];

        for (int i = 0; i < size; i++) {
            times[i] = visitNum.get(visitNum.size() - i - 1);
            System.out.println("times: " + times[i]);
            System.out.println("list of spots: " + multimap.get(times[i]));
        }

        for (int i = 0; i < times.length; i++) {
            for (String s : multimap.get(times[i])) {
                if (res.size() < MAX_SPOTS_RECOMMENDED) {
                    System.out.println("Added spots: " + s);
                    res.add(s);
                }
                else
                    break;
            }
        }

        return res;
    }

    public static List<String> getRecommendationList() {
        return res;
    }
}
