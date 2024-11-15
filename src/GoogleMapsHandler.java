import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GoogleMapsHandler {

    private static final String API_KEY = "AIzaSyA7fke7RjTcQI68ksEb9dpYGSEedWLwoGk";

    private OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static Double Distance(boolean isDistance, String origin, String  dest, String Mode) throws IOException{
        GoogleMapsHandler request = new GoogleMapsHandler();
        String url_request = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + origin +"&destinations="+ dest +"&mode="+Mode+"&language=fr-FR&key=" + API_KEY;
        String response = request.run(url_request);
        Double result = 0.0;
        if(isDistance) {
        	JsonObject first = new Gson().fromJson(response, JsonObject.class);
        	JsonObject rows= (JsonObject) first.get("rows").getAsJsonArray().get(0);
        	JsonObject elements = (JsonObject) rows.get("elements").getAsJsonArray().get(0);
        	JsonObject distance = (JsonObject) elements.get("distance");
        	String actual_distance = distance.get("text").toString();
        	String Dnumber = actual_distance.substring(1, actual_distance.length()-3);
        	String temp = Dnumber.replaceAll(" ","");
        	result = Double.valueOf(temp.replaceAll(",", "."));
        	System.out.println(result);
        }
        else {
        	JsonObject first = new Gson().fromJson(response, JsonObject.class);
        	JsonObject rows= (JsonObject) first.get("rows").getAsJsonArray().get(0);
        	JsonObject elements = (JsonObject) rows.get("elements").getAsJsonArray().get(0);
        	JsonObject time = (JsonObject) elements.get("duration");
        	String actual_time = time.get("text").toString();
        	String number = actual_time.replaceAll("[^0-9]+", " ");
        	List<String> TempArray = Arrays.asList(number.trim().split(" "));
        	if(TempArray.size()==2) {
        		result = Double.valueOf(TempArray.get(0))*60+Double.valueOf(TempArray.get(1));
        	}
        	if(TempArray.size()==1) {
        		result = Double.valueOf(TempArray.get(0));
        	}
        
        	System.out.println(result);
        }
        
        return result;
    }
}
